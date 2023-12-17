package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMonEventManager;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class StartEventAction implements Action {
    private ICMonEvent eventToStart;
    private ICMonEventManager eventManager;

    public StartEventAction(ICMonEvent eventToStart, ICMonEventManager eventManager) {
        this.eventToStart = eventToStart;
        this.eventManager = eventManager;
    }

    @Override
    public void perform() {
        if (eventToStart != null && eventManager != null) {
            eventManager.registerEvent(eventToStart);
            eventToStart.start();
        }
    }
}
