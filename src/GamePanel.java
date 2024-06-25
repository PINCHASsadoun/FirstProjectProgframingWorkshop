import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class GamePanel extends JPanel {


    private Track track = new Track();//Benyamin
    private boolean game = true;
    private GameFrame gameFrame;


    public static final int LIVES_COUNT = 3;
    private int lives = LIVES_COUNT;
    private GameStatusUpdater gameStatusUpdater;
    private final int TILE_SIZE = 20;
    private Map map;

    private PacMan pacMan;
    private List<Ghost> ghosts;
    private List<Cherry> cherries;
    private List<Circle> circles;
    private List<Dot> dots;
    private boolean gameStarted;
    private boolean ghostsVulnerable;
    private Thread vulnerabilityThread;
    private Thread gameThread;
    private Thread pacManThread;
    private int score = 0;
    private int[] cherryRow;
    private int[] cherryCol;
    private Direction lastAttemptedDirection = null;
    private final int PACMAN_SPEED = 200;  // Pac-Man's speed (milliseconds)
    private final int GHOST_SPEED = 450;   // Ghosts' speed (milliseconds)
    private boolean showReadyImage = true;
    private Image readyImage;

    public GamePanel(GameStatusUpdater gameStatusUpdater, int mapNum) {
        track.main();//Benyamin


        this.gameStatusUpdater = gameStatusUpdater;

        if (mapNum ==2)
            spawn2();
        if (mapNum ==1)
            spawn1();


        gameStarted = false;
        ghostsVulnerable = false;

        // Load the "ready" image
        readyImage = new ImageIcon("resources/ready.png").getImage();

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                SwingUtilities.invokeLater(() -> {
                    if (showReadyImage) {
                        showReadyImage = false;
                        gameStarted = true;
                        startGameThreads();
                    }
                    setPacManDirection(e.getKeyCode());
                    checkCherryCollision();
                    checkCircleCollision();
                    checkDotCollision();

                    repaint();
                });
            }
        });

    }

    public void spawn1(){
        map = new Map(21, 37, 1);
        pacMan = new PacMan(11, 18);
        ghosts = Arrays.asList(new Ghost(9, 17, "red.png"),
                new Ghost(9, 18, "cyan.png"),
                new Ghost(9, 19, "orange.png"),
                new Ghost(8, 18, "purple.png"));

        cherries = new ArrayList<>();
        circles = new ArrayList<>();
        dots = new CopyOnWriteArrayList<>();

        placeCherries(3); // Place 3 cherries on the map
        placeCircles(4);  // Place 4 circles on the map
        placeDots();
    }

    public void spawn2(){
        map = new Map(21, 37, 2);
        pacMan = new PacMan(11, 18);
        ghosts = Arrays.asList(new Ghost(9, 17, "red.png"),
                new Ghost(9, 18, "cyan.png"),
                new Ghost(9, 19, "orange.png"),
                new Ghost(8, 18, "purple.png"));

        cherries = new ArrayList<>();
        circles = new ArrayList<>();
        dots = new CopyOnWriteArrayList<>();

        placeCherries(3); // Place 3 cherries on the map
        placeCircles(4);  // Place 4 circles on the map
        placeDots();
    }

    private void gameOver() {
        track.stop();
        track.fail();
        this.game=false;

        if (JOptionPane.showConfirmDialog(this, "Game Over, try again?", "Game Over", JOptionPane.YES_NO_OPTION)==0){
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();

            String[] args = {};
            Main.main(args);
        }
        else
            System.exit(0);
    }
    private void win() {
        track.stop();
        track.win();
        this.game=false;

        JOptionPane.showConfirmDialog(this, "Win", "Congratulations", JOptionPane.CLOSED_OPTION);
        System.exit(0);
    }


    private void startGameThreads() {
        gameThread = new Thread(() -> {
            while (this.game) {
                if (gameStarted) {
                    for (Ghost ghost : ghosts) {
                        ghost.move(map, pacMan.getRow(), pacMan.getCol(), ghosts);
                    }
                    checkGhostCollision();
                    repaint();
                }
                try {
                    Thread.sleep(GHOST_SPEED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();
        Thread collisionThread = new Thread(() -> {
            while (true) {
                if (dots.isEmpty())
                    win();
                if (gameStarted) {
                    checkVulnerableCollision();
                    repaint();
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        collisionThread.start();
    }


    private void placeCherries(int count) {
        Random random = new Random();
        int rows = map.getRows();
        int cols = map.getCols();

        cherryRow = new int[count];
        cherryCol = new int[count];

        for (int i = 0; i < count; i++) {
            int row, col;
            do {
                row = random.nextInt(rows);
                col = random.nextInt(cols);
                cherryRow[i] = row;
                cherryCol[i] = col;
            } while (!map.isPath(row, col) || !isEmpty(row, col, cherries)&& !map.isHQ(row, col));

            cherries.add(new Cherry(row, col, "resources/cherry.png"));
        }
    }

    private void placeCircles(int count) {
        Random random = new Random();
        int rows = map.getRows();
        int cols = map.getCols();

        for (int i = 0; i < count; i++) {
            int row, col;
            do {
                row = random.nextInt(rows);
                col = random.nextInt(cols);
            } while (!map.isPath(row, col) || !isEmpty(row, col, cherries) &&
                    !isEmpty(row, col, circles));

            circles.add(new Circle(row, col));
        }
    }

    private void placeDots() {
        int rows = map.getRows();
        int cols = map.getCols();

        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getCols(); j++) {
                int row = i;
                int col = j;

                if (map.isPath(row, col) && isEmpty(row, col, dots) &&
                        isEmpty(row, col, cherries) &&
                        isEmpty(row, col, circles) &&
                        !map.isHQ(row, col)) {
                    dots.add(new Dot(row, col));
                }
            }
        }
    }

    private boolean isEmpty(int row, int col, List<?> items) {
        for (Object item : items) {
            if (item instanceof Cherry cherry) {
                if (cherry.getRow() == row && cherry.getCol() == col) {
                    return false;
                }
            } else if (item instanceof Circle circle) {
                if (circle.getRow() == row && circle.getCol() == col) {
                    return false;
                }
            } else if (item instanceof Dot dot) {
                if (dot.getRow() == row && dot.getCol() == col) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setPacManDirection(int keyCode) {
        Direction newDirection = pacMan.getDirection();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                newDirection = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                newDirection = Direction.DOWN;
                break;
            case KeyEvent.VK_LEFT:
                newDirection = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                newDirection = Direction.RIGHT;
                break;
        }
        lastAttemptedDirection = newDirection;
        if (pacManThread == null || !pacManThread.isAlive()) {
            pacManThread = new Thread(() -> {
                while (true) {
                    movePacMan();
                    checkCherryCollision();
                    checkCircleCollision();
                    checkDotCollision();
                    repaint();
                    try {
                        Thread.sleep(PACMAN_SPEED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            pacManThread.start();
        }
    }

    private void movePacMan() {
        int newRow = pacMan.getRow() + pacMan.getDirection().getRowDelta();
        int newCol = pacMan.getCol() + pacMan.getDirection().getColDelta();

        if (lastAttemptedDirection != null) {
            int attemptedRow = pacMan.getRow() + lastAttemptedDirection.getRowDelta();
            int attemptedCol = pacMan.getCol() + lastAttemptedDirection.getColDelta();

            if (pacMan.canMoveTo(attemptedRow, attemptedCol, map)) {
                pacMan.setPosition(attemptedRow, attemptedCol);
                pacMan.setDirection(lastAttemptedDirection);
                lastAttemptedDirection = null; // Reset after successful move
            } else if (pacMan.canMoveTo(newRow, newCol, map)) {
                pacMan.setPosition(newRow, newCol);
            }
        } else if (pacMan.canMoveTo(newRow, newCol, map)) {
            pacMan.setPosition(newRow, newCol);
        }
    }

    private void checkCherryCollision() {
        for (int i = 0; i < cherries.size(); i++) {
            Cherry cherry = cherries.get(i);
            if (pacMan.getBounds(TILE_SIZE).intersects(cherry.getBounds(TILE_SIZE))) {
                score += 100;
                gameStatusUpdater.setScore(score);
                cherries.remove(i);
                break;
            }
        }
    }

    private void checkGhostCollision() {
        for (Ghost ghost : ghosts) {
            if (!ghost.isVulnerable() && (pacMan.getRow() == ghost.getRow() && pacMan.getCol() == ghost.getCol())) {
                lives--;
                gameStatusUpdater.removeHeart();
                if (lives <= 0) {

                    gameOver();
                }
            }
        }
    }

    private void checkVulnerableCollision() {
        for (Ghost ghost : ghosts) {
            if (ghost.isVulnerable() && (pacMan.getRow() == ghost.getRow() && pacMan.getCol() == ghost.getCol())) {
                this.score += 200;
                gameStatusUpdater.setScore(score);
                ghost.setPosition(9,18);
                ghost.restoreOriginalImage();
                ghost.setVulnerable(false);
                repaint();
            }
        }
    }

    private void checkCircleCollision() {
        for (Circle circle : circles) {
            if (pacMan.getBounds(TILE_SIZE).intersects(circle.getBounds(TILE_SIZE))) {
                circles.remove(circle);
                makeGhostsVulnerable();
                break;
            }
        }
    }

    private void checkDotCollision() {
        for (Dot dot : dots) {
            if (pacMan.getBounds(TILE_SIZE).intersects(dot.getBounds(TILE_SIZE))) {
                dots.remove(dot);
                score += 10;
                gameStatusUpdater.setScore(score);
                break;
            }
        }
    }

    private void makeGhostsVulnerable() {
        if (vulnerabilityThread != null && vulnerabilityThread.isAlive()) {
            vulnerabilityThread.interrupt();
        }

        ghostsVulnerable = true;
        for (Ghost ghost : ghosts) {
            ghost.setVulnerable(true);
        }

        vulnerabilityThread = new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // Handle interruption
            } finally {
                ghostsVulnerable = false;
                for (Ghost ghost : ghosts) {
                    ghost.setVulnerable(false);
                }
            }
        });
        vulnerabilityThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        map.draw(g, TILE_SIZE);
        pacMan.draw(g, TILE_SIZE);
        for (Ghost ghost : ghosts) {
            ghost.draw(g, TILE_SIZE);
        }
        for (Cherry cherry : cherries) {
            cherry.draw(g, TILE_SIZE);
        }
        for (Circle circle : circles) {
            circle.draw(g, TILE_SIZE);
        }
        for (Dot dot : dots) {
            dot.draw(g, TILE_SIZE);
        }
        if (showReadyImage) {
            int x = (getWidth() - readyImage.getWidth(null)) / 2 + 4;
            int y = (getHeight() - readyImage.getHeight(null)) / 2 + 43;
            g.drawImage(readyImage, x, y, this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(map.getCols() * TILE_SIZE, map.getRows() * TILE_SIZE);
    }
}
