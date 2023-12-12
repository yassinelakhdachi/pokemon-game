package ch.epfl.cs107.icmon.handler;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;

public interface ICMonInteractionVisitor extends AreaInteractionVisitor {
    default void interactWith(ICMonBehavior.ICMonCell other, boolean isCellInteraction) {
    }
    default void interactWith(ICMonPlayer other, boolean isCellInteraction) {
    }
    default void interactWith(ICBall other, boolean isCellInteraction) {
    }
    default void interactWith(ICShopAssistant other, boolean isCellInteraction) {
    }
}
