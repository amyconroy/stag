package Actions;
import Entities.Artefact;
import Entities.Location;
import Entities.Player;
import java.util.HashMap;

public class DropAction implements Gameplay {
    private Player player;
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
            Artefact artefactDrop = userInv.get(artefactName);
            String description = artefactDrop.getDescription();
            // remove the artefact from player and add to location
            player.removeFromInventory(artefactName, artefactDrop);
            location.addArtefact(artefactName, description);
        }
    }

    // ensure artefact present in inv to drop
    private boolean checkInventory(){
        for(String input : userInput)
            for (String key : userInv.keySet())
                if (key.equals(input)) {
                    artefactName = input;
                    return true;
                }
        System.out.println("Specified artefact is not present in your inventory.");
        return false;
    }
}
