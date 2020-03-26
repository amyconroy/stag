package Actions;

import Entities.Character;
import Entities.Player;
import Entities.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

// reports entities in the current location + paths to other locations
// todo - add the exception; maybe making a print method?

public class LookAction {
    private Player player;
    private Location location;
    private BufferedWriter out;

    public LookAction(Player player, BufferedWriter out){
        this.out = out;
        this.player = player;
    }

    public void executeAction() throws IOException {
        location = player.getPlayerLocation();
        giveLocationDescription();
        reportArtefacts();
        reportCharacters();
        reportFurniture();
        reportPaths();
    }

    private void giveLocationDescription() throws IOException{
        String locDescription = location.getLocDescription();
        out.write("This location is : " + locDescription + "\n\n");
    }

    private void reportArtefacts() throws IOException {
        HashMap<String, Artefact> artefacts = location.getAllArtefacts();
        if(!(artefacts.isEmpty())){
            out.write("ARTEFACTS in current location : " + artefacts.keySet() + ".\n");
        }
        else{
            out.write("There are no ARTEFACTS in current location.\n");
        }
    }

    private void reportCharacters() throws IOException {
        HashMap<String, Character> characters = location.getAllCharacters();
        if(!(characters.isEmpty())){
            out.write("CHARACTERS in current location : " + characters.keySet() + ".\n");
        }
        else{
            out.write("There are no CHARACTERS in current location.\n");
        }
    }

    private void reportFurniture() throws IOException {
        HashMap<String, Furniture> furniture = location.getAllFurniture();
        if(!(furniture.isEmpty())){
            out.write("FURNITURE in current location : " + furniture.keySet() + ".\n");
        }
        else{
            out.write("There is no FURNITURE in current location.\n");
        }
    }

    private void reportPaths() throws IOException {
        ArrayList<String> paths = location.getPaths();
        if(!(paths.isEmpty())){
            out.write("PATHS in current location : " + paths + ".\n");
        }
        else{
            out.write("There are no PATHS in current location.\n");
        }
    }
}
