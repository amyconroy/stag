package Entities;
import java.util.HashMap;

public class Player extends Character implements Entity {
    public Location playerLocation;
    public Location startLocation;
    public HashMap<String, Location> locationMap;
    public HashMap<String, Artefact> playerInventory;
    public int health;

    public Player(String playerName, HashMap<String, Location> locationMap){
        super();
        super.setName(playerName);
        playerLocation = null;
        playerInventory = new HashMap<>();
        health = 3;
        this.locationMap = locationMap;
        getFirstLocation();
    }

    public int getHealth(){ return health; }

    public void increaseHealth(){ health++; }

    public void decreaseHealth(){ health--; }

    public boolean checkIfDead(){
        if(health == 0) {
            killPlayer();
            return true;
        }
    return false; }

    public void killPlayer(){
        playerInventory = new HashMap<>();
        health = 3; }

    private void getFirstLocation(){
        for(String name : locationMap.keySet()){
            Location loc = locationMap.get(name);
            if(loc.checkIfStart()) startLocation = loc; }
    }

    public HashMap<String, Artefact> getPlayerInventory(){ return playerInventory; }

    public boolean checkInInventory(String name){
        for(String key : playerInventory.keySet())
            if (key.equals(name)) return true;
        return false; }

    public void addToInventory(Artefact artefact){
        String artefactName = artefact.getName();
        playerInventory.put(artefactName, artefact); }

    public void removeFromInventory(String artefactName, Artefact artefact) { playerInventory.remove(artefactName, artefact); }

    public Location getPlayerLocation(){ return playerLocation; }

    public void setPlayerLocation(Location newLocation) { playerLocation = newLocation; }
}
