package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

public final class Lab extends ICMonArea {
    private ICMonPlayer playerlab;
    private ICMonPlayer otherPlayerlab;

    private ICMon gameReferencelab;
    public Lab(ICMon gameReferencelab) {
        this.gameReferencelab = gameReferencelab;
    }

    public void setPlayer(ICMonPlayer playerlab){
        this.playerlab = playerlab;
    }

    @Override
    protected void createArea() {
        // Ajouter des éléments de base comme le fond (background) et le premier plan (foreground)
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        // Ajouter la porte menant à Town
        registerActor(new Door(this, "Town", new DiscreteCoordinates(15, 23),
                new DiscreteCoordinates(7, 1)));

        // Coordonnées où vous voulez que le joueur apparaisse dans Lab
        DiscreteCoordinates spawnPosition = new DiscreteCoordinates(6, 2);

        // Création du joueur pour Lab
        // Utiliser gameReference pour créer le joueur
        otherPlayerlab = new ICMonPlayer(this, Orientation.DOWN, spawnPosition, "actors/player", gameReferencelab);
        // Enregistrer le joueur dans Lab
        registerActor(otherPlayerlab);
    }

    @Override
    public String getTitle() {
        return "Lab";
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        // Coordonnées où le joueur apparaîtra lorsqu'il entre dans Lab
        return new DiscreteCoordinates(7, 1);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        setViewCandidate(playerlab);

    }

    public void draw(Canvas canvas) {
        if (otherPlayerlab != null) {
            otherPlayerlab.draw(canvas); // Dessiner l'autre joueur
        }
        otherPlayerlab.draw(canvas);

    }

}
