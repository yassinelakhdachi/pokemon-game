package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public final class Lab extends ICMonArea {

    @Override
    protected void createArea() {
        // Ajouter des éléments de base comme le fond (background) et le premier plan (foreground)
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        // Ajouter la porte menant à Town
        registerActor(new Door(this, "Town", new DiscreteCoordinates(15, 23),
                new DiscreteCoordinates(7, 1)));
    }

    @Override
    public String getTitle() {
        return "Lab";
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        // Coordonnées où le joueur apparaîtra lorsqu'il entre dans Lab
        return new DiscreteCoordinates(7, 1);
    }
}
