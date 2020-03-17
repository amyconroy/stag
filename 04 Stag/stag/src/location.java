// should this be a public interface?
// to map all the properties and methods - specific
// to the location
//

public class location extends entity {
    public Object location;
// one hash map with location - graph of paths - then separate hash maps with all the entities
    // keep track of paths to other locations
        // have a this location?
    private location(){new location();}
/*
    public location() {

    } */

    public Object getCurrLocation(){
        return this.location;
    }

    // curr characters that are in this location : set each character to have a location ?
    // artefacts that are in the location : array
    // furniture that is at that location

}
