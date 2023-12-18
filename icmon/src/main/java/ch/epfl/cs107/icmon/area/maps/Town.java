package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.ICMonEventManager;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.area.Door;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.EndOfTheGameEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.math.Orientation;

/**
 * ???
 */
public final class Town extends ICMonArea {
    private ICMonEventManager eventManager;

    private EndOfTheGameEvent endOfTheGameEvent;

    public Town(ICMonEventManager eventManager) {
        this.eventManager = eventManager;
    }
    /**
     * ???
     *
     * @return ???
     */
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(5, 5);
    }

    /**
     * ???
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        // Création de ICShopAssistant avec eventManager et endOfTheGameEvent
        ICShopAssistant assistant = new ICShopAssistant(this, Orientation.DOWN, new DiscreteCoordinates(8, 8), eventManager);
        registerActor(assistant);

        registerActor(new Door(this, "Lab", new DiscreteCoordinates(6, 2),
                new DiscreteCoordinates(15, 24)));

        registerActor(new Door(this, "Arena", new DiscreteCoordinates(4, 2),
                new DiscreteCoordinates(20, 16)));
    }

    /**
     * ???
     *
     * @return ???
     */
    @Override
    public String getTitle() {
        return "Town";
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
