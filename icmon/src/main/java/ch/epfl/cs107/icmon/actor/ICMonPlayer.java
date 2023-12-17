package ch.epfl.cs107.icmon.actor;
import ch.epfl.cs107.icmon.area.Door;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.gamelogic.messages.PassDoorMessage;
import ch.epfl.cs107.play.engine.actor.Dialog;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.math.Vector;


import java.util.Collections;
import java.util.List;

public final class ICMonPlayer extends ICMonActor implements Interactor {
    private static final int MOVE_DURATION = 8;
    public static final int ANIMATION_DURATION = 8;
    private OrientedAnimation[] animation;
    private OrientedAnimation currentAnimation;
    private OrientedAnimation walkingOnGround;
    private OrientedAnimation walkingOnWater;
    private final ICMonPlayerInteractionHandler handler;
    private final ICMon game; // Référence au jeu pour l'accès à l'état du jeu
    private Orientation orientation;
    private DiscreteCoordinates currentCoordinates;
    private ICMonArea currentArea;


    private Dialog currentDialog;
    private boolean isInDialogue;

    public ICMonPlayer(ICMonArea area, Orientation orientation, DiscreteCoordinates coordinates, String spriteName, ICMon game) {
        super(area, orientation, coordinates);
        animation = new OrientedAnimation[2];
        animation[0] = new OrientedAnimation("actors/player", ANIMATION_DURATION / 2, orientation, this);
        animation[1] = new OrientedAnimation("actors/player_water", ANIMATION_DURATION / 2, orientation, this);
        walkingOnGround = animation[0];
        walkingOnWater = animation[1];
        currentAnimation = walkingOnGround;
        handler = new ICMonPlayerInteractionHandler();
        this.game = game;
        resetMotion();
    }

    @Override
    public void update(float deltaTime) {
        if (isInDialogue) {
            if (currentDialog != null && getOwnerArea().getKeyboard().get(Keyboard.SPACE).isPressed()) {
                currentDialog.update(deltaTime);
                if (currentDialog.isCompleted()) {
                    isInDialogue = false;
                    currentDialog = null;
                }
            }
        } else {
            // Comportement habituel du joueur (déplacement, etc.)
            Keyboard keyboard = getOwnerArea().getKeyboard();
            moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
            moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
            moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
            moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

            if (isDisplacementOccurs()) {
                currentAnimation.update(deltaTime);
            } else {
                currentAnimation.reset();
                resetMotion();
            }
            super.update(deltaTime);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (isInDialogue && currentDialog != null) {
            currentDialog.draw(canvas);
        } else {
            currentAnimation.draw(canvas);
        }
    }


    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return super.getCurrentCells();
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        return keyboard.get(Keyboard.L).isPressed();
    }

//    @Override
//    public void interactWith(Interactable other, boolean isCellInteraction) {
//        other.acceptInteraction(handler, isCellInteraction);
//    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    private void moveIfPressed(Orientation orientation, Button b) {
        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                currentAnimation.orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    public void leaveArea() {
        if (currentArea != null) {
            currentArea.unregisterActor(this);
        }
        currentArea = null;
    }

    public void enterArea(ICMonArea newArea, DiscreteCoordinates newCoordinates) {
        currentArea = newArea;
        newArea.registerActor(this);
        setPosition(newCoordinates.toVector());
        newArea.setViewCandidate(this); // Définit le joueur comme candidat pour le suivi de la caméra
        centerCamera(); // Centre la caméra sur le joueur dans la nouvelle aire
    }



    public void setPosition(Vector newPosition) {
        if (newPosition != null) {
            super.setCurrentPosition(newPosition);
        }
    }

    public void centerCamera() {
        super.centerCamera();
    }

    public void openDialog(Dialog dialog) {
        this.currentDialog = dialog;
        this.isInDialogue = true;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);

        // Ajoutez la logique spéciale ici pour la porte menant à l'Arena
        if (other instanceof Door && isCellInteraction) {
            System.out.println("ICMonPlayer interacts with a door at " + getCurrentMainCellCoordinates());
            Door door = (Door) other;
            game.send(new PassDoorMessage(this, door, game)); // Envoyer le message pour gérer le changement d'aire
            // Vérification temporaire de la position spécifique
            if (getCurrentMainCellCoordinates().equals(new DiscreteCoordinates(20, 16))) {
                System.out.println("Changing area to Arena directly");
                ICMonArea destinationArea = game.getArea("Arena");
                this.leaveArea();
                this.enterArea(destinationArea, destinationArea.getPlayerSpawnPosition());
                this.centerCamera();
            }
        }
    }

    private class ICMonPlayerInteractionHandler implements ICMonInteractionVisitor {
        @Override
        public void interactWith(ICMonBehavior.ICMonCell other, boolean isCellInteraction) {
            if (isCellInteraction) {
                switch (other.getType()) {
                    case WATER -> currentAnimation = walkingOnWater;
                    case INDOOR_WALKABLE, OUTDOOR_WALKABLE, GRASS -> currentAnimation = walkingOnGround;
                }
            }
        }

        @Override
        public void interactWith(ICBall ball, boolean wantsViewInteractions) {
            if (wantsViewInteraction()) {
                ball.collect();
            }
        }

        @Override
        public void interactWith(Interactable other, boolean isCellInteraction) {
            if (other instanceof Door && isCellInteraction) {
                System.out.println("ICMonPlayer is sending PassDoorMessage");
                Door door = (Door) other;
                game.send(new PassDoorMessage(ICMonPlayer.this, door, game));
                System.out.println("PassDoorMessage sent");
            }else {
                // Gère les autres interactions
                other.acceptInteraction(handler, isCellInteraction);
            }
        }
    }
}

