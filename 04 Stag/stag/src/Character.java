public class Character {
    public String characterName;
    public String description;

    public Character(){
        characterName = null;
        description = null; }

    public void setDescription(String descriptionToSet){ description = descriptionToSet; }

    public String getDescription(){ return description; }

    public void setName(String name){ characterName = name; }

    public String getName(){ return characterName; }
}
