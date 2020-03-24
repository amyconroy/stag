import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import com.alexmerz.graphviz.Parser;
import java.util.*;
import java.io.*;

// superclass for all entities (players/characters/etc)
// location.getCurrLocation();
// should this be abstract or nah?

// create world - will fill locations, artefacts etc rather than in parser : then
// have a controller that is abstract that can call getAllLocations and get the hash
// map with all of the locations

public class createWorld extends StagParser {
    // one HashMap that contains all locations
    private HashMap <String, Location> locationMap;

    public createWorld(){
        locationMap = new HashMap<>();
    }

    public HashMap<String, Location> getAllWorldLocations(){ return locationMap; }

    public void getLocationGraphs(ArrayList<Graph> entityGraphs) {
        for (Graph map : entityGraphs) {
            ArrayList<Graph> worldLocations = map.getSubgraphs();
            createLocations(worldLocations);
            // sort the paths after
            ArrayList<Edge> paths = map.getEdges();
            for (Edge e : paths) {
                // set paths in game controller
                System.out.printf("Path from %s to %s\n", e.getSource().getNode().getId().getId(), e.getTarget().getNode().getId().getId());
            }
        }
        // need another method for creating the various graphs / subgraphs (multiple diff methods?)
    }

    // System.out.printf("\tfirst graph id = %s, name = %s\n", map.getId().getId(), nLoc.getId().getId());
    // use  Node nLoc = nodesLocation.get(0); then nLoc.getId().get() to get the name of the location
    public void createLocations(ArrayList<Graph> worldLocations){
        for (Graph map : worldLocations) {
            ArrayList<Node> nodesLocation = map.getNodes(false);
            Location currLocation = new Location();
            ArrayList<Graph> locationItems = map.getSubgraphs();
            currLocation = fillLocation(locationItems, currLocation);
            Node nodeName = nodesLocation.get(0);
            locationMap.put(nodeName.getId().getId(), currLocation);
            System.out.println("test create locations : " + locationMap);
        }
    }

    public Location fillLocation(ArrayList<Graph> locationItems, Location currLocation){
        // for as many items are there are in the location
        for (Graph map : locationItems) {
            Object newEntity = map.getId().getId();
            ArrayList<Node> entities = map.getNodes(false);
            for(Node locationEntity : entities) {
                if (newEntity.equals("artefacts")) {
                    currLocation.addArtefact(locationEntity.getId().getId(), locationEntity.getAttribute("description"));
                } else if (newEntity.equals("characters")) {
                    currLocation.addCharacter(locationEntity.getId().getId(), locationEntity.getAttribute("description"));
                } else if (newEntity.equals("furniture")) {
                    currLocation.addFurniture(locationEntity.getId().getId(), locationEntity.getAttribute("description"));
                }
            }
        }
        return currLocation;
    }

}
