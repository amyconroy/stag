package Actions;
import Entities.Player;
import java.io.BufferedWriter;
import java.io.IOException;

public class HealthAction implements Gameplay {
    private int health;
    private Player player;
    private BufferedWriter out;

    public HealthAction(Player player, BufferedWriter out){
        this.out = out;
        this.player = player; }

    public void executeAction() throws IOException {
        setHealth();
        printHealth(); }

    private void setHealth(){ health = player.getHealth(); }

    private void printHealth() throws IOException {
        out.write("YOUR CURRENT HEALTH LEVEL IS: " + health + "\n"); }
}
