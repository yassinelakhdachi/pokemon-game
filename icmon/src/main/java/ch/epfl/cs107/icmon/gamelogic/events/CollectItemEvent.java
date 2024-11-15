package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMonEventManager;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.StartEventAction;
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

public class CollectItemEvent extends ICMonEvent {
    private ICMonItem item;
    private ICMonEventManager eventManager;
    private EndOfTheGameEvent endOfTheGameEvent;

    public CollectItemEvent(ICMonPlayer player, ICMonItem item, ICMonEventManager eventManager, EndOfTheGameEvent endOfTheGameEvent) {
        super(player);
        this.item = item;
        this.eventManager = eventManager;
        this.endOfTheGameEvent = endOfTheGameEvent;
        onStart(new RegisterEventAction(this, eventManager));
        onStart(new LogAction("ICMonItemCollect has started !"));
        onComplete(new LogAction("ICMonItemCollect has been completed !"));
        onComplete(new UnRegisterEventAction(this, eventManager));
        onComplete(new StartEventAction(endOfTheGameEvent, eventManager));
    }

    @Override
    public void update(float deltaTime) {
        if (item.isCollected() && !isComplete()) {
            complete();
        }
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        String dialogueText = loadDialogueFromXML("dialogs/collect_item_event_interaction_with_icshopassistant.xml");
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

    @Override
    public void interactWith(ICBall ball, boolean isCellInteraction) {
        if (!isCellInteraction) {
            System.out.println("Player is interacting with Ball");
            ball.collect();
        }
    }
}
