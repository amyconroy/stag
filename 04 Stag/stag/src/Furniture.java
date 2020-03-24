public class Furniture extends Location{
    public String description;
    public String furnitureName;

    public Furniture(){
        description = null;
        furnitureName = null;
    }

    public void setDescription(String descriptionToSet){ description = descriptionToSet; }

    public String getDescription(){ return description; }

    public void setName(String name){ furnitureName = name; }

    public String getName(){ return furnitureName; }
}
