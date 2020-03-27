import Actions.Action;
import Actions.CommandAction;
import Actions.DropAction;
import Actions.GetAction;
import Actions.GotoAction;
import Actions.HealthAction;
import Actions.InventoryAction;
import Actions.LookAction;
import Entities.Location;
import Entities.Player;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StagController {
    private HashMap<String, Location> locationMap;
    private HashMap<String, Player> stagPlayers;
    private HashMap<String, List<Action>> actionsMap;
    private Player currPlayer;
    private Location currLocation;
    private String[] userInput;
    private String actionWord;
    private ArrayList<Object> standardCommands;
    private BufferedWriter out;
    private Location start;
    private StagWorld world;
    private boolean actionGameplay; // if false, standard gameplay

    public StagController(StagWorld world, HashMap<String, List<Action>> actionsMap){
        this.world = world;
        locationMap = new HashMap<>();
        stagPlayers = new HashMap<>();
        this.actionsMap = actionsMap;
        fillStandardCommands();
        locationMap = world.getAllWorldLocations();
    }

    public void checkPlayer(String playerName) {
        if(!(stagPlayers.containsKey(playerName))){
            Player newPlayer = new Player(playerName, locationMap);
            setStart();
            newPlayer.setPlayerLocation(start);
            stagPlayers.put(playerName, newPlayer);
        }
        currPlayer = stagPlayers.get(playerName);
    }

    public void playGame(String playerName, String[] userInput, BufferedWriter out) throws IOException {
        this.out = out;
        checkPlayer(playerName);
        setUserInput(userInput);
        preformAction();
        currLocation = currPlayer.getPlayerLocation();
        endTurn();
    }

    private void setStart(){ start = world.getStart(); }

    private void fillStandardCommands(){
        standardCommands = new ArrayList<>();
        standardCommands.add("inventory");
        standardCommands.add("inv");
        standardCommands.add("get");
        standardCommands.add("drop");
        standardCommands.add("goto");
        standardCommands.add("look");
        standardCommands.add("health");
    }

    private void setUserInput(String[] userInput){
        this.userInput = userInput;
    }

    private void preformAction() throws IOException {
        checkPlayerAlive();
        if(!(actionGameplay)) preformStandardAction();
        else{
            CommandAction action = new CommandAction(actionWord);
            Action currAction = action.findAction(actionsMap, userInput);
            if(currAction != null) action.setAction(currAction, userInput, currPlayer, locationMap, out);
            else out.write("Invalid action.\n");
        }
    }

    private void preformStandardAction() throws IOException {
        switch (actionWord) {
            case "inventory":
            case "inv":
                InventoryAction invAction = new InventoryAction(currPlayer);
                invAction.setAction(out);
                break;
            case "get":
                GetAction getAction = new GetAction(currPlayer, userInput);
                getAction.executeAction();
                break;
            case "drop":
                DropAction dropAction = new DropAction(currPlayer, userInput);
                dropAction.executeAction();
                break;
            case "goto":
                GotoAction goAction = new GotoAction(currPlayer, userInput, locationMap);
                goAction.setAction(out);
                break;
            case "look":
                LookAction lookAction = new LookAction(currPlayer, out);
                lookAction.executeAction();
                break;
            case "health":
                HealthAction healthAction = new HealthAction(currPlayer, out);
                healthAction.executeAction();
                checkPlayerAlive();
                break;
        }
    }

    private void checkPlayerAlive() throws IOException {
        if(currPlayer.checkIfDead()) {
            currPlayer.setPlayerLocation(start);
            out.write("You have died. Restarting player.");
        }
    }

    // checks for at least one matching valid key word (trigger or standard) from input
    public boolean checkValidInput(String[] userInput){
        for(String input : userInput) {
            if (actionsMap.containsKey(input) || checkStandardCommand(input)) {
                actionWord = input;
                actionGameplay = true;
                return true; }
        }
        return false;
    }

    private boolean checkStandardCommand(String input){ return standardCommands.contains(input); }

    public void endTurn(){ currPlayer.setPlayerLocation(currLocation); }
}
