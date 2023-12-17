package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class Nidoqueen extends Pokemon {
    public Nidoqueen(ICMonArea area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates, "latios", 10, 1);
    }
}
