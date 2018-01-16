import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Snake extends JFrame {

    private GameView view;
    private JLabel scoreTxt, scoreNum;
    private JButton score;
    private Timer time;

    public Snake() {
        super("Game Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        setLayout(null);

        view = new GameView();

        scoreTxt = new JLabel("Score :");
        scoreTxt.setBounds(10, 10, 100, 20);
        scoreTxt.setFocusable(false);

        scoreNum = new JLabel("0");
        scoreNum.setBounds(70, 10, 30, 20);

        // pobieranie wyniku z GameView
        score = new JButton("0");
        score.doClick();
        score.setBounds(110, 10, 100, 20);
        score.setFocusable(false);
        score.setVisible(false);
        // czas do pobierania wynikow
        time = new Timer(150, new Scores());
        time.start();

        add(view);
        add(scoreTxt);
        add(scoreNum);
        add(score);

        setVisible(true);
    }

    // MAIN
    public static void main(String[] args) {
        new Snake();
    }

    private class Scores implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == time) {
                score.doClick();
                int s = view.getScore();
                String sco = "" + s;
                scoreNum.setText(sco);
            }

        }
    }

}
