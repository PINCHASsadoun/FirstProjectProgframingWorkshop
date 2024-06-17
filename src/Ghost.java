import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Ghost {
    private int row;
    private int col;
    private final int spawnRow;
    private final int spawnCol;
    private Direction direction;
    private final Image originalImage;
    private Image currentImage;
    private boolean isVulnerable;
    private boolean isDead;

    public Ghost(int row, int col, String imagePath) {
        this.row = row;
        this.col = col;
        this.spawnRow = row;
        this.spawnCol = col;
        this.direction = Direction.RIGHT;
        this.originalImage = loadImage(imagePath);
        this.currentImage = this.originalImage;
        this.isVulnerable = false;
        this.isDead = false;
    }

    public void restoreOriginalImage(){
        this.currentImage = this.originalImage;
    }

    private Image loadImage(String path) {
        try {
            return ImageIO.read(new File("resources/" + path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

//    public String getImagePath() {
//
//    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setVulnerable(boolean vulnerable) {
        this.isVulnerable = vulnerable;
        if (vulnerable) {
            this.currentImage = loadImage("vulnerable.png");
        } else {
            this.currentImage = this.originalImage;
        }
    }

    public void setDead(boolean dead) {
        this.isDead = dead;
        if (dead) {
            resetToSpawn();
        } else {
            this.currentImage = this.originalImage;
        }
    }

    public boolean isVulnerable() {
        return isVulnerable;
    }

    public boolean isDead() {
        return isDead;
    }

    public void resetToSpawn() {
        this.row = spawnRow;
        this.col = spawnCol;
        this.isDead = false;
        this.isVulnerable = false;
        this.currentImage = this.originalImage;

    }

    public void move(Map map, int pacManRow, int pacManCol, List<Ghost> ghosts) {
        if (isDead) {
            // Move towards spawn point
            if (isVulnerable) {
                if (this.row == pacManRow && this.col == pacManCol) {
                    this.row = spawnRow;
                    this.col = spawnCol;

                }
            }
            if (row < spawnRow) row++;
            else if (row > spawnRow) row--;
            else if (col < spawnCol) col++;
            else if (col > spawnCol) col--;
            if (row == spawnRow && col == spawnCol) {
                resetToSpawn();
            }
            return;
        }

        int[] rowOffsets = {-1, 1, 0, 0};
        int[] colOffsets = {0, 0, -1, 1};
        Direction[] directions = Direction.values();

        // Determine the best move direction
        Direction bestDirection = direction;
        int bestDistance = isVulnerable ? 0 : Integer.MAX_VALUE; // Invert logic for vulnerability

        for (int i = 0; i < directions.length; i++) {
            int newRow = row + rowOffsets[i];
            int newCol = col + colOffsets[i];

            if (map.isPath(newRow, newCol) && noCollision(newRow, newCol, ghosts)) {
                int distance = Math.abs(newRow - pacManRow) + Math.abs(newCol - pacManCol);
                if ((isVulnerable && distance > bestDistance) || (!isVulnerable && distance < bestDistance)) {
                    bestDistance = distance;
                    bestDirection = directions[i];
                }
            }
        }

        int newRow = row;
        int newCol = col;

        switch (bestDirection) {
            case UP:
                newRow--;
                break;
            case DOWN:
                newRow++;
                break;
            case LEFT:
                newCol--;
                break;
            case RIGHT:
                newCol++;
                break;
        }

        if (map.isPath(newRow, newCol)) {
            row = newRow;
            col = newCol;
            direction = bestDirection;
        }
    }

    private boolean noCollision(int newRow, int newCol, List<Ghost> ghosts) {
        for (Ghost ghost : ghosts) {
            if (ghost != this && ghost.getRow() == newRow && ghost.getCol() == newCol) {
                return false;
            }
        }
        return true;
    }

    public void draw(Graphics g, int tileSize) {
        g.drawImage(currentImage, col * tileSize, row * tileSize, tileSize, tileSize, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ghost ghost = (Ghost) o;
        return row == ghost.row && col == ghost.col && spawnRow == ghost.spawnRow && spawnCol == ghost.spawnCol && isVulnerable == ghost.isVulnerable && isDead == ghost.isDead && direction == ghost.direction && Objects.equals(originalImage, ghost.originalImage) && Objects.equals(currentImage, ghost.currentImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, spawnRow, spawnCol, direction, originalImage, currentImage, isVulnerable, isDead);
    }
}
