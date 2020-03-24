import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import com.alexmerz.graphviz.objects.Edge;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;


// THINGS TO FIX: server exit if the file can't be found

public class StagParser {
    public void readActionsFile(String actionFilename) {
        JSONParser jsonParser = new JSONParser();
        ArrayList<HashMap<String, Object>> actionsList = new ArrayList<>();
        JSONArray actionArray = null;
        try {
            FileReader reader = new FileReader(actionFilename);
            JSONObject jsonObj = (JSONObject) jsonParser.parse(reader);
            actionArray = (JSONArray) jsonObj.get("actions");
            actionsList = parseActionsArray(actionArray);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        // populate world with actions list - give actions Class the actions list?
    }

    public ArrayList<HashMap<String, Object>> parseActionsArray(JSONArray actionsArray) {
        ArrayList<HashMap<String, Object>> actionsList = new ArrayList<>();
        for (Object actionsObj : actionsArray) {
            JSONObject JSONAction = (JSONObject) actionsObj;
            HashMap<String, Object> actionsTriggers = createActionsMap(JSONAction);
            actionsList.add(actionsTriggers);
        }
        System.out.println("ACTIONSLIST : " + actionsList);
        return actionsList;
    }

    public HashMap<String, Object> createActionsMap(JSONObject JSONAction) {
        HashMap<String, Object> actionsMap = new HashMap<>();
        Set actionSet = JSONAction.keySet();
        Iterator<String> actionKeys = actionSet.iterator();
        while (actionKeys.hasNext()) {
            String actionKey = actionKeys.next();
            actionsMap.put(actionKey, JSONAction.get(actionKey));
        }
        System.out.println("ACTIONSMAP : " + actionsMap);
        return actionsMap;
    }

    public void readEntitiesFile(String entityFilename) {
        ArrayList<Graph> graphs = null;
        ArrayList<Graph> entityGraphs = null;
        try {
            Parser parser = new Parser();
            FileReader reader = new FileReader(entityFilename);
            parser.parse(reader);
            graphs = parser.getGraphs();
            entityGraphs = graphs.get(0).getSubgraphs();
            new createWorld();
            createWorld.getLocationGraphs(entityGraphs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (com.alexmerz.graphviz.ParseException e) {
            e.printStackTrace();
        }
    }
}
