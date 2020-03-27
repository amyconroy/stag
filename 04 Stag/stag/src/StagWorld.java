import Entities.Location;
import java.util.HashMap;

// generate the world with locations and paths
public class StagWorld {
    private HashMap <String, Location> locationMap;
    private Location startLocation;

    public StagWorld(){
        locationMap = new HashMap<>();
    }

    public HashMap<String, Location> getAllWorldLocations(){ return locationMap; }

    public Location getStart() {
        return startLocation;
    }

    public Location startLocation(Location location){
        startLocation = location;
        location.setAsStart();
        return startLocation;
    }

    public void setLocationMap(HashMap<String, Location> locationMap){
        this.locationMap = locationMap;
    }
}
