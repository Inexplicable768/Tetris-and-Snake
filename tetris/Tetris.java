package tetris;
import javax.swing.JFrame;

public class Tetris {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris in Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Game grid = new Game();
        frame.add(grid);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        grid.startGame();
    }
}

