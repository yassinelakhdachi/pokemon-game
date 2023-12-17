package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.ICMonEventManager;

public class UnRegisterEventAction implements Action {
    private ICMonEvent event;
    private ICMonEventManager eventManager;

    /**
     * Constructeur pour UnRegisterEventAction.
     *
     * @param event        (ICMonEvent): l'événement à désenregistrer, non null
     * @param eventManager (ICMonEventManager): gestionnaire des événements du jeu, non null
     */
    public UnRegisterEventAction(ICMonEvent event, ICMonEventManager eventManager) {
        this.event = event;
        this.eventManager = eventManager;
    }

    @Override
    public void perform() {
        if (eventManager != null && event != null) {
            eventManager.unregisterEvent(event);
        }
    }
}
