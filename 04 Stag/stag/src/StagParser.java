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
import java.util.HashMap;


public class StagParser {
    public static JSONArray readActionsFile(String actionFilename) {
        // parse exception inherent?
        // server exit if the file can't be found
        JSONParser jsonParser = new JSONParser();
        JSONArray actionsArray = null;
        try {
            FileReader reader = new FileReader(actionFilename);
            JSONObject jsonObj = (JSONObject) jsonParser.parse(reader);
            actionsArray = (JSONArray)jsonObj.get("actions");
            System.out.println("test : " + actionsArray);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return actionsArray;
    }

    public static void readEntitiesFile(String entitiesFilename){
        try {
            Parser parser = new Parser();
            FileReader reader = new FileReader(entitiesFilename);
            parser.parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void readEntitiesFile(String entityFilename){
        try {
            Parser parser = new Parser();
            FileReader reader = new FileReader(entityFilename);
            parser.parse(reader);
            ArrayList<Graph> graphs = parser.getGraphs();
            // returns a list of all subgraphs
            ArrayList<Graph> subGraphs = graphs.get(0).getSubgraphs();
            // cycles through until hit all subgraphs
            for(Graph g : subGraphs){
                // get the subgraph of the subgraph
                new ArrayList<Graph> subGraphs = g.getSubgraphs();
                for (Graph g1 : subGraphs){
                    ArrayList<Node> nodesLocation = g1.getNodes(false);
                    Node nLoc = nodesLocation.get(0);
                    System.out.printf("\tid = %s, name = %s\n",g1.getId().getId(), nLoc.getId().getId());
                    ArrayList<Graph> subGraphs2 = g1.getSubgraphs();
                    for (Graph g2 : subGraphs2) {
                        System.out.printf("\t\tid = %s\n", g2.getId().getId());
                        ArrayList<Node> nodesEnt = g2.getNodes(false);
                        for (Node nEnt : nodesEnt) {
                            System.out.printf("\t\t\tid = %s, description = %s\n", nEnt.getId().getId(), nEnt.getAttribute("description"));
                        }
                    }
                }
                ArrayList<Edge> edges = g.getEdges();
                for (Edge e : edges){
                    System.out.printf("Path from %s to %s\n", e.getSource().getNode().getId().getId(), e.getTarget().getNode().getId().getId());
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe);
        } catch (com.alexmerz.graphviz.ParseException pe) {
            System.out.println(pe);
        }
    }
}
