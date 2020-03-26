package Actions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

public class Action {
    private HashSet<String> subjects;
    private HashSet<String> consumed;
    private HashSet<String> produced;
    private String narration;

    public Action(HashSet<String> subjects, HashSet<String> consumed,
           HashSet<String> produced, String narration){
        this.subjects = subjects;
        this.consumed = consumed;
        this.produced = produced;
        this.narration = narration;
    }

    public HashSet<String> getSubjects(){ return subjects; }

    public HashSet<String> getConsumed(){ return consumed; }

    public HashSet <String> getProduced(){ return produced; }

    public String getNarration(){ return narration; }
}
