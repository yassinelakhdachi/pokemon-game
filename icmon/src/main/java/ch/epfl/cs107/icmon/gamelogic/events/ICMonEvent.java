package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.Action;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.Updatable;

import java.util.ArrayList;

public abstract class ICMonEvent implements Updatable, ICMonInteractionVisitor {
    private boolean isStarted;
    private boolean isComplete;
    private boolean isSuspended;
    private ICMonPlayer player;
    private ArrayList<Action> starting = new ArrayList<>();
    private ArrayList<Action> ending = new ArrayList<>();
    private ArrayList<Action> suspended = new ArrayList<>();
    private ArrayList<Action> resumed = new ArrayList<>();

    public ICMonEvent(ICMonPlayer player){
        this.player = player;
    }

    public final void start(){
        if (!isStarted){
            for( Action action:starting){
                action.perform();
            }
            isStarted = true;
        }
    }
    public final void complete(){
        if(!isComplete || isStarted){
            for( Action action:ending){
                action.perform();
            }
            isComplete = true;
            isStarted = false;
        }
    }

    public final void suspend(){
        if(!isComplete || !isSuspended || isStarted){
            for( Action action:suspended){
                action.perform();
            }
            isComplete = true;
            isSuspended = true;
            isStarted = false;
        }
    }

    public final void resume(){
        if(!isComplete || isSuspended || isStarted){
            for( Action action:resumed){
                action.perform();
            }
            isComplete = true;
            isSuspended = false;
            isStarted = false;
        }
    }

    public final void onStart(Action action){
        starting.add(action);
    }
    public final void onComplete(Action action){
        ending.add(action);
    }
    public final void onSuspension(Action action){
        suspended.add(action);
    }
    public final void onResume(Action action){
        resumed.add(action);
    }

}
