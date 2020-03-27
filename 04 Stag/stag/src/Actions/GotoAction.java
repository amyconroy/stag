package Actions;
import Entities.Location;
import Entities.Player;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GotoAction implements Gameplay {
    private Player player;
    private Location location;
    private String[] userInput;
    private String newLocationName;
    private HashMap<String, Location> locationMap;

    public GotoAction(Player player, String[] userInput, HashMap<String, Location> locationMap){
        this.player = player;
        this.userInput = userInput;
        this.locationMap = locationMap;
        location = player.getPlayerLocation();
    }

    public void setAction(BufferedWriter out) throws IOException {
        if(findLocation()){
            executeAction();
        }
        else out.write("No path to specified location.");
    }

    public void executeAction(){
        Location newLocation = locationMap.get(newLocationName);
        player.setPlayerLocation(newLocation);
    }

    // ensure that there is a path to the specified location
    private boolean findLocation() {
        ArrayList<String> paths = location.getPaths();

        for(String input : userInput)
            for (String path : paths)
                if (input.equals(path)) {
                    newLocationName = path;
                    return true;
                }
        return false;
    }
}
