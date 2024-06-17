import java.awt.*;
import java.util.Objects;

public class Dot {
    private int row;
    private int col;

    public Dot(int row, int col) {
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
        int dotSize = tileSize / 5;
        int x = col * tileSize + tileSize / 3; // Center the circle in the tile horizontally
        int y = row * tileSize + tileSize / 3; // Center the circle in the tile vertically
        g.setColor(Color.white);
        g.fillOval(x, y, dotSize, dotSize);
    }

    public Rectangle getBounds(int tileSize) {
        int dotSize = tileSize / 5;
        int x = col * tileSize + tileSize / 3;
        int y = row * tileSize + tileSize / 3;
        return new Rectangle(x, y, dotSize, dotSize);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dot dot = (Dot) o;
        return row == dot.row && col == dot.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
