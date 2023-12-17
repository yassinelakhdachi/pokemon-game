package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

import java.util.ArrayList;
import java.util.List;

public class ICMonEventManager {
    private List<ICMonEvent> activeEvents;
    private List<ICMonEvent> newEvents;
    private List<ICMonEvent> completedEvents;


    public boolean isEventActive(String eventName) {
        return activeEvents.stream().anyMatch(event -> event.getClass().getSimpleName().equals(eventName));
    }

    public ICMonEventManager() {
        activeEvents = new ArrayList<>();
        newEvents = new ArrayList<>();
        completedEvents = new ArrayList<>();
    }

    public void registerEvent(ICMonEvent event) {
        newEvents.add(event);
    }

    public void unregisterEvent(ICMonEvent event) {
        completedEvents.add(event);
    }

    public void updateEvents() {
        // Ajouter les nouveaux événements
        activeEvents.addAll(newEvents);
        newEvents.clear();

        // Supprimer les événements complétés
        activeEvents.removeAll(completedEvents);
        completedEvents.clear();
    }

    public List<ICMonEvent> getActiveEvents() {
        return activeEvents;
    }
}
