import java.util.ArrayList;
import java.util.HashMap;

public class StagController {
    public HashMap<String, Location> locationMap;
    public HashMap<String, Player> stagPlayers;
    public StagWorld currWorld;
    public Player currPlayer;

    StagController(StagWorld newWorld){
        currWorld = new StagWorld();
        currWorld = newWorld;
        locationMap = new HashMap<>();
        locationMap = currWorld.getAllWorldLocations();
        stagPlayers = new HashMap<>();
    }

    public void checkPlayer(String playerName){
        if(!(stagPlayers.containsKey(playerName))){
            Player newPlayer = new Player(playerName);
            stagPlayers.put(playerName, newPlayer);
        }
        currPlayer = stagPlayers.get(playerName);
    }

    public void playGame(String playerName){
        checkPlayer(playerName);
    }
}
