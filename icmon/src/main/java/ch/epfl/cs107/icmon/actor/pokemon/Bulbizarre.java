package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class Bulbizarre extends Pokemon {
    public Bulbizarre(ICMonArea area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates, "bulbizarre", 10, 1);
    }
}
