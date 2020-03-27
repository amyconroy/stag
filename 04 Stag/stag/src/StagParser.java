import Entities.Location;
import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import Actions.Action;
import java.io.File;

public class StagParser {
    public HashMap<String, List<Action>> actionsMap = new HashMap<>();
    private HashMap <String, Location> locationMap = new HashMap<>();
    boolean startFlag = true;
    StagWorld stagWorld = new StagWorld();

    public HashMap<String, List<Action>> readActionsFile(String actionFilename) {
        JSONParser jsonParser = new JSONParser();
        JSONArray actionArray;
        try {
            FileReader reader = new FileReader(actionFilename);
            checkFileTest(actionFilename);
            JSONObject jsonObj = (JSONObject) jsonParser.parse(reader);
            actionArray = (JSONArray) jsonObj.get("actions");
            parseActionsArray(actionArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(actionsMap.keySet());
        return actionsMap;
    }

    private void checkFileTest(String filename) throws Exception{
        File file = new File(filename);

        if(file.length() == 0) throw new Exception("File length is zero.");
    }

    private void parseActionsArray(JSONArray actionsArray) {
        String[] triggerKeys = null;
        HashSet<String> subjects = null;
        HashSet<String> consumed = null;
        HashSet<String> produced = null;
        HashSet<String> newSet;
        String narration = null;

        for (Object actionsObj : actionsArray) {
            JSONObject JSONAction = (JSONObject) actionsObj;
            var actionSet = JSONAction.keySet();
            // iterate through each part of the action, separating triggers to be used as key
            for (String actionKey : (Iterable<String>) actionSet) {
                if(actionKey.equals("triggers")){
                    Object value = JSONAction.get(actionKey);
                    String stringValue = value.toString();
                    triggerKeys = stringValue.split("\\W+");
                }
                Object value = JSONAction.get(actionKey);
                String stringValue = value.toString();
                String[] keys = stringValue.split("\\W+");
                newSet = createHashSet(keys);
                switch (actionKey) {
                    case "subjects":
                        subjects = newSet;
                        break;
                    case "consumed":
                        consumed = newSet;
                        break;
                    case "produced":
                        produced = newSet;
                        break;
                    case "narration":
                        narration = stringValue;
                        break;
                }
            }
            Action action = new Action(subjects, consumed, produced, narration);
            assert triggerKeys != null;
            fillActionsMap(action, triggerKeys);
        }
    }


    private void fillActionsMap(Action action, String[] triggerKeys){
        List<Action> actions = new ArrayList<>();

        // catching blank triggers
        for(String value : triggerKeys)
            if (value.trim().length() > 0) if (actionsMap.containsKey(value)) {
                // adding to an already existing trigger words array list
                actions = actionsMap.get(value);
                actions.add(action);
                actionsMap.put(value, actions);
            } else {
                actions.add(action);
                actionsMap.put(value, actions);
            }
    }

    private HashSet<String> createHashSet(String[] keys){
        HashSet<String> newSet = new HashSet<>();
        for(String value : keys){
            if(value.trim().length() > 0){
                newSet.add(value);
            }
        }
        return newSet;
    }

    public StagWorld createWorld(String entityFilename) throws Exception {
        ArrayList<Graph> graphs;
        ArrayList<Graph> entityGraphs;
        try {
            Parser parser = new Parser();
            FileReader reader = new FileReader(entityFilename);
            checkFileTest(entityFilename);
            parser.parse(reader);
            graphs = parser.getGraphs();
            entityGraphs = graphs.get(0).getSubgraphs();
            createLocationGraphs(entityGraphs);
            stagWorld.setLocationMap(locationMap);

        } catch (FileNotFoundException | com.alexmerz.graphviz.ParseException e) {
            e.printStackTrace();
        }
        return stagWorld;
    }

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
            currLocation.setDescription(description);
            if(startFlag){
                currLocation = stagWorld.startLocation(currLocation);
                startFlag = false;
            }
            System.out.println("test : " + nodeName.getId().getId());
            System.out.println("currloc : " + currLocation);
            // add name of location, and location object to hashmap
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
