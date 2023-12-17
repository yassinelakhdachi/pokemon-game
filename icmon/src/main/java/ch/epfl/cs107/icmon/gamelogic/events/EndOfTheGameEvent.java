package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMonEventManager;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnRegisterEventAction;
import ch.epfl.cs107.play.engine.actor.Dialog;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class EndOfTheGameEvent extends ICMonEvent {
    public EndOfTheGameEvent(ICMonPlayer player, ICMonEventManager eventManager) {
        super(player);
        onStart(new RegisterEventAction(this, eventManager));
        onComplete(new UnRegisterEventAction(this, eventManager));
    }

    @Override
    public void update(float deltaTime) {
        // Logique spécifique de mise à jour, si nécessaire
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        String dialogueText = loadDialogueFromXML("dialogs/end_of_game_event_interaction_with_icshopassistant.xml");
        getPlayer().openDialog(new Dialog(dialogueText));
    }

    private String loadDialogueFromXML(String filePath) {
        SAXBuilder saxBuilder = new SAXBuilder();
        File xmlFile = new File(filePath);

        try {
            Document document = saxBuilder.build(xmlFile);
            Element rootElement = document.getRootElement();
            List<Element> lines = rootElement.getChildren("line");

            return lines.stream()
                    .map(Element::getText)
                    .collect(Collectors.joining("\n"));
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
            return "Error loading dialogue";
        }
    }
}
