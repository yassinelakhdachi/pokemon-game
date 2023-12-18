package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.area.Door;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.ICMon;

public class PassDoorMessage extends GamePlayMessage {
    private final ICMonPlayer player;
    private final Door door;
    private final ICMon game;
    public PassDoorMessage(ICMonPlayer player, Door door, ICMon game) {
        this.player = player;
        this.door = door;
        this.game = game;
        System.out.println("PassDoorMessage created for door leading to " + door.getDestinationArea());
    }

    @Override
    public void process() {
        System.out.println("Processing PassDoorMessage");
        player.leaveArea();
        ICMonArea destinationArea = game.getArea(door.getDestinationArea());

        if (destinationArea != null) {
            player.enterArea(destinationArea, destinationArea.getPlayerSpawnPosition());
            System.out.println("Player moved to " + door.getDestinationArea());
            game.updateCurrentArea(destinationArea.getTitle());
        }
        }



}
