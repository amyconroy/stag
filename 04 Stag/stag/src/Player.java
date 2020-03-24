// extending entity or character? likely character.
import java.util.*;

// should it still extend character? undecided
public class Player extends Character {
    public Player currPlayer;
    public Location playerLocation;
    public ArrayList<Artefact> playerInventory;

    public Player(){
        currPlayer = null;
        playerLocation = null;
        playerInventory = new ArrayList<>();
    }

    public Player getCurrentPlayer(){ return currPlayer; }

    public ArrayList<Artefact> getPlayerInventory(){ return playerInventory; }

    // search Player Inventory ? - is that item in inventory
    // remove item from player inventory ...

    public Location getPlayerLocation(){ return playerLocation; }

    public void setPlayerLocation(){ playerLocation = playerLocation.getCurrLocation(); }
}
