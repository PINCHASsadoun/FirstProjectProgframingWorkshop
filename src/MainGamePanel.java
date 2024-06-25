import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainGamePanel extends JPanel {

    GameStatusUpdaterBar gameStatusBar = new GameStatusUpdaterBar();
    GamePanel gamePanel = new GamePanel(gameStatusBar, 1);

    public MainGamePanel(GameFrame gameFrame) {


        setLayout(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);
        add(gameStatusBar, BorderLayout.NORTH);

        gamePanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                gamePanel.requestFocusInWindow();
            }

        });
    }
}
