package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.engine.actor.Actor;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

abstract public class ICMonFight extends PauseMenu implements Actor {
    private float counter;
    private Pokemon playerPokemon;

    private Pokemon opponentPokemon;
    private ICMonFightArenaGraphics arena;

    public ICMonFight(Pokemon playerPokemon, Pokemon opponentPokemon) {
        this.playerPokemon = playerPokemon;
        this.opponentPokemon = opponentPokemon;
        this.arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, playerPokemon.getProperties(), opponentPokemon.getProperties());
    }
    @Override
    public void update(float deltaTime) {
        if (isRunning()) {
            counter -= deltaTime;
        }
    }

    public boolean isRunning() {
        return counter > 0;
    }

    @Override
    public void draw(Canvas canvas) {
        arena.draw(canvas);
    }

    @Override
    public Transform getTransform() {
        // Retourne une transformation par défaut
        return Transform.I;
    }

    @Override
    public Vector getVelocity() {
        // Retourne une vitesse par défaut
        return Vector.ZERO;
    }
}
