package tetris;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel implements ActionListener {

    private final int TILE_SIZE = 20;
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 20;
    private final int OFFSET = 30;
    private final int MARGIN = 100;

    private Timer timer;
    private Tetromino currentPiece;
    public int score = 0;
    public int level = 1;
    public boolean gameover = false;
    boolean[][] board = new boolean[10][20]; // [x][y]


    public Game() {
        setPreferredSize(new Dimension(BOARD_WIDTH * TILE_SIZE,BOARD_HEIGHT * TILE_SIZE + OFFSET));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new TetrisKeyAdapter());
    }

    public void startGame() {
        currentPiece = new Tetromino();
        timer = new Timer(500, this); // game speed
        timer.start();
        // before drawing the grid draw some gui
        
    }
    
    public void gameOver() {
     System.out.println("gameover");
     gameover = true;
     // ask user to save score
    }
    public void getHighScore() { // get the highest score in the file
    
    }
    
    private void lockPiece() { // set blocks in place
    for (Point p : currentPiece.getBlocks()) {
        board[p.x][p.y] = true;
       }
      score+=100;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameover) {return;}
        g.setColor(Color.WHITE);
        g.drawString(String.format("Score: %d", score), 0, 10);
        g.drawString(String.format("Level: %d", level),0,20);
        g.drawString("Press ESC to pause",WIDTH+90,10);

        
        // draw grid
        g.setColor(Color.DARK_GRAY);
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                g.drawRect(x * TILE_SIZE,y * TILE_SIZE+OFFSET,TILE_SIZE,TILE_SIZE);
            }
        }
        // draw pre existing pieces
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                if (y == 0 && board[x][y]) {
                    gameOver();
                }
                if (board[x][y]) {
                    // TODO: eventually fill based on color 
                    g.fillRect(x * TILE_SIZE,y * TILE_SIZE + OFFSET,TILE_SIZE,TILE_SIZE);
                }
            }
        }

        // draw current piece
        if (currentPiece != null) {
            switch (currentPiece.type) {
                case I ->  { g.setColor(Color.CYAN);}
                case O ->  { g.setColor(Color.YELLOW);}
                case T ->  { g.setColor(Color.MAGENTA);}
                case S ->  { g.setColor(Color.GREEN);}
                case Z ->  { g.setColor(Color.RED);}
                case J ->  { g.setColor(Color.ORANGE);}
                case L ->  { g.setColor(Color.BLUE);}
            }
           
            for (Point p : currentPiece.getBlocks()) {
                g.fillRect(p.x * TILE_SIZE,p.y * TILE_SIZE + OFFSET,TILE_SIZE,TILE_SIZE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!currentPiece.moveDown(board)) {
            lockPiece();
            currentPiece = new Tetromino();
        }
        repaint();
    }


   private class TetrisKeyAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> currentPiece.moveLeft(board);
            case KeyEvent.VK_RIGHT -> currentPiece.moveRight(board);
            case KeyEvent.VK_DOWN -> currentPiece.moveDown(board);
            case KeyEvent.VK_UP -> currentPiece.rotate(board);
        }
        repaint();
    }
    }

    }


