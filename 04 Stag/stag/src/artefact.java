public class artefact extends location {
    private boolean isCollected = false;

    private artefact(){ new artefact(); }
    private boolean checkIfCollected(){
        return isCollected;
    }
    private void collected(){
        isCollected = true;
    }
    // have put the artefact back down
    private void isPutBack(){
        isCollected = false;
    }
}
