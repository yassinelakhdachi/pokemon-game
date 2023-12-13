package ch.epfl.cs107.icmon.actor;

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

    public ICMonPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String spriteName, ICMon game) {
        super(owner, orientation, coordinates);
        animation = new OrientedAnimation[2];
        animation[0] = new OrientedAnimation("actors/player", ANIMATION_DURATION/2, orientation, this);
        animation[1] = new OrientedAnimation("actors/player_water", ANIMATION_DURATION/2, orientation, this);
        walkingOnGround = animation[0];
        walkingOnWater = animation[1];
        currentAnimation = walkingOnGround;
        handler = new ICMonPlayerInteractionHandler();
        this.game = game;
        resetMotion();
    }

    @Override
    public void update(float deltaTime) {
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

    @Override
    public void draw(Canvas canvas) {
        currentAnimation.draw(canvas);
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

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }

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

    public void enterArea(Area area, DiscreteCoordinates position) {
        super.enterArea(area, position);
    }

    public void centerCamera() {
        super.centerCamera();
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
            other.acceptInteraction(handler, isCellInteraction);
            if (game != null) {
                game.getGameState().acceptInteraction(other, isCellInteraction); // Délégation aux événements du jeu
            }
        }
    }
}
