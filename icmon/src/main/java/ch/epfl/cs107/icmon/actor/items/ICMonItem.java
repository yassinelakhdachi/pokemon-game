package ch.epfl.cs107.icmon.actor.items;

import ch.epfl.cs107.play.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

import javax.swing.*;


public abstract class ICMonItem extends CollectableAreaEntity implements Interactable {
    private RPGSprite item;

    public ICMonItem(Area area, DiscreteCoordinates position, String spriteName) {
        super(area, Orientation.DOWN, position);
        item = new RPGSprite(spriteName, 1f, 1f, this);
    }
    @Override
    public boolean takeCellSpace() {return true;}
    @Override
    public boolean isCellInteractable() {return true;}

    @Override
    public void draw(Canvas canvas) {
        item.draw(canvas);
    }
}
