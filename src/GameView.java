import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GameView extends JPanel implements ActionListener {

    private ArrayList<Integer> x, y;

    private boolean right = true;
    private boolean down = false;
    private boolean left = false;
    private boolean up = false;
    private boolean status = true;

    private int dot, tmp = 0;
    private int targetX, targetY;
    private int score = 0;
    private final int WIDTH = 460;
    private final int HEIGHT = 410;

    private Timer timer;

    public GameView() {

        addKeyListener(new KClick());
        setBackground(Color.BLACK);
        setBounds(10, 40, WIDTH, HEIGHT);
        setFocusable(true);

        initializeGame();

    }

    // metoda do inicjalizacji gry
    private void initializeGame() {

        x = new ArrayList<Integer>();
        y = new ArrayList<Integer>();

        dot = 3;

        // początkowe połóżenie węża
        for (int i = 0; i < dot; i++) {
            x.add(200 - i * 10);
            y.add(200);
        }

        getTarget();

        // timer potrzebny do repainta !
        timer = new Timer(150, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDraw(g);
    }


    private void doDraw(Graphics g) {

        if (status) {
            // cel ma kolor żółty i jest kwadratem
            g.setColor(Color.YELLOW);
            g.fillRect(targetX, targetY, 10, 10);

            for (int i = 0; i < dot; i++) {
                if (i == 0) {
                    // głowa węża ma kolor czerwony
                    g.setColor(Color.RED);
                    g.fillOval(x.get(i), y.get(i), 10, 10);
                } else
                    // ciało węża ma kolor zielony
                    g.setColor(Color.GREEN);
                g.fillOval(x.get(i), y.get(i), 10, 10);
            }

        }
    }

    private void getTarget() {
        Random rand = new Random();
        int r = rand.nextInt(44);
        targetX = 10 + (r * 10);
        r = rand.nextInt(39);
        targetY = 10 + (r * 10);
    }

    private void checkTarget() {

        if (x.contains(targetX) && y.contains(targetY)) {
            ++dot;
            score += 10;
            // dodanie pola do weza
            x.add(x.get(1));
            y.add(y.get(1));
            getTarget();
        }
    }

    private void checkCollision() {

        String msg = "Game Over, click for new game";

        // sprawdzenie kolizji z wężem
        for (int i = 1; i < dot; i++) {
            if (x.get(i).equals(x.get(0)) && y.get(i).equals(y.get(0))) {
                msg = "<html><center>~ You entered the snake, game is over ~</center>" +
                        "<center>Click OK for new game!</center></html>";
                status = false;
            }
        }

        if (x.get(0) > WIDTH - 10) {
            status = false;
        }

        if (x.get(0) < 0) {
            status = false;
        }

        if (y.get(0) > HEIGHT - 10) {
            status = false;
        }

        if (y.get(0) < 0) {
            status = false;
        }

        if (!status) {
            JOptionPane.showMessageDialog(null, msg);
            timer.stop();
            x.clear();
            y.clear();
            score = 0;
            dot = 3;

            for (int i = 0; i < dot; i++) {
                x.add(200 - i * 10);
                y.add(200);
            }

            getTarget();
            timer.start();

            status = true;
            right = true;
            down = false;
            left = false;
            up = false;

        }
    }

    public int getScore() {
        return score;
    }

    private void move() {

        for (int i = dot - 1; i > 0; i--) {
            x.set(i, x.get(i - 1));
            y.set(i, y.get(i - 1));
        }

        if (up) {
            tmp = y.get(0);
            y.set(0, tmp - 10);
        }

        if (down) {
            tmp = y.get(0);
            y.set(0, tmp + 10);
        }

        if (left) {
            tmp = x.get(0);
            x.set(0, tmp - 10);
        }

        if (right) {
            tmp = x.get(0);
            x.set(0, tmp + 10);
        }
    }

    // obsluga zdarzen
    @Override
    public void actionPerformed(ActionEvent e) {

        if (status) {
            checkTarget();
            checkCollision();
            move();
        }

        repaint();
    }

    private class KClick extends KeyAdapter {

        // Sterowanie strzałkami
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            // aby wąż nierobił cały czas kolizji w jednej płaszczyznie !
            if ((key == KeyEvent.VK_UP && !down)) {
                up = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_DOWN && !up)) {
                down = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_LEFT && !right)) {
                up = false;
                down = false;
                left = true;
            }

            if ((key == KeyEvent.VK_RIGHT && !left)) {
                up = false;
                down = false;
                right = true;
            }

        }
    }

}
