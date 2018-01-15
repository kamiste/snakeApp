import javax.swing.*;
import java.awt.*;

public class Snake extends JFrame {

    private GameView widok;

    public Snake(){
        super("Game Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(500,500));
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        widok = new GameView();

        add(widok);

        setVisible(true);
    }

    // MAIN
    public static void main(String[] args){
        new Snake();
    }
}
