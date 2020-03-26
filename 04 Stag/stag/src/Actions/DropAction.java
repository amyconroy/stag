package Actions;

import Entities.*;
import java.util.*;

public class DropAction {
    private Player player;
    private Artefact artefactDrop;
    private String artefactName;
    private String[] userInput;
    private HashMap<String, Artefact> userInv;
    private Location location;

    public DropAction(Player player, String[] userInput) {
        this.player = player;
        this.userInput = userInput;
        userInv = new HashMap<>();
        location = player.getPlayerLocation();
    }

    public void executeAction(){
        userInv = player.getPlayerInventory();
        if(checkInventory()){
            artefactDrop = userInv.get(artefactName);
            artefactDrop.collectArtefact();
            String description = artefactDrop.getDescription();
            player.removeFromInventory(artefactName, artefactDrop);
            location.addArtefact(artefactName, description);
        }
    }

    private boolean checkInventory(){
        for(String input : userInput){
            for(String key : userInv.keySet()){
                if(key.equals(input)){
                    artefactName = input;
                    return true;
                }
            }
        }
        System.out.println("Specified artefact is not present in your inventory.");
        return false;
    }
}
