package Actions;
import Entities.Artefact;
import Entities.Character;
import Entities.Furniture;
import Entities.Location;
import Entities.Player;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CommandAction implements Gameplay {
    private HashSet<String> subjects;
    private HashSet<String> consumed;
    private HashSet<String> produced;
    private String narration;
    private String[] userInput;
    private Action action;
    private List<Action> actionList;
    private String trigger;
    private Player player;
    private Location location;
    public HashMap<String, Artefact> playerInventory;
    private HashMap<String, Location> locationMap;
    private BufferedWriter out;
    private boolean consumedFlag;
    private boolean producedFlag;

    public CommandAction(String trigger){
        this.trigger = trigger;
        consumedFlag = false;
        producedFlag = false;
    }

    public Action findAction(HashMap<String, List<Action>> actionsMap, String[] userInput){
        this.userInput = userInput;
        actionList = actionsMap.get(trigger);

        if(checkForSubject()) return action;
        return null;
    }

    public void setAction(Action action, String[] userInput, Player player,
                              HashMap<String, Location> locationMap, BufferedWriter out) throws IOException {
        this.userInput = userInput;
        this.action = action;
        this.player = player;
        this.locationMap = locationMap;
        this.out = out;
        this.subjects = action.getSubjects();
        this.consumed = action.getConsumed();
        this.produced = action.getProduced();
        this.narration = action.getNarration();
        playerInventory = player.getPlayerInventory();
        location = player.getPlayerLocation();
        executeAction();
    }

    public void executeAction() throws IOException {
        if(checkRequiredSubject()){
            consumeObject();
            produceObject();
            printNarration(); }
    }

    private boolean checkRequiredSubject(){
        for(String input : userInput)
            if (checkEachSubject(input, subjects) || player.checkInInventory(input)) return true;
        return false; }

        // check through hashset of subjects for matching from user input
    private boolean checkEachSubject(String input, HashSet<String> checkSubjects) {
        for(String s : checkSubjects){
            if(s.equals(input)) return true; }
        return false; }

    private boolean checkForSubject(){
        HashSet<String> checkSubjects;

        for(Action newAction : actionList){
            checkSubjects = newAction.getSubjects();
            for(String input : userInput){
                if(checkSubjects != null){
                    // if the user input is a word in the subjects hashset, this is the required action
                    if(checkEachSubject(input, checkSubjects)){
                        action = newAction;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void printNarration() throws IOException {
        if(consumedFlag || producedFlag){
            out.write(narration);
            consumedFlag = false;
            producedFlag = false;
        }
    }

    private void consumeObject(){
        for(String item : consumed)
            if (location.checkForArtefact(item)) {
                location.removeArtefact(item);
                consumedFlag = true;
            } else if (item.equals("health")) {
                if(!player.checkIfDead()){
                    player.decreaseHealth();
                    consumedFlag = true;
                }
            }
    }

    private void produceObject(){
        Location unplaced;
        unplaced = locationMap.get("unplaced");

        // cycles through each entity to check it exists in unplaced
        for(String item : produced) {
            if (unplaced.checkForArtefact(item)) {
                Artefact artefact = unplaced.getArtefact(item);
                unplaced.removeArtefact(item);
                String description = artefact.getDescription();
                location.addArtefact(item, description);
                consumedFlag = true;
            }
            if (unplaced.checkForCharacter(item)) {
                Character character = unplaced.getCharacter(item);
                unplaced.removeCharacter(item);
                String description = character.getDescription();
                location.addCharacter(item, description);
                consumedFlag = true;
            }
            if (unplaced.checkForFurniture(item)) {
                Furniture furniture = unplaced.getFurniture(item);
                unplaced.removeFurniture(item);
                String description = furniture.getDescription();
                location.addFurniture(item, description);
                consumedFlag = true;
            }
            if (locationMap.containsKey(item)) {
                location.addPath(item);
                consumedFlag = true;
            }
            if (item.equals("health")) {
                player.increaseHealth();
                consumedFlag = true;
            }
        }
    }
}

