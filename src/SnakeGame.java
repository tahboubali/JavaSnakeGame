import java.util.ArrayList;
import java.io.*;

public class SnakeGame {
    static boolean running = false;
    static char input;
    static Thread inputThread;
    static final int WIDTH = 100;
    static final int HEIGHT = WIDTH / 3;
    static ArrayList<int[]> snakePositions;
    static char[][] grid = new char[HEIGHT][WIDTH];
    static int applePosX = (int) (Math.random() * WIDTH);
    static int applePosY = (int) (Math.random() * HEIGHT);
    static int score;
    static float speed = 200;

    enum SnakeDirections {
        left, right, up, down
    }

    static SnakeDirections currDirection = SnakeDirections.right;

    public static void main(String[] args) throws InterruptedException {
        initializeGame();
        inputThread.start();
        runGame();
    }


    static void initializeGame() {
        newSnake();
        score = 0;
        running = true;

        inputThread = new Thread(() -> {
            try {
                while (running) {
                    input = (char) System.in.read();
                    changeSnakeDirection(input);
                }
                Thread.currentThread().join();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        generateNewApple();
        updateGrid();
        drawGrid();
    }

    static void newSnake() {
        snakePositions = null;
        snakePositions = new ArrayList<>() {{
            for (int i = 10; i >= 6; i--) {
                this.add(new int[]{6, i});
            }
        }};
    }

    static void runGame() throws InterruptedException {
        while (true) {
            checkCollisions();
            if (!running) {
                break;
            }
            moveSnake();
            checkApple();
            updateGrid();
            drawGrid();
            Thread.sleep((long) speed);
        }
        gameOver();
        System.exit(0);
    }

    static void updateGrid() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                for (int[] p : snakePositions) {
                    if (i == snakePositions.get(0)[0] && j == snakePositions.get(0)[1]) {
                        grid[i][j] = 'S';
                        break;
                    } else if (i == p[0] && j == p[1]) {
                        grid[i][j] = 's';
                        break;
                    } else {
                        grid[i][j] = ' ';
                    }
                }
            }
        }
        grid[applePosY][applePosX] = 'O';
    }

    static void drawGrid() {
        System.out.print("\033[H\033[2J");
        System.out.println("APPLE POS: [" + applePosX + ", " + applePosY + "]");
        System.out.println("SCORE: " + score);
        System.out.print('+');
        for (int i = 1; i <= WIDTH - 1; i++) {
            System.out.print('-');
        }
        System.out.println('+');
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (j == 0) {
                    System.out.print('|');
                } else if (grid[i][j] == 0) {
                    System.out.print(' ');
                } else {
                    System.out.print(grid[i][j]);
                }
            }
            System.out.println('|');
        }
        System.out.print('+');
        for (int i = 1; i <= WIDTH - 1; i++) {
            System.out.print('-');
        }
        System.out.println('+');
    }

    static void moveSnake() {
        for (int i = snakePositions.size() - 1; i > 0; i--) {
            snakePositions.set(i, snakePositions.get(i - 1));
        }

        if (currDirection == SnakeDirections.up) {
            snakePositions.set(0, new int[]{snakePositions.get(0)[0] - 1, snakePositions.get(0)[1]});
        } else if (currDirection == SnakeDirections.down) {
            snakePositions.set(0, new int[]{snakePositions.get(0)[0] + 1, snakePositions.get(0)[1]});
        } else if (currDirection == SnakeDirections.left) {
            snakePositions.set(0, new int[]{snakePositions.get(0)[0], snakePositions.get(0)[1] - 1});
        } else if (currDirection == SnakeDirections.right) {
            snakePositions.set(0, new int[]{snakePositions.get(0)[0], snakePositions.get(0)[1] + 1});
        }

    }

    static void changeSnakeDirection(char direction) {
        switch (direction) {
            case 'a' -> currDirection = SnakeDirections.left;
            case 'd' -> currDirection = SnakeDirections.right;
            case 'w' -> currDirection = SnakeDirections.up;
            case 's' -> currDirection = SnakeDirections.down;
        }
    }

    static void generateNewApple() {
        while (true) {
            applePosX = (int) (Math.random() * (WIDTH - 1)) + 1;
            applePosY = (int) (Math.random() * (HEIGHT - 1));
            boolean appleInSnake = false;

            for (int[] snakePos : snakePositions) {
                if (snakePos[0] == applePosY && snakePos[1] == applePosX) {
                    appleInSnake = true;
                    break;
                }
            }

            if (!appleInSnake) {
                return;
            }
        }
    }

    static void checkApple() {
        if (snakePositions.get(0)[1] == applePosX && snakePositions.get(0)[0] == applePosY) {
            generateNewApple();
            growSnake();
            score++;
            speed -= 0.6;
        }
    }

    static void checkCollisions() {
        if (snakePositions.get(0)[0] == -1) {
            running = false;
        }
        if (snakePositions.get(0)[0] == HEIGHT) {
            running = false;
        }
        if (snakePositions.get(0)[1] == WIDTH) {
            running = false;
        }
        if (snakePositions.get(0)[1] == 0) {
            running = false;
        }

        for (int i = 1; i < snakePositions.size(); i++) {
            if (snakePositions.get(i)[0] == snakePositions.get(0)[0] && snakePositions.get(i)[1] == snakePositions.get(0)[1]) {
                running = false;
                break;
            }
        }
    }

    static void growSnake() {

        if (snakePositions.get(snakePositions.size() - 1)[0] > snakePositions.get(snakePositions.size() - 2)[0])
            // down
            snakePositions.add(new int[]{snakePositions.get(snakePositions.size() - 1)[0] + 1, snakePositions.get(snakePositions.size() - 1)[1]});
        else if (snakePositions.get(snakePositions.size() - 1)[0] < snakePositions.get(snakePositions.size() - 2)[0])
            // up
            snakePositions.add(new int[]{snakePositions.get(snakePositions.size() - 1)[0] - 1, snakePositions.get(snakePositions.size() - 1)[1]});
        else if (snakePositions.get(snakePositions.size() - 1)[1] > snakePositions.get(snakePositions.size() - 2)[1])
            // left
            snakePositions.add(new int[]{snakePositions.get(snakePositions.size() - 1)[0], snakePositions.get(snakePositions.size() - 1)[1] + 1});
        else
            // right
            snakePositions.add(new int[]{snakePositions.get(snakePositions.size() - 1)[0], snakePositions.get(snakePositions.size() - 1)[1] - 1});


    }

    static void gameOver() throws InterruptedException {
        System.out.print("\033[H\033[2J");
        System.out.print("GAME ");
        Thread.sleep(500);
        System.out.println("OVER!");
        System.out.println();
        Thread.sleep(500);
        System.out.println("SCORE: " + score);
    }

}