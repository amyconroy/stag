public class Artefact {
    public boolean isCollected;
    public String description;
    public String artefactName;

    public Artefact(){
        isCollected = false;
        description = null;
        artefactName = null;
    }

    public void setDescription(String descriptionToSet){ description = descriptionToSet; }

    public String getDescription(){ return description; }

    public void setName(String name){ artefactName = name; }

    public String getName(){ return artefactName; }

    public boolean checkIfCollected(){ return isCollected; }

    private void collectArtefact(){ isCollected = true; }

    private void isPutBack(){ isCollected = false; }
}
