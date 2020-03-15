import java.io.*;
import java.net.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.alexmerz.graphviz.*;
import com.alexmerz.graphviz.objects.*;

class StagServer {
    public static void main(String args[]) {
        if (args.length != 2) System.out.println("Usage: java StagServer <entity-file> <action-file>");
        else new StagServer(args[0], args[1], 8888);
    }

    public StagServer(String entityFilename, String actionFilename, int portNumber) {
        try {
            JSONArray actionContents = readActionsFile(actionFilename);
            readEntitiesFile(entityFilename);
            // call the parser (?) class sending across the JSONArray
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            while (true) acceptNextConnection(ss);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void acceptNextConnection(ServerSocket ss) {
        try {
            // Next line will block until a connection is received
            Socket socket = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            processNextCommand(in, out);
            out.close();
            in.close();
            socket.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void processNextCommand(BufferedReader in, BufferedWriter out) throws IOException {
        String line = in.readLine();
        out.write("You said... " + line + "\n");
    }

    /* HashMap<Graph, String> entitiesMap = new HashMap<>();
             entitiesMap.put(g, g.getId().getId());
             Set<Graph> key = entitiesMap.keySet(); */


}
