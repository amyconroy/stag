package Entities;

public class Artefact extends Location implements Entity{
    private String description;
    private String name;

    public Artefact(){
        description = null;
        name = null;
    }

    public void setDescription(String descriptionToSet){ description = descriptionToSet; }

    public String getDescription(){ return description; }

    public void setName(String name){ this.name = name; }

    public String getName(){ return name; }
}
