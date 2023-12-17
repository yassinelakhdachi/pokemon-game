package ch.epfl.cs107.icmon.actor.items;

import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class ICBall extends ICMonItem {
    private boolean isCollected = false; // Nouvel attribut

    public ICBall(Area area, DiscreteCoordinates position) {
        super(area, position, "items/icball");
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        if (!isCollected) { // Vérifie si la balle n'a pas été déjà collectée
            System.out.println("Player is interacting with Ball"); // Affiche le message
            isCollected = true; // Marque la balle comme collectée
        }
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    public boolean isCollected() {
        return isCollected;
    }
}
