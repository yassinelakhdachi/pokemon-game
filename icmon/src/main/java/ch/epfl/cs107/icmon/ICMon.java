package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.area.Arena;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.StartEventAction;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.EndOfTheGameEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.messages.GamePlayMessage;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.Town;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.engine.actor.Animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ICMon extends AreaGame {

    public final static float CAMERA_SCALE_FACTOR = 13.f;
    private final String[] areas = {"Town"};
    private ICMonPlayer player;
    private int areaIndex;

    private ICMonItem ball;

    private ICMonGameState gameState = new ICMonGameState();

    private ArrayList<ICMonEvent> events = new ArrayList<>();

    private ICMonEventManager eventManager = new ICMonEventManager();

    private GamePlayMessage message;

    private Map<String, ICMonArea> areasMap = new HashMap<>();

    private ArrayList<ICMonEvent> activeEvents = new ArrayList<>();
    private ArrayList<ICMonEvent> eventsToStart = new ArrayList<>();
    private ArrayList<ICMonEvent> completedEvents = new ArrayList<>();

    public void registerEvent(ICMonEvent event) {
        eventsToStart.add(event);
    }

    public void unregisterEvent(ICMonEvent event) {
        completedEvents.add(event);
    }

    public ICMonGameState getGameState() {
        return gameState;
    }

    public class ICMonGameState {
        public void acceptInteraction(Interactable interactable, boolean isCellInteraction) {
            for (ICMonEvent event : events) {
                interactable.acceptInteraction(event, isCellInteraction);
            }
        }
    }

    private void createAreas() {
        Town town = new Town(eventManager);
        addArea(town);
        Arena arena = new Arena(); // Création de Arena sans paramètres
        addArea(arena);
    }

    private void addArea(ICMonArea area) {
        areasMap.put(area.getTitle(), area);
        super.addArea(area);
        area.begin(getWindow(), getFileSystem()); // Initialiser l'aire
    }

    public void updateCurrentArea(String areaTitle) {
        setCurrentArea(areaTitle, false);
    }


    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            createAreas();
            areaIndex = 0;
            initArea(areas[areaIndex]);
            ball = new ICBall(getCurrentArea(), new DiscreteCoordinates(6, 6));
            getCurrentArea().registerActor(ball);

            ICMonArea currentICMonArea = (ICMonArea) getCurrentArea();
            player = new ICMonPlayer(currentICMonArea, Orientation.DOWN, new DiscreteCoordinates(5, 5), "actors/player", this);
            currentICMonArea.registerActor(player);

            EndOfTheGameEvent endOfTheGameEvent = new EndOfTheGameEvent(player, eventManager);

            CollectItemEvent collectItemEvent = new CollectItemEvent(player, ball, eventManager, endOfTheGameEvent);
            collectItemEvent.start();

            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        eventManager.updateEvents();
        for (ICMonEvent event : eventManager.getActiveEvents()) {
            event.update(deltaTime);
        }
        if (ball != null && ball.isCollected()) {
            if (!eventManager.getActiveEvents().isEmpty()) {
                eventManager.getActiveEvents().get(0).complete();
            }
        }
        if (message != null) {
            System.out.println("ICMon is processing a message");
            message.process();
            message = null;
        }
    }

    public void send(GamePlayMessage message) {
        this.message = message;
        System.out.println("Message sent: " + message);
    }

    @Override
    public void end() {
        // End game logic here
    }

    @Override
    public String getTitle() {
        return "ICMon";
    }

    private void initArea(String areaKey) {
        ICMonArea area = areasMap.get(areaKey);
        if (area == null) {
            System.out.println("Area not found: " + areaKey);
            return;
        }
        setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();

        player = new ICMonPlayer(area, Orientation.DOWN, coords, "actors/player", this);
        player.enterArea(area, coords);
        player.centerCamera();
    }

    public ICMonEventManager getEventManager() {
        return eventManager;
    }

    public ICMonArea getArea(String areaName) {
        return areasMap.get(areaName);
    }
}
