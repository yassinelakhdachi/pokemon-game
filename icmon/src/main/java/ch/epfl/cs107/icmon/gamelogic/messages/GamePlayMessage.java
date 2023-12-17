package ch.epfl.cs107.icmon.gamelogic.messages;

/**
 * Classe de base pour les messages de gameplay.
 */
public abstract class GamePlayMessage {
    /**
     * Traitement spécifique du message.
     */
    public abstract void process();
}
