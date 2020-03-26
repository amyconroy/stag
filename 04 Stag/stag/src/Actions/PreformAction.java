package Actions;
import java.util.HashSet;

public abstract class PreformAction {
    private HashSet<String> subjects;
    private HashSet<String> consumed;
    private HashSet<String> produced;
    private String narration;

    public void setAction(Action action){ }

    public void executeAction(){ }

    public void identifySubjects(){ }

    public void identifyConsumed(){ }

    public void identifyProduced(){ }
}
