package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

public class ICShopAssistant extends NPCActor {

    /**
     * Constructor for ICShopAssistant.
     *
     * @param owner       (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the actor in the Area. Not null
     * @param coordinates (DiscreteCoordinates): Initial position of the actor. Not null
     */
    public ICShopAssistant(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates, "actors/assistant");
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        System.out.println("This is an interaction between the player and ICShopAssistant based on events !");
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }




    @Override
    public void draw(Canvas canvas) {
        // Utilisez directement le sprite hérité de NPCActor
        if (getSprite() != null) {
            getSprite().draw(canvas);
        }
    }

    // Méthode pour obtenir le sprite hérité (si nécessaire)
    protected Sprite getSprite() {
        return sprite;
    }
}
