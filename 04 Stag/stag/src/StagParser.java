import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Graph;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import Actions.Action;

// todo THINGS TO FIX: server exit if the file can't be found
// todo switch it to <String, List<Action>>
//  //then if the keyword already exists, add the action to the list and search by subjects

public class StagParser {
    public HashMap<String, List<Action>> actionsMap = new HashMap<>();

    public HashMap<String, List<Action>> readActionsFile(String actionFilename) {
        JSONParser jsonParser = new JSONParser();
        JSONArray actionArray = null;
        try {
            FileReader reader = new FileReader(actionFilename);
            JSONObject jsonObj = (JSONObject) jsonParser.parse(reader);
            actionArray = (JSONArray) jsonObj.get("actions");
            parseActionsArray(actionArray);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        System.out.println(actionsMap.keySet());
        return actionsMap;
    }

    private void parseActionsArray(JSONArray actionsArray) {
        Action actionObject = null;
        String[] triggerKeys = null;

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
                actionObject = createActionObject(JSONAction, actionKey);
            }
            assert triggerKeys != null;
            fillActionsMap(actionObject, triggerKeys);
        }
    }

    private void fillActionsMap(Action action, String[] triggerKeys){
        List<Action> actions = new ArrayList<>();

        for(String value : triggerKeys){
            // catching blank triggers
            if(value.trim().length() > 0) {
                if(actionsMap.containsKey(value)) {
                    actions = actionsMap.get(value);
                    actions.add(action);
                    actionsMap.put(value, actions);
                }
                else{
                    actions.add(action);
                    actionsMap.put(value, actions);
                }
            }
        }
    }

    // creates the action object - with each being a hashset
    private Action createActionObject(JSONObject JSONAction, String actionKey) {
        HashSet<String> subjects = null;
        HashSet<String> consumed = null;
        HashSet<String> produced = null;
        HashSet<String> newSet = null;
        String narration = null;

        Object value = JSONAction.get(actionKey);
        String stringValue = value.toString();
        String[] keys = stringValue.split("\\W+");
        newSet = createHashSet(actionKey, keys);
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
        return new Action(subjects, consumed, produced, narration);
    }

    private HashSet<String> createHashSet(String actionKey, String[] keys){
        HashSet<String> newSet = new HashSet<>();
        for(String value : keys){
            if(value.trim().length() > 0){
                newSet.add(value);
            }
        }
        return newSet;
    }

    public StagWorld readEntitiesFile(String entityFilename) {
        ArrayList<Graph> graphs = null;
        ArrayList<Graph> entityGraphs = null;
        StagWorld newWorld = new StagWorld();
        try {
            Parser parser = new Parser();
            FileReader reader = new FileReader(entityFilename);
            parser.parse(reader);
            graphs = parser.getGraphs();
            entityGraphs = graphs.get(0).getSubgraphs();
            newWorld.createLocationGraphs(entityGraphs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (com.alexmerz.graphviz.ParseException e) {
            e.printStackTrace();
        }
        return newWorld;
    }
}
