package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.play.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;

import java.util.Collections;
import java.util.List;

public class Door extends AreaEntity {
    private String destinationArea;
    private DiscreteCoordinates destinationCoordinates;

    public Door(Area area, String destinationArea, DiscreteCoordinates destinationCoordinates,
                DiscreteCoordinates mainCoordinates) {
        super(area, Orientation.UP, mainCoordinates);
        this.destinationArea = destinationArea;
        this.destinationCoordinates = destinationCoordinates;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void draw(Canvas canvas) {
        // Méthode vide car la porte ne se dessine pas elle-même (fait partie du décor)
    }

    @Override
    public boolean takeCellSpace() {
        return false; // La porte ne bloque pas le passage d'autres acteurs
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        System.out.println("Player is trying to interact with a door at " + getCurrentMainCellCoordinates());
    }

    public String getDestinationArea() {
        return destinationArea;
    }

    public DiscreteCoordinates getDestinationCoordinates() {
        return destinationCoordinates;
    }
}
