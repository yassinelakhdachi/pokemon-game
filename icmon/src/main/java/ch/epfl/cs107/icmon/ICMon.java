package ch.epfl.cs107.icmon;


import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.areagame.AreaGame;
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

/**
 * ???
 */
public class ICMon extends AreaGame {

    /** ??? */
    public final static float CAMERA_SCALE_FACTOR = 13.f;
    /** ??? */
    private final String[] areas = {"Town"};
    /** ??? */
    private ICMonPlayer player;
    /** ??? */
    private int areaIndex;

    private ICMonItem ball;

    private ArrayList<ICMonEvent> events = new ArrayList<>();

    /**
     * ???
     */
    private void createAreas() {
        addArea(new Town());
    }

    /**
     * ???
     * @param window (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return ???
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            createAreas();
            areaIndex = 0;
            initArea(areas[areaIndex]);
            ball = new ICBall(getCurrentArea(), new DiscreteCoordinates(6, 6));
            getCurrentArea().registerActor(ball);
            CollectItemEvent event1 = new CollectItemEvent(player, ball);
            events.add(event1);
            LogAction collectStarted = new LogAction("CollectItemEvent started !");
            LogAction collectCompleted = new LogAction("CollectItemEvent completed !");
            event1.onStart(collectStarted);
            event1.start();
            event1.onComplete(collectCompleted);
            return true;
        }
        return false;
    }

    /**
     * ???
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (ICMonEvent event:events){
            event.update(deltaTime);
        }
        if (ball.isCollected()) {
            events.get(0).complete();
        }

    }

    /**
     * ???
     */
    @Override
    public void end() {

    }

    /**
     * ???
     * @return ???
     */
    @Override
    public String getTitle() {
        return "ICMon";
    }

    /**
     * ???
     * @param areaKey ???
     */
    private void initArea(String areaKey) {
        ICMonArea area = (ICMonArea) setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        player = new ICMonPlayer(area, Orientation.DOWN, coords, "actors/player");
        player.enterArea(area, coords);
        player.centerCamera();
    }

}