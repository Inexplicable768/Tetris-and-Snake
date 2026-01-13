package snake;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class Snake extends JPanel implements ActionListener{
    private final int TILE_SIZE = 20;
    private final int BOARD_WIDTH = 15;
    private final int BOARD_HEIGHT = 15;
    private final int OFFSET = 30;
    private final int SPEED = 150; // speed in ms / block
    
    enum Direction { UP, DOWN, LEFT, RIGHT }
    boolean running = true;
    private Direction direction = Direction.RIGHT;

    private int snakeLength = 3;
    private int[] snakeX = new int[BOARD_WIDTH * BOARD_HEIGHT];
    private int[] snakeY = new int[BOARD_WIDTH * BOARD_HEIGHT];
    Point appleLoc;
    
    private Timer timer;
    public int score = 0;
    Font largeFont = new Font("SansSerif", Font.BOLD, 24);
    int[] snake;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Snake grid = new Snake();
        frame.add(grid);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    // the constructor is sort of our real entry point for the program
    // main just sets up the Jframe 
    Snake () {
        setPreferredSize(new Dimension(BOARD_WIDTH * TILE_SIZE,BOARD_HEIGHT * TILE_SIZE + OFFSET));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(new KeyboardInput());
        startGame();
    }
    
    private void startGame() {
    snakeLength = 3;

    for (int i = 0; i < snakeLength; i++) {
        snakeX[i] = 7 - i;
        snakeY[i] = 7;
    }

    spawnApple();

    timer = new Timer(SPEED, this);
    timer.start();
    }
    private void gameOver() {
        running = false;
    }

    private void spawnApple() {
        Random r = new Random();
        appleLoc = new Point(r.nextInt(BOARD_WIDTH),r.nextInt(BOARD_HEIGHT));
    }
    
    private void moveSnake() {
    // i think this should work in java but just move the array along
    if (!running) return;
    for (int i = snakeLength - 1; i > 0; i--) {
        snakeX[i] = snakeX[i - 1];
        snakeY[i] = snakeY[i - 1];
    }
    
    // change according to direction
    switch (direction) {
        case UP -> snakeY[0]--;
        case DOWN -> snakeY[0]++;
        case LEFT -> snakeX[0]--;
        case RIGHT -> snakeX[0]++;
    }
}
private void checkCollisions() {
    // position of head
    int headX = snakeX[0];
    int headY = snakeY[0];
    // check for food here
    if (appleLoc.x == headX && appleLoc.y == headY) {
        snakeLength++;
        score+=10;
        spawnApple();
    }
    // check wall collision
    if (headX < 0 || headX >= BOARD_WIDTH || headY < 0 || headY >= BOARD_HEIGHT) {
        gameOver();
    }
    // collision with self
    
}
    
    // where drawing happens
     @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
         g.setFont(largeFont);
        g.setColor(Color.BLACK);
        g.drawString(String.format("Score: %d", score), 0, 17);
        
        // draw board
         g.setColor(Color.DARK_GRAY);
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                g.drawRect(x * TILE_SIZE,y * TILE_SIZE+OFFSET,TILE_SIZE,TILE_SIZE);
            }
        }
        // draw SNAKE
        g.setColor(Color.GREEN);
        for (int i = 0; i < snakeLength; i++) {
            g.fillRect(snakeX[i] * TILE_SIZE,
                    snakeY[i] * TILE_SIZE + OFFSET,
                    TILE_SIZE, TILE_SIZE);
        }
        // draw apple
        g.setColor(Color.RED);
        g.fillRect(appleLoc.x*TILE_SIZE, appleLoc.y*TILE_SIZE+OFFSET, TILE_SIZE,TILE_SIZE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        moveSnake();
        checkCollisions();
        repaint();
    }
    
    public class KeyboardInput extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                if (direction != Direction.RIGHT)
                    direction = Direction.LEFT;
            }
            case KeyEvent.VK_RIGHT -> {
                if (direction != Direction.LEFT)
                    direction = Direction.RIGHT;
            }
            case KeyEvent.VK_UP -> {
                if (direction != Direction.DOWN)
                    direction = Direction.UP;
            }
            case KeyEvent.VK_DOWN -> {
                if (direction != Direction.UP)
                    direction = Direction.DOWN;
            }
          }
        }
        @Override public void keyReleased(KeyEvent e){}
        @Override public void keyTyped(KeyEvent e){}
   
    }
    
}
