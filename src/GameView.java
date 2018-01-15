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

    private Timer timer;

    public GameView() {

        addKeyListener(new KClick());
        setBackground(Color.BLACK);
        setBounds(10, 10, 475, 450);
        setFocusable(true);

        initializeGame();

    }

    // metoda do inicjalizacji gry
    public void initializeGame() {
        x = new ArrayList<Integer>();
        y = new ArrayList<Integer>();

        dot = 3;

        for (int i = 0; i < dot; i++) {
            x.add(100 - i * 10);
            y.add(100);
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

    public void doDraw(Graphics g) {

        if (status) {
            // cel ma kolor żółty
            g.setColor(Color.YELLOW);
            g.fillOval(targetX, targetY, 10, 10);

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
        int r = rand.nextInt(43);
        targetX = r * 10;
        r = rand.nextInt(43);
        targetY = r * 10;
    }

    private void checkTarget() {

        if (x.contains(targetX) && y.contains(targetY)) {
            ++dot;
            // dodanie pola do weza
            x.add(x.get(1));
            y.add(y.get(1));
            getTarget();
        }
    }


    public void move() {

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
            move();
        }

        repaint();
    }

    private class KClick extends KeyAdapter {

        // Sterowanie strzałkami
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_UP)) {
                up = true;
                down = false;
                right = false;
                left = false;
                System.out.println("UP");
            }

            if ((key == KeyEvent.VK_DOWN)) {
                up = false;
                down = true;
                right = false;
                left = false;
                System.out.println("DOWN");
            }

            if ((key == KeyEvent.VK_LEFT)) {
                up = false;
                down = false;
                right = false;
                left = true;
                System.out.println("LEFT");
            }

            if ((key == KeyEvent.VK_RIGHT)) {
                up = false;
                down = false;
                right = true;
                left = false;
                System.out.println("RIGHT");
            }

        }
    }

}
