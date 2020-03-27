package Entities;
import java.util.ArrayList;
import java.util.HashMap;

public class Location {
    private String description;
    private ArrayList<String> localPaths;
    private HashMap<String, Artefact> localArtefacts;
    private HashMap<String, Furniture> localFurniture;
    private HashMap<String, Character> localCharacters;
    private boolean startFlag = false;

    public Location(){
        localCharacters = new HashMap<>();
        localFurniture = new HashMap<>();
        localArtefacts = new HashMap<>();
        localPaths = new ArrayList<>(); }

    public void addPath(String path){ localPaths.add(path); }

    public ArrayList<String> getPaths() { return localPaths; }

    public void setDescription(String description){ this.description = description; }

    public String getDescription(){ return description; }

    public void setAsStart(){ startFlag = true; }

    public boolean checkIfStart(){ return startFlag; }

    public HashMap <String, Artefact> getAllArtefacts(){ return localArtefacts; }

    public void addArtefact(String artefactName, String description){
        Artefact currArtefact = new Artefact();
        currArtefact.setName(artefactName);
        currArtefact.setDescription(description);
        localArtefacts.put(artefactName, currArtefact); }

    public boolean checkForArtefact(String request){
        for(String name : localArtefacts.keySet()) if (name.equals(request)) return true;
        return false; }

    public Artefact getArtefact(String artefactName){
        Artefact requestedArtefact;
        requestedArtefact = localArtefacts.get(artefactName);
        return requestedArtefact; }

    public void removeArtefact(String artefactName){ localArtefacts.remove(artefactName); }

    public void addFurniture(String furnitureType, String description){
        Furniture currFurniture = new Furniture();
        currFurniture.setName(furnitureType);
        currFurniture.setDescription(description);
        localFurniture.put(furnitureType, currFurniture); }

    public boolean checkForFurniture(String request){
        for(String name : localFurniture.keySet()) if (name.equals(request)) return true;
        return false; }

    public Furniture getFurniture(String furnitureName){
        Furniture requestedFurniture;
        requestedFurniture = localFurniture.get(furnitureName);
        return requestedFurniture; }

    public HashMap<String, Furniture> getAllFurniture(){ return localFurniture; }

    public void removeFurniture(String furnitureName){ localCharacters.remove(furnitureName); }

    public void addCharacter(String characterName, String description){
        Character currCharacter = new Character();
        currCharacter.setName(characterName);
        currCharacter.setDescription(description);
        localCharacters.put(characterName, currCharacter); }

    public void removeCharacter(String characterName){ localCharacters.remove(characterName); }

    public HashMap<String, Character> getAllCharacters(){ return localCharacters; }

    public boolean checkForCharacter(String request){
        for(String name : localCharacters.keySet()) if (name.equals(request)) return true;
        return false; }

    public Character getCharacter(String characterName){
        Character requestedCharacter;
        requestedCharacter = localCharacters.get(characterName);
        return requestedCharacter; }
}
