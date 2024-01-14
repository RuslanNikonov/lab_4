import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MovingStringsConsole extends JApplet implements Runnable {
    private String[] strings = {"String 1", "String 2", "String 3", "String 4", "String 5"};
    private int[] xPositions;
    private int[] yPositions;
    private int[] xDirections;
    private int[] yDirections;
    private Thread animator;

    public void init() {
        xPositions = new int[strings.length];
        yPositions = new int[strings.length];
        xDirections = new int[strings.length];
        yDirections = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            xPositions[i] = new Random().nextInt(400); // Генерируем случайное положительное значение в пределах ширины окна
            yPositions[i] = new Random().nextInt(400); // Генерируем случайное положительное значение в пределах высоты окна
            xDirections[i] = new Random().nextBoolean() ? 1 : -1;
            yDirections[i] = new Random().nextBoolean() ? 1 : -1;
        }
    }

    public void start() {
        animator = new Thread(this);
        animator.start();
    }

    public void stop() {
        animator = null;
    }

    public void run() {
        while (Thread.currentThread() == animator) {
            for (int i = 0; i < strings.length; i++) {
                xPositions[i] += xDirections[i];
                yPositions[i] += yDirections[i];
                if (xPositions[i] > 400 - 50 || xPositions[i] < 0) {
                    xDirections[i] = -xDirections[i];
                }
                if (yPositions[i] > 400 - 20 || yPositions[i] < 0) {
                    yDirections[i] = -yDirections[i];
                }
            }
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paint(Graphics g) {
        for (int i = 0; i < strings.length; i++) {
            g.drawString(strings[i], xPositions[i], yPositions[i]);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Движущиеся строки");
        MovingStringsConsole applet = new MovingStringsConsole();
        frame.add(applet);
        frame.setSize(400, 400);
        applet.init();
        applet.start();
        frame.setVisible(true);
    }
}
