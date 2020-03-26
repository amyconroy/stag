import Actions.*;
import Entities.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.*;
import java.lang.*;

public class StagController {
    private HashMap<String, Location> locationMap;
    private HashMap<String, Player> stagPlayers;
    private HashMap<String, List<Action>> actionsMap;
    private Action action;
    private Player currPlayer;
    private Location currLocation;
    private String[] userInput;
    private String actionWord;
    private ArrayList<Object> standardCommands;
    private BufferedWriter out;
    private boolean actionGameplay; // if false, standard gameplay
    // todo - better way to handle this?

    public StagController(StagWorld newWorld, HashMap<String, List<Action>> actionsMap){
        StagWorld currWorld;
        locationMap = new HashMap<>();
        stagPlayers = new HashMap<>();
        this.actionsMap = actionsMap;
        currWorld = newWorld;
        fillStandardCommands();
        locationMap = currWorld.getAllWorldLocations();
    }

    public Player getCurrPlayer(){
        return this.currPlayer;
    }

    public void writeToClient(String string) throws IOException {
        out.write(string + "\n");
    }

    public void checkPlayer(String playerName) {
        if(!(stagPlayers.containsKey(playerName))){
            Player newPlayer = new Player(playerName);
            Location firstLoc = locationMap.get("start");
            newPlayer.setPlayerLocation(firstLoc);
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

    private void fillStandardCommands(){
        standardCommands = new ArrayList<>();
        standardCommands.add("inventory");
        standardCommands.add("inv");
        standardCommands.add("get");
        standardCommands.add("drop");
        standardCommands.add("goto");
        standardCommands.add("look");
    }

    private void setUserInput(String[] userInput){
        this.userInput = userInput;
    }

    private void preformAction() throws IOException {
        if(!(actionGameplay)){
            preformStandardAction();
        }
    }

    private void preformStandardAction() throws IOException {
        if(actionWord.equals("inventory") || actionWord.equals("inv")){
            InventoryAction action = new InventoryAction(currPlayer);
            action.executeAction(out);
        }
        if(actionWord.equals("get")){
            GetAction action = new GetAction(currPlayer, userInput);
            action.executeAction();
        }
        if(actionWord.equals("drop")){
            DropAction action = new DropAction(currPlayer, userInput);
            action.executeAction();
        }
        if(actionWord.equals("goto")){
            GotoAction action = new GotoAction(currPlayer, userInput, locationMap);
            action.executeAction(out);
        }
        if(actionWord.equals("look")){
            LookAction action = new LookAction(currPlayer, out);
            action.executeAction();
        }
    }

    // checks for at least one matching valid command
    public boolean checkValidInput(String[] userInput){
        for(String input : userInput) {
            if(actionsMap.containsKey(input)){
                actionWord = input;
                actionGameplay = true;
                return true;
            }

            else if(checkStandardCommand(input)){
                actionWord = input;
                actionGameplay = false;
                return true;
            }
        }
        return false;
    }

    private boolean checkStandardCommand(String input){ return standardCommands.contains(input); }

    public void endTurn(){ currPlayer.setPlayerLocation(currLocation); }
}
