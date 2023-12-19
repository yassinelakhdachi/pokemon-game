package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.window.Canvas;

public final class Arena extends ICMonArea {

    private ICMonPlayer player;

    private ICMonPlayer otherPlayer;

    private ICMon gameReference;


//    public Arena(ICMonPlayer player){
//        this.player = player;
//    }

    public void setPlayer(ICMonPlayer player){
        this.player = player;
    }

    public Arena(ICMon gameReference) {
        this.gameReference = gameReference;
    }


    @Override
    protected void createArea() {
        System.out.println("Creating Arena...");

        registerActor(new Background(this,"arena"));
        System.out.println("Background added to Arena");

        registerActor(new Foreground(this));
        System.out.println("Foreground added to Arena");

        // Ajout de la porte
        registerActor(new Door(this, "Town", new DiscreteCoordinates(20, 15),
                new DiscreteCoordinates(4, 1)));
        System.out.println("Door from Arena to Town added");

        // Coordonnées où vous voulez qu44e le joueur apparaisse dans Arena
        DiscreteCoordinates spawnPosition = new DiscreteCoordinates(4, 4);

        // Création du joueur pour Arena
        // Utiliser gameReference pour créer le joueur
        otherPlayer = new ICMonPlayer(this, Orientation.DOWN, spawnPosition, "actors/player", gameReference);
        // Enregistrer le joueur dans Arena
        registerActor(otherPlayer);
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            setBehavior(new ICMonBehavior(window, "Arena"));
            createArea();
            return true;
        }
        return false;
    }


    @Override
    public String getTitle() {
        return "Arena";
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        setViewCandidate(player);
    }


    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(4, 2);
    }


    public void draw(Canvas canvas) {
        if (otherPlayer != null) {
            otherPlayer.draw(canvas); // Dessiner l'autre joueur
        }
        otherPlayer.draw(canvas);

    }
}
