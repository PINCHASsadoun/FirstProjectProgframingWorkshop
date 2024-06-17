import javax.swing.*;
import java.awt.*;

public class GameFrame {
    private JFrame frame;
    private AnimationPanel animationPanel;

    public GameFrame() {
        frame = new JFrame("pac-man");
        animationPanel = new AnimationPanel(this);
        frame.getContentPane().add(animationPanel);
        frame.setSize(new Dimension(750, 470));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.black);
        frame.setVisible(true);




    }

    public void resetWindow() {
        frame.getContentPane().remove(animationPanel);
        MainGamePanel mainGamePanel = new MainGamePanel();
        frame.getContentPane().add(mainGamePanel);

        frame.revalidate();
        frame.repaint();
    }
}
