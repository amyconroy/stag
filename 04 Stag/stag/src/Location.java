import java.util.*;
// one hash map with location - graph of paths - then separate hash maps with all the entities
// keep track of paths to other locations
// have a this location?

public class Location extends createWorld {
    public Location location;
    public HashMap<String, Artefact> localArtefacts;
    public HashMap<String, Furniture> localFurniture;
    public HashMap<String, Character> localCharacters;

    public Location(){
        localCharacters = new HashMap<>();
        localFurniture = new HashMap<>();
        localArtefacts = new HashMap<>(); }

    public Location getCurrLocation(){ return this.location; }

    public void addArtefact(String artefactName, String description){
        Artefact currArtefact = new Artefact();
        currArtefact.setName(artefactName);
        currArtefact.setDescription(description);
        localArtefacts.put(artefactName, currArtefact); }

    public HashMap <String, Artefact> getArtefacts(){ return localArtefacts; }

    public void addFurniture(String furnitureType, String description){
        Furniture currFurniture = new Furniture();
        currFurniture.setName(furnitureType);
        currFurniture.setDescription(description);
        localFurniture.put(furnitureType, currFurniture); }

    public HashMap<String, Furniture> getFurniture(){ return localFurniture; }

    public void addCharacter(String characterName, String description){
        Character currCharacter = new Character();
        currCharacter.setName(characterName);
        currCharacter.setDescription(description);
        localCharacters.put(characterName, currCharacter);
    }
    public HashMap<String, Character> getCharacters(){ return localCharacters; }
}
