package tetris;

import java.awt.Point;
import java.util.Random;

public class Tetromino {

    public enum Type {I, O, T, S, Z, J, L}

    private Point[] blocks = new Point[4]; // storage of the 4 squares
    private Point[] shape; // tetris shape
    private int x = 4;
    private int y = 0;
    public Type type; // type of shape

    private static final Random random = new Random();

    public Tetromino() {
        type = Type.values()[random.nextInt(Type.values().length)];
        shape = getShape(type);

        for (int i = 0; i < 4; i++) {
            blocks[i] = new Point();
        }

        updateBlocks();
    }

    private Point[] getShape(Type type) {
        switch (type) {
            case I -> {
                return new Point[]{new Point(0, 0),new Point(0, 1),new Point(0, 2),new Point(0, 3)};
            }
            case O -> {
                return new Point[]{new Point(0, 0),new Point(1, 0),new Point(0, 1),new Point(1, 1)};
            }
            case T -> {
                return new Point[]{new Point(1, 0),new Point(0, 1),new Point(1, 1),new Point(2, 1)};
            }
            case S -> {
                return new Point[]{new Point(1, 0),new Point(2, 0),new Point(0, 1),new Point(1, 1)};
            }
            case Z -> {
                return new Point[]{new Point(0, 0),new Point(1, 0),new Point(1, 1),new Point(2, 1)};
            }
            case J -> {
                return new Point[]{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)};
            }
            case L -> {
                return new Point[]{new Point(2, 0),new Point(0, 1),new Point(1, 1),new Point(2, 1) };
            }
            default -> throw new IllegalStateException("Unknown type");
        }
    }

    public Point[] getBlocks() {
        return blocks;
    }

    public Type getType() {
        return type;
    }

    // movement of blocks
    public boolean moveDown(boolean[][] board) {
    if (!canMove(0, 1, shape, board)) {
        return false; // landed
    }
    y++;
    updateBlocks();
    return true;
}

public void moveLeft(boolean[][] board) {
    if (canMove(-1, 0, shape, board)) {
        x--;
        updateBlocks();
    }
}

public void moveRight(boolean[][] board) {
    if (canMove(1, 0, shape, board)) {
        x++;
        updateBlocks();
    }
}

public void rotate(boolean[][] board) {
    if (type == Type.O) return;

    Point[] rotated = new Point[4];
    for (int i = 0; i < 4; i++) {
        rotated[i] = new Point(-shape[i].y, shape[i].x);
    }

    if (canMove(0, 0, rotated, board)) {
        shape = rotated;
        updateBlocks();
    }
}


    private void updateBlocks() {
        if (y>20)return;
        for (int i = 0; i < 4; i++) {
              blocks[i].x = x + shape[i].x;
              blocks[i].y = y + shape[i].y;
        }
    }
    private boolean canMove(int dx, int dy, Point[] testShape, boolean[][] board) {
    for (Point p : testShape) {
        int newX = x + p.x + dx;
        int newY = y + p.y + dy;

        // check if the peice coordinates are within the grid
        if (newX < 0 || newX >= 10 || newY < 0 || newY >= 20) {
            return false;
        }

        // Board collision (if area is filled with another piece)
        if (board[newX][newY]) {
            return false;
        }
    }
    return true;
}

    
}
               
    

