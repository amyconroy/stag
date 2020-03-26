import Entities.Location;
import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;

import java.util.*;

// todo superclass for all entities (players/characters/etc)
// location.getCurrLocation();
// should this be abstract or nah?

// todo create world - will fill locations, artefacts etc rather than in parser : then
// have a controller that is abstract that can call getAllLocations and get the hash
// map with all of the locations

public class StagWorld {
    // one HashMap that contains all locations
    private HashMap <String, Location> locationMap;

    public StagWorld(){
        locationMap = new HashMap<>();
    }

    // i feel like this function is irrelevant now ..?
    public HashMap<String, Location> getAllWorldLocations(){ return locationMap; }

    public void createLocationGraphs(ArrayList<Graph> entityGraphs) {
        for (Graph map : entityGraphs) {
            ArrayList<Graph> worldLocations = map.getSubgraphs();
            createLocations(worldLocations);
            createPaths(map);
        }
    }

    public void createLocations(ArrayList<Graph> worldLocations){
        for (Graph map : worldLocations) {
            ArrayList<Node> nodesLocation = map.getNodes(false);
            Location currLocation = new Location();
            ArrayList<Graph> locationItems = map.getSubgraphs();
            currLocation = fillLocation(locationItems, currLocation);
            Node nodeName = nodesLocation.get(0);
            String description = nodeName.getAttribute("description");
            currLocation.setLocDescription(description);
            locationMap.put(nodeName.getId().getId(), currLocation);
        }
    }

    public void createPaths(Graph map){
        ArrayList<Edge> paths = map.getEdges();
        for (Edge e : paths)
            for (String key : locationMap.keySet())
                if (e.getSource().getNode().getId().getId().equals(key)) {
                    Location location = locationMap.get(key);
                    location.addPath(e.getTarget().getNode().getId().getId());
                }
    }

    public Location fillLocation(ArrayList<Graph> locationItems, Location currLocation){
        for (Graph map : locationItems) {
            Object newEntity = map.getId().getId();
            ArrayList<Node> entities = map.getNodes(false);
            for(Node locationEntity : entities) {
                if (newEntity.equals("artefacts"))
                    currLocation.addArtefact(locationEntity.getId().getId(), locationEntity.getAttribute("description"));
                if (newEntity.equals("characters"))
                    currLocation.addCharacter(locationEntity.getId().getId(), locationEntity.getAttribute("description"));
                if (newEntity.equals("furniture"))
                    currLocation.addFurniture(locationEntity.getId().getId(), locationEntity.getAttribute("description"));
            }
        }
        return currLocation;
    }

}
