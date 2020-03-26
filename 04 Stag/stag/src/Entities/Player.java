package Entities;// extending entity or character? likely character.
import Entities.Artefact;
import Entities.Character;
import Entities.Location;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

// should it still extend character? undecided
// set the curr location each time that the player moves

public class Player extends Character {
    public Location playerLocation;
    public HashMap<String, Artefact> playerInventory;

    public Player(String playerName){
        super(); // calls the parent class - character
        super.setName(playerName); // is this right?
        playerLocation = null;
        playerInventory = new HashMap<>();
    }

    public HashMap<String, Artefact> getPlayerInventory(){ return playerInventory; }

    public void addToInventory(Artefact artefact){
        String artefactName = artefact.getName();
        playerInventory.put(artefactName, artefact); }

    public void removeFromInventory(String artefactName, Artefact artefact) { playerInventory.remove(artefactName, artefact); }

    public Location getPlayerLocation(){ return playerLocation; }

    public void setPlayerLocation(Location newLocation) { playerLocation = newLocation; }
}
