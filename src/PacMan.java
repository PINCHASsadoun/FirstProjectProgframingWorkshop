import java.awt.*;
import java.time.Duration;
import java.util.Objects;

public class PacMan {
    private int row;
    private int col;
    private Direction direction;
    private boolean mouthOpen;

    public PacMan(int row, int col) {
        this.row = row;
        this.col = col;
        this.direction = Direction.UP; // Default direction
        this.mouthOpen = false; // Initial state with mouth open
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
        this.mouthOpen = !this.mouthOpen; // Toggle mouth state on move
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void draw(Graphics g, int tileSize) {
        g.setColor(Color.YELLOW);
        int startAngle = 45;
        int arcAngle = mouthOpen ? 270 : 360; // Open or close mouth

        if(Objects.requireNonNull(direction)==Direction.UP) {
            startAngle = mouthOpen ? 135 : 90;
        } else if (direction == Direction.DOWN) {
            startAngle = mouthOpen ? 315 : 270;
        } else if (direction == Direction.LEFT) {
            startAngle = mouthOpen ? 225 : 180;
        } else if (direction == Direction.RIGHT){
            startAngle = mouthOpen ? 45:0;
        }

        g.fillArc(col * tileSize, row * tileSize, tileSize, tileSize, startAngle, arcAngle);
    }

    public Rectangle getBounds(int tileSize) {
        return new Rectangle(col * tileSize, row * tileSize, tileSize, tileSize);
    }

    public boolean canMoveTo(int newRow, int newCol, Map map) {
        return map.isPath(newRow, newCol) && (!map.isHQGate(newRow, newCol) || !map.isHQ(newRow, newCol));
    }
}
