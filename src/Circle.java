import java.awt.*;

public class Circle {
    private int row;
    private int col;

    public Circle(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void draw(Graphics g, int tileSize) {
        int circleSize = tileSize / 2;
        int x = col * tileSize + tileSize / 4; // Center the circle in the tile horizontally
        int y = row * tileSize + tileSize / 4; // Center the circle in the tile vertically
        g.setColor(Color.pink);
        g.fillOval(x, y, circleSize, circleSize);
    }

    public Rectangle getBounds(int tileSize) {
        int circleSize = tileSize / 2;
        int x = col * tileSize + tileSize / 4;
        int y = row * tileSize + tileSize / 4;
        return new Rectangle(x, y, circleSize, circleSize);
    }
}
