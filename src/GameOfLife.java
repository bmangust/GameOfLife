package life;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GameOfLife extends JFrame {
    private static Generation current;
    static int cellSize = 20;
    static int size = 40;
    static int width = cellSize * size - 24;
    static int labelHeight = 30;
    static int height = width + labelHeight - 14;
    static JLabel generationLabel;
    static JLabel aliveLabel;
    static JPanel fieldPanel;

    public GameOfLife() {
        super("Game Of Life");

        current = new Generation(size);
        int alive = current.countAlive();
        int generation = current.getGenerationNumber();
        Cell[][] array = current.getArrayOfCells();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);

        JPanel labelPanel = new JPanel();
        labelPanel.setBounds(0, 0, width, labelHeight);
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        generationLabel = new JLabel("Generation #" + generation);
        generationLabel.setBounds(0,0, 100, 15);
        generationLabel.setAlignmentX(LEFT_ALIGNMENT);
        aliveLabel = new JLabel("Alive: " + alive);
        aliveLabel.setBounds(0,0, 100, 15);
        aliveLabel.setAlignmentX(LEFT_ALIGNMENT);

        labelPanel.setAlignmentX(LEFT_ALIGNMENT);
        labelPanel.add(generationLabel);
        labelPanel.add(aliveLabel);

        fieldPanel = new JPanel();
        fieldPanel.setBounds(0, 0, width, height - labelHeight);
        fieldPanel.setLayout(new GridLayout(1, 1,0,0));

        updateField(array);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(labelPanel);
        add(Box.createRigidArea(new Dimension(0, 2)));
        add(fieldPanel);

        setVisible(true);

        visualizeGUI();
    }

    public static void updateField(Cell[][] array) {
        JPanel newFieldPanel = new JPanel();
        newFieldPanel.setBounds(0, 0, width, height - labelHeight);
        newFieldPanel.setLayout(new GridLayout(array.length, array.length,1,1));
        newFieldPanel.setBackground(Color.BLACK);
        for (int j = 0; j < array.length; j++) {
            for (int i = 0; i < array.length; i++) {
                JPanel cell = new JPanel();
                cell.setBounds(0, 0, width / array.length, height / array.length);
                Color color = array[j][i].isAlive() ? Color.BLACK : Color.WHITE;
                cell.setBackground(color);
                newFieldPanel.add(cell);
            }
        }
        fieldPanel.removeAll();
        fieldPanel.add(newFieldPanel);
        fieldPanel.revalidate();
//        fieldPanel.repaint();
    }

    public static void updateLabels(int generation, int alive) {
        generationLabel.setText("Generation #" + generation);
        aliveLabel.setText("Alive: " + alive);
    }

    public static void visualizeGUI() {
        while (true) {
            updateLabels(current.getGenerationNumber(), current.countAlive());
            updateField(current.getArrayOfCells());
            current = current.getNewGeneration();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void visualize() {
        try {
            clearScreen();
            System.out.println("Generation #" + current.getGenerationNumber());
            System.out.println("Alive: " + current.countAlive());
            visualize(current.getArrayOfCells());
            current = current.getNewGeneration();
            Thread.sleep(500);
        }
        catch (InterruptedException e) {}
    }

    public static void visualize(int n) {
        for (int i = 0; i < n; i++) {
            visualize();
        }
    }

    private static void visualize(Cell[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[j][i].renderCell());
            }
            System.out.println();
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

