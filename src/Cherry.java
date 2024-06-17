import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Cherry {
    private int row;
    private int col;
    private final Image image;

    public Cherry(int row, int col, String imagePath) {
        this.row = row;
        this.col = col;
        this.image = loadImage(imagePath);
    }

    private Image loadImage(String path) {
        try {
            return ImageIO.read(new File( path));
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


    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void draw(Graphics g, int tileSize) {
        g.drawImage(image, col * tileSize, row * tileSize, tileSize, tileSize, null);
    }

    public Rectangle getBounds(int tileSize) {
        return new Rectangle(col * tileSize, row * tileSize, tileSize, tileSize);
    }
}
