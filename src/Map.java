import java.awt.*;

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
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row == 0) {
                    map[0][col] = Color.BLUE;
                } else if (row == rows - 1) {
                    map[rows - 1][col] = Color.blue;
                } else if (col == 0) {
                    map[row][0] = Color.BLUE;
                } else if (col == rows - 1) {
                    map[row][cols - 1] = Color.blue;
                }
            }
        }
//        map[rows][cols] = Color.blue;
        map[1][10] = Color.blue;
        map[1][18]=Color.blue;
        map[1][26]=Color.blue;

        map[2][2] = Color.blue;
        map[2][4] = Color.blue;
        map[2][5] = Color.blue;
        map[2][6] = Color.blue;
        map[2][8] = Color.blue;
        map[2][10] = Color.blue;
        map[2][12] = Color.blue;
        map[2][14] = Color.blue;
        map[2][15] = Color.blue;
        map[2][16] = Color.blue;
        map[2][18] = Color.blue;
        map[2][20] = Color.blue;
        map[2][21] = Color.blue;
        map[2][22] = Color.blue;
        map[2][24] = Color.blue;
        map[2][26] = Color.blue;
        map[2][28] = Color.blue;
        map[2][30] = Color.blue;
        map[2][31] = Color.blue;
        map[2][32] = Color.blue;
        map[2][34] = Color.blue;

        map[3][2]=Color.blue;
        map[3][8]=Color.blue;
        map[3][12]=Color.blue;
        map[3][16]=Color.blue;
        map[3][18]=Color.blue;
        map[3][20]=Color.blue;
        map[3][24]=Color.blue;
        map[3][28]=Color.blue;
        map[3][34]=Color.blue;

        map[4][2]=Color.blue;
        map[4][4]=Color.blue;
        map[4][5]=Color.blue;
        map[4][6]=Color.blue;
        map[4][8]=Color.blue;
        map[4][10]=Color.blue;
        map[4][11]=Color.blue;
        map[4][12]=Color.blue;
        map[4][14]=Color.blue;
        map[4][16]=Color.blue;
        map[4][18]=Color.blue;
        map[4][20]=Color.blue;
        map[4][22]=Color.blue;
        map[4][24]=Color.blue;
        map[4][25]=Color.blue;
        map[4][26]=Color.blue;
        map[4][28]=Color.blue;
        map[4][30]=Color.blue;
        map[4][31]=Color.blue;
        map[4][32]=Color.blue;
        map[4][34]=Color.blue;

        map[5][8]=Color.blue;
        map[5][10]=Color.blue;
        map[5][14]=Color.blue;
        map[5][22]=Color.blue;
        map[5][26]=Color.blue;
        map[5][28]=Color.blue;

        map[6][2]=Color.blue;
        map[6][4]=Color.blue;
        map[6][5]=Color.blue;
        map[6][6]=Color.blue;
        map[6][8]=Color.blue;
        map[6][10]=Color.blue;
        map[6][12]=Color.blue;
        map[6][13]=Color.blue;
        map[6][14]=Color.blue;
        map[6][16]=Color.blue;
        map[6][17]=Color.blue;
        map[6][18]=Color.blue;
        map[6][19]=Color.blue;
        map[6][20]=Color.blue;
        map[6][22]=Color.blue;
        map[6][23]=Color.blue;
        map[6][24]=Color.blue;
        map[6][26]=Color.blue;
        map[6][28]=Color.blue;
        map[6][30]=Color.blue;
        map[6][31]=Color.blue;
        map[6][32]=Color.blue;
        map[6][34]=Color.blue;

        map[7][2]=Color.blue;
        map[7][6]=Color.blue;
        map[7][30]=Color.blue;
        map[7][34]=Color.blue;

        map[8][2]=Color.blue;
        map[8][4]=Color.blue;
        map[8][6]=Color.blue;
        map[8][8]=Color.blue;
        map[8][10]=Color.blue;
        map[8][11]=Color.blue;
        map[8][12]=Color.blue;
        map[8][14]=Color.blue;
        map[8][22]=Color.blue;
        map[8][24]=Color.blue;
        map[8][25]=Color.blue;
        map[8][26]=Color.blue;
        map[8][28]=Color.blue;
        map[8][30]=Color.blue;
        map[8][32]=Color.blue;
        map[8][34]=Color.blue;

        map[9][4]=Color.blue;
        map[9][8]=Color.blue;
        map[9][14]=Color.blue;
        map[9][22]=Color.blue;
        map[9][28]=Color.blue;
        map[9][32]=Color.blue;

        map[10][2]=Color.blue;
        map[10][3]=Color.blue;
        map[10][4]=Color.blue;
        map[10][5]=Color.blue;
        map[10][6]=Color.blue;
        map[10][8]=Color.blue;
        map[10][10]=Color.blue;
        map[10][11]=Color.blue;
        map[10][12]=Color.blue;
        map[10][14]=Color.blue;
        map[10][22]=Color.blue;
        map[10][24]=Color.blue;
        map[10][25]=Color.blue;
        map[10][26]=Color.blue;
        map[10][28]=Color.blue;
        map[10][30]=Color.blue;
        map[10][31]=Color.blue;
        map[10][32]=Color.blue;
        map[10][33]=Color.blue;
        map[10][34]=Color.blue;

        map[11][8]=Color.blue;
        map[11][28]=Color.blue;

        map[12][2]=Color.blue;
        map[12][4]=Color.blue;
        map[12][5]=Color.blue;
        map[12][6]=Color.blue;
        map[12][8]=Color.blue;
        map[12][10]=Color.blue;
        map[12][12]=Color.blue;
        map[12][13]=Color.blue;
        map[12][14]=Color.blue;
        map[12][16]=Color.blue;
        map[12][17]=Color.blue;
        map[12][18]=Color.blue;
        map[12][19]=Color.blue;
        map[12][20]=Color.blue;
        map[12][22]=Color.blue;
        map[12][23]=Color.blue;
        map[12][24]=Color.blue;
        map[12][26]=Color.blue;
        map[12][28]=Color.blue;
        map[12][30]=Color.blue;
        map[12][31]=Color.blue;
        map[12][32]=Color.blue;
        map[12][34]=Color.blue;

        map[13][2]=Color.blue;
        map[13][6]=Color.blue;
        map[13][8]=Color.blue;
        map[13][10]=Color.blue;
        map[13][18]=Color.blue;
        map[13][26]=Color.blue;
        map[13][28]=Color.blue;
        map[13][30]=Color.blue;
        map[13][34]=Color.blue;

        map[14][2]=Color.blue;
        map[14][3]=Color.blue;
        map[14][4]=Color.blue;
        map[14][6]=Color.blue;
        map[14][8]=Color.blue;
        map[14][10]=Color.blue;
        map[14][11]=Color.blue;
        map[14][12]=Color.blue;
        map[14][14]=Color.blue;
        map[14][15]=Color.blue;
        map[14][16]=Color.blue;
        map[14][18]=Color.blue;
        map[14][20]=Color.blue;
        map[14][21]=Color.blue;
        map[14][22]=Color.blue;
        map[14][24]=Color.blue;
        map[14][25]=Color.blue;
        map[14][26]=Color.blue;
        map[14][28]=Color.blue;
        map[14][30]=Color.blue;
        map[14][32]=Color.blue;
        map[14][33]=Color.blue;
        map[14][34]=Color.blue;

        map[16][2]=Color.blue;
        map[16][4]=Color.blue;
        map[16][5]=Color.blue;
        map[16][6]=Color.blue;
        map[16][8]=Color.blue;
        map[16][9]=Color.blue;
        map[16][10]=Color.blue;
        map[16][12]=Color.blue;
        map[16][14]=Color.blue;
        map[16][16]=Color.blue;
        map[16][17]=Color.blue;
        map[16][18]=Color.blue;
        map[16][19]=Color.blue;
        map[16][20]=Color.blue;
        map[16][22]=Color.blue;
        map[16][24]=Color.blue;
        map[16][26]=Color.blue;
        map[16][27]=Color.blue;
        map[16][28]=Color.blue;
        map[16][30]=Color.blue;
        map[16][31]=Color.blue;
        map[16][32]=Color.blue;
        map[16][34]=Color.blue;

        map[17][2]=Color.blue;
        map[17][4]=Color.blue;
        map[17][12]=Color.blue;
        map[17][14]=Color.blue;
        map[17][18]=Color.blue;
        map[17][22]=Color.blue;
        map[17][24]=Color.blue;
        map[17][32]=Color.blue;
        map[17][34]=Color.blue;

        map[18][2]=Color.blue;
        map[18][4]=Color.blue;
        map[18][6]=Color.blue;
        map[18][7]=Color.blue;
        map[18][8]=Color.blue;
        map[18][10]=Color.blue;
        map[18][12]=Color.blue;
        map[18][14]=Color.blue;
        map[18][16]=Color.blue;
        map[18][18]=Color.blue;
        map[18][20]=Color.blue;
        map[18][22]=Color.blue;
        map[18][24]=Color.blue;
        map[18][26]=Color.blue;
        map[18][28]=Color.blue;
        map[18][29]=Color.blue;
        map[18][30]=Color.blue;
        map[18][32]=Color.blue;
        map[18][34]=Color.blue;

        map[19][10]=Color.blue;
        map[19][16]=Color.blue;
        map[19][20]=Color.blue;
        map[19][26]=Color.blue;



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
