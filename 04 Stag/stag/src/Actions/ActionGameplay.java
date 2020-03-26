package Actions;
import java.util.*;

interface ActionGameplay {

    void identifyAction(String userInput);

    void executeAction();

    void setAction(Action action);

    void identifySubjects();

    void identifyConsumed();

    void identifyProduced();

}
