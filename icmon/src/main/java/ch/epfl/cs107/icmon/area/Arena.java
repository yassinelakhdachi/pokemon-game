package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;

public final class Arena extends ICMonArea {

    @Override
    protected void createArea() {
        // Chemin d'accès au fichier background
        registerActor(new Background(this, "arena"));

        // Chemin d'accès au fichier foreground et définition de la région d'intérêt
//        RegionOfInterest roi = new RegionOfInterest(0, 0, 1000, 1000);
//        registerActor(new Foreground(this, roi, "arena"));

        // Ajout de la porte
        registerActor(new Door(this, "Town", new DiscreteCoordinates(20, 15),
                new DiscreteCoordinates(4, 1)));
    }

    @Override
    public String getTitle() {
        return "Arena";
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(4, 2);
    }
}
