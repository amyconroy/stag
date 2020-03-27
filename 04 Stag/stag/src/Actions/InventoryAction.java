package Actions;
import Entities.Artefact;
import Entities.Player;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

// lists all of the artefacts currently being carried by the player
public class InventoryAction implements Gameplay{
    private Player player;
    private BufferedWriter out;
    private HashMap<String, Artefact> userInv;

    public InventoryAction(Player player) {
        this.player = player;
    }

    public void setAction(BufferedWriter out) throws IOException {
        this.out = out;
        executeAction();
    }

    public void executeAction() throws IOException {
        getInventory();
        printInventory();
    }

    private void getInventory(){
        userInv = player.getPlayerInventory();
    }

    private void printInventory() throws IOException {
        if(!(userInv.isEmpty())) out.write("YOUR INVENTORY CONTAINS: " + userInv.keySet() + "\n");
        else out.write("YOUR INVENTORY is empty.\n"); }
}
