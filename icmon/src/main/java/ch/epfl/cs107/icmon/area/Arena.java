package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public final class Arena extends ICMonArea {

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
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
