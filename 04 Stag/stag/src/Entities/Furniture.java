package Entities;

public class Furniture extends Location implements Entity {
    private String description;
    private String name;

    public Furniture(){
        description = null;
        name = null;
    }

    public void setDescription(String descriptionToSet){ description = descriptionToSet; }

    public String getDescription(){ return description; }

    public void setName(String name){ this.name = name; }

    public String getName(){ return name; }
}
