package Actions;
import Entities.*;
import Entities.Character;

import java.util.*;


// picks up a specified artefact from current location and put it into players inv
public class GetAction {
    private Artefact requestedArtefact;
    private String artefactName;
    private Location location;
    private HashMap<String, Artefact> artefacts;
    private String[] userInput;
    private Player player;

    public GetAction(Player player, String[] userInput) {
        this.userInput = userInput;
        this.player = player;
        artefacts = new HashMap<>();
        location = player.playerLocation;
    }

    public void executeAction(){
        artefacts = location.getAllArtefacts();
        if(findArtefact()){
            requestedArtefact.collectArtefact();
            putInInventory();
            location.removeArtefact(artefactName);
        }
    }

    private boolean findArtefact(){
        for(String input : userInput){
            if(artefacts.containsKey(input)){
                requestedArtefact = artefacts.get(input);
                artefactName = input;
                return true;
            }
        }
        System.out.println("Requested artefact is not present in current location.");
        return false;
    }

    private void putInInventory(){
        player.addToInventory(requestedArtefact);
    }
}
