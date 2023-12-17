package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.play.engine.actor.Actor;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class PokemonFightEvent extends ICMonEvent implements Actor {
    private ICMonFight fightMenu;

    public PokemonFightEvent(ICMonPlayer player, ICMonFight fightMenu) {
        super(player);  // Appel du constructeur de la super classe
        this.fightMenu = fightMenu;
    }

    public ICMonFight getFightMenu() {
        return fightMenu;
    }

    public void startFight() {
        // Logique pour démarrer le combat
    }

    public void endFight() {
        // Logique pour terminer le combat
    }

    @Override
    public void update(float deltaTime) {
        // Logique d'update pour le combat
    }

    @Override
    public void draw(Canvas canvas) {
        // Logique de dessin pour le combat
    }

    @Override
    public Transform getTransform() {
        return Transform.I; // Transform.I est une transformation identité par défaut
    }

    @Override
    public Vector getVelocity() {
        return Vector.ZERO; // Vector.ZERO représente une vitesse nulle
    }
}
