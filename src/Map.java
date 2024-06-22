import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Map {
    private final int rows;
    private final int cols;
    private final Color[][] map;
    private final int hqRowStart;
    private final int hqRowEnd;
    private final int hqColStart;
    private final int hqColEnd;

    public Map(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.hqRowStart = rows / 2 - 2;
        this.hqRowEnd = rows / 2;
        this.hqColStart = cols / 2 - 2;
        this.hqColEnd = cols / 2 + 2;
        map = new Color[rows][cols];
        initMap();
    }

    private void initMap() {
        // Initialize the map with walls, paths, and HQ walls
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Outer walls
                if (isHQWall(row, col)) {
                    map[row][col] = Color.BLUE; // HQ walls
                } else if (isHQGate(row, col)) {
                    map[row][col] = Color.GREEN; // Gate path
                } else {
                    map[row][col] = Color.BLACK; // Paths
                }
            }
        }
        int[][] colorMatrix = readTextFile();

        // Fill the map based on the matrix
        fillMapFromMatrix(colorMatrix);
    }

    public static String importTextFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int[][] readTextFile() {
        String filePath = "resources/mapMatrixVersion2.txt";
        String fileContent = importTextFile(filePath);

        int[][] map = new int[rows][cols];
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                map[i][j] = fileContent.charAt(count) - '0';
                count++;
            }
        }
        return map;
    }

    private void fillMapFromMatrix(int[][] matrix) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols;col++) {
                if(matrix[row][col] == 1) {
                    map[row][col] = Color.BLUE;
                }
            }
        }
    }

    private boolean isHQWall(int row, int col) {
        return (row == hqRowStart || row == hqRowEnd) && (col >= hqColStart && col <= hqColEnd) && !(row == hqRowStart && col == (hqColStart + hqColEnd) / 2) ||
                (col == hqColStart || col == hqColEnd) && (row >= hqRowStart && row <= hqRowEnd);
    }

    public boolean isPath(int row, int col) {
        return map[row][col] == Color.BLACK || map[row][col] == Color.GREEN;
    }

    public boolean isHQ(int row, int col) {
        return row >= hqRowStart && row <= hqRowEnd && col >= hqColStart && col <= hqColEnd;
    }

    public boolean isHQGate(int row, int col) {
        return row == hqRowStart && col == (hqColStart + hqColEnd) / 2;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    private boolean isWall(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && map[row][col] == Color.BLUE;
    }

    public void draw(Graphics g, int tileSize) {
        g.setColor(Color.BLACK); // Set background color
        g.fillRect(0, 0, cols * tileSize, rows * tileSize); // Fill the background

        g.setColor(Color.BLUE); // Set wall color
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isWall(row, col)) {
                    // Draw only the edges that are not adjacent to another wall
                    if (!isWall(row - 1, col)) { // Top
                        g.drawLine(col * tileSize, row * tileSize, (col + 1) * tileSize, row * tileSize);
                    }
                    if (!isWall(row + 1, col)) { // Bottom
                        g.drawLine(col * tileSize, (row + 1) * tileSize, (col + 1) * tileSize, (row + 1) * tileSize);
                    }
                    if (!isWall(row, col - 1)) { // Left
                        g.drawLine(col * tileSize, row * tileSize, col * tileSize, (row + 1) * tileSize);
                    }
                    if (!isWall(row, col + 1)) { // Right
                        g.drawLine((col + 1) * tileSize, row * tileSize, (col + 1) * tileSize, (row + 1) * tileSize);
                    }
                }
            }
        }
    }
}
