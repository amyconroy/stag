import Actions.Action;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;

class StagServer {
    public static void main(String args[]) {
        if (args.length != 2) System.out.println("Usage: java StagServer <entity-file> <action-file>");
        else new StagServer(args[0], args[1], 8888);
    }

    public StagServer(String entityFilename, String actionFilename, int portNumber) {
        try {
            StagParser stagParser = new StagParser();
            // todo check for empty file
            HashMap<String, List<Action>> actionsMap = stagParser.readActionsFile(actionFilename);
            StagWorld stagWorld = stagParser.readEntitiesFile(entityFilename);
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            StagController stagController = new StagController(stagWorld, actionsMap);
            // passing in the control because the control contains the whole world
            while (true) acceptNextConnection(ss, stagController);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void acceptNextConnection(ServerSocket ss, StagController stagController) {
        try {
            // Next line will block until a connection is received
            Socket socket = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            processNextCommand(in, out, stagController);
            out.close();
            in.close();
            socket.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void processNextCommand(BufferedReader in, BufferedWriter out, StagController stagController) throws IOException {
        String line = in.readLine();
        String playerName = line.substring(0, line.indexOf(':'));
        String [] userInput = line.split(" ");
        if(!stagController.checkValidInput(userInput))
            out.write("Please ensure you input a valid command." + line + "\n");
        else stagController.playGame(playerName, userInput, out);
    }
}