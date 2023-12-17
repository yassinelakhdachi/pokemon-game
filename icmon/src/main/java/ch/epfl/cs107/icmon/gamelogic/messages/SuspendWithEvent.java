package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;

public class SuspendWithEvent extends GamePlayMessage {
    private PokemonFightEvent event;
    private ICMon game;

    public SuspendWithEvent(ICMon game, PokemonFightEvent event) {
        this.game = game;
        this.event = event;
    }

    public PokemonFightEvent getEvent() {
        return event;
    }

    @Override
    public void process() {

    }
}
