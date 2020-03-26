package Actions;

import Entities.*;
import java.util.*;
import java.io.*;

// moves from one location to another
public class GotoAction {
    private Player player;
    private Location location;
    private String[] userInput;
    private String newLocationName;
    private HashMap<String, Location> locationMap;
    private BufferedWriter out;

    public GotoAction(Player player, String[] userInput, HashMap<String, Location> locationMap){
        this.player = player;
        this.userInput = userInput;
        this.locationMap = locationMap;
        location = player.getPlayerLocation();
    }

    public void executeAction(BufferedWriter out) throws IOException {
        if(findLocation()){
            this.out = out;
            Location newLocation = locationMap.get(newLocationName);
            player.setPlayerLocation(newLocation);
        }
        else{
            out.write("No path to specified location.");
        }
    }

    private boolean findLocation() {
        ArrayList<String> paths = location.getPaths();

        for(String input : userInput){
            for(String path : paths){
                if(input.equals(path)){
                    newLocationName = path;
                    return true;
                }
            }
        }
        return false;
    }
}
