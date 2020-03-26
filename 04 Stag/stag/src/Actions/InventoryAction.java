package Actions;
import Entities.*;
import java.util.*;
import java.io.*;

// lists all of the artefacts currently being carried by the player
public class InventoryAction {
    private Player player;
    private BufferedWriter out;
    private HashMap<String, Artefact> userInv;

    public InventoryAction(Player player) {
        this.player = player;
    }

    public void executeAction(BufferedWriter out) throws IOException {
        this.out = out;
        getInventory();
        printInventory();
    }

    private void getInventory(){
        userInv = player.getPlayerInventory();
    }

    public void printInventory() throws IOException {
        if(!(userInv.isEmpty())){
            out.write("YOUR INVENTORY CONTAINS: " + userInv.keySet() + "\n");
        }
        else{
            out.write("YOUR INVENTORY is empty.\n");
        }
    }

}
