package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.icmon.area.ICMonArea;

public abstract class Pokemon extends ICMonActor {
    private final String name;
    private float hp;
    private final float maxHp;
    private final int damage;

    protected Pokemon(ICMonArea area, Orientation orientation, DiscreteCoordinates coordinates,
                      String name, float maxHp, int damage) {
        super(area, orientation, coordinates);
        this.name = name;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.damage = damage;
        // Création du sprite pour le Pokémon
        new RPGSprite("pokemon/" + name, 1, 1, this);
    }

    // Méthodes pour gérer les points de vie et les dommages
    public void receiveDamage(float amount) {
        hp = Math.max(hp - amount, 0);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    // Getters pour les attributs
    public String getName() {
        return name;
    }

    public float getHp() {
        return hp;
    }

    public float getMaxHp() {
        return maxHp;
    }

    public int getDamage() {
        return damage;
    }

    /**
     * @author Hamza REMMAL (hamza.remmal@epfl.ch)
     */
    public final class PokemonProperties {
        public String name() {
            return name;
        }

        public float hp() {
            return hp;
        }

        public float maxHp() {
            return maxHp;
        }

        public int damage() {
            return damage;
        }


    }

    public PokemonProperties getProperties() {
        return new PokemonProperties();
    }



}
