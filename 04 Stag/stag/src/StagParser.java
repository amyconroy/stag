import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;


public class StagParser {
    public static JSONArray readActionsFile(String actionFilename) {
        // parse exception inherent?
        // server exit if the file can't be found
        JSONParser jsonParser = new JSONParser();
        JSONArray actionsArray = null;
        try {
            FileReader reader = new FileReader(actionFilename);
            JSONObject jsonObj = (JSONObject) jsonParser.parse(reader);
            actionsArray = (JSONArray) jsonObj.get("actions");
            parseActionsArray(actionsArray);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return actionsArray;
    }


    public static void parseActionsArray(JSONArray actionsArray){
        for (Object actionsObj : actionsArray) {
            JSONObject JSONAction = (JSONObject) actionsObj;
                new ArrayList actionsTriggers = new ArrayList<String> JSONAction.get("triggers").toString();
                System.out.println("test" + actionsTriggers);
                /*
                ArrayList<String> actionsTriggers = createActionsArray(JSONAction, "triggers");
                ArrayList<String> actionsSubjects = createActionsArray(JSONAction, "subjects");
                ArrayList<String> actionsConsumed = createActionsArray(JSONAction, "consumed");
                ArrayList<String> actionsProduced = createActionsArray(JSONAction, "produced");
                ArrayList<String> actionsNarration = createActionsArray(JSONAction, "narration");  */
        }
    }

/*
    public ArrayList<String> createActionsArray(JSONObject actionObject, String key){
        ArrayList actionsArray = new ArrayList<String>();

        actionsArray = actionObject.key;

        return actionsArray;
    } */

    public static ArrayList<Graph> getGraphs(String entityFilename) {
        ArrayList<Graph> graphs = null;
        try {
            Parser parser = new Parser();
            FileReader reader = new FileReader(entityFilename);
            parser.parse(reader);
            graphs = parser.getGraphs();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (com.alexmerz.graphviz.ParseException e) {
            e.printStackTrace();
        }
        return graphs;
    }

    public static ArrayList<Graph> getSubGraphs(ArrayList<Graph> graphs, String entityFilename){
        ArrayList<Graph> subGraphs = null;
        try {
            // repetative code ...
            Parser parser = new Parser();
            FileReader reader = new FileReader(entityFilename);
            parser.parse(reader);
            subGraphs = graphs.get(0).getSubgraphs();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (com.alexmerz.graphviz.ParseException e) {
            e.printStackTrace();
        }
        return subGraphs;
    }

    public static /* ArrayList<Graph> */ void createLocationList(ArrayList<Graph> graphs, ArrayList<Graph> subGraphs) {
        for (Graph g : subGraphs) {
            ArrayList<Graph> subGraphs1 = g.getSubgraphs();
            for (Graph g1 : subGraphs1) {
                ArrayList<Node> nodesLocation = g1.getNodes(false);
                Node nLoc = nodesLocation.get(0);
                System.out.printf("\tid = %s, name = %s\n", g1.getId().getId(), nLoc.getId().getId());
                ArrayList<Graph> subGraphs2 = g1.getSubgraphs();
                for (Graph g2 : subGraphs2) {
                    System.out.printf("\t\tid = %s\n", g2.getId().getId());
                    /*
                    ArrayList<Node> nodesEnt = g2.getNodes(false);
                    for (Node nEnt : nodesEnt) {
                        System.out.printf("\t\t\tid = %s, description = %s\n", nEnt.getId().getId(), nEnt.getAttribute("description")); */
                    }
                }
            }
        /*
            ArrayList<Edge> edges = g.getEdges();
            for (Edge e : edges) {
                System.out.printf("Path from %s to %s\n", e.getSource().getNode().getId().getId(), e.getTarget().getNode().getId().getId());
            } */
    }
    // need another method for creating the various graphs / subgraphs (multiple diff methods?)
}
