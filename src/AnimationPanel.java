import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class AnimationPanel extends JPanel {
    public final int PANEL_WIDTH = 750;
    public final int PANEL_HEIGHT = 470;
    private int x = 0;
    private int y = 0;
    private int xVelocity = 2;
    private int yVelocity = 2;
    private final int OBJECT_WIDTH = 250;
    private final int OBJECT_HEIGHT = 60;
    private JButton frameButton;
    private Image backgroundImage;
    private GameFrame gameframe;
    private Thread frameThread;

    public AnimationPanel(GameFrame gameframe) {
        this.gameframe = gameframe;
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        frameButton = new JButton();
        frameButton.setOpaque(false);
        frameButton.setContentAreaFilled(false);
        frameButton.setBorderPainted(false);
        frameButton.setBounds(x, y, OBJECT_WIDTH, OBJECT_HEIGHT);

//        ImageIcon icon = new ImageIcon(getClass().getResource("startButton.PNG"));
        ImageIcon icon = new ImageIcon("resources/startButton.PNG");

        frameButton.setIcon(icon);

        this.setLayout(null);
        this.add(frameButton);

        frameButton.addActionListener(e -> gameframe.resetWindow());

        // Load the background image
//        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("background.JPG"));
        ImageIcon backgroundIcon = new ImageIcon("resources/background.JPG");
        backgroundImage = backgroundIcon.getImage();

        frameThread = new Thread(() -> {
            while (true) {
                updatePosition();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        frameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, this);
    }

    public void updatePosition() {
        x += xVelocity;
        y += yVelocity;

        if (x <= 0 || x >= PANEL_WIDTH - OBJECT_WIDTH) {
            xVelocity = -xVelocity;
        }

        if (y <= 0 || y >= PANEL_HEIGHT - OBJECT_HEIGHT) {
            yVelocity = -yVelocity;
        }

        frameButton.setLocation(x, y);
    }
}
