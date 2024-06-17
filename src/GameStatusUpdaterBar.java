import javax.swing.*;
import java.awt.*;

public class GameStatusUpdaterBar extends JPanel implements GameStatusUpdater {
    JLabel scoreLabel = new JLabel("Score: ");
    LiveHeartsStatusPanel liveHeartsStatusPanel = new LiveHeartsStatusPanel( GamePanel.LIVES_COUNT);
    public GameStatusUpdaterBar() {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        add(scoreLabel, BorderLayout.WEST);
        add(liveHeartsStatusPanel,BorderLayout.EAST);

        scoreLabel.setOpaque(false);
        scoreLabel.setForeground(Color.yellow);
    }

    @Override
    public void setScore(int score) {
        scoreLabel.setText("Score: "+score);
    }

    @Override
    public void removeHeart(){
        liveHeartsStatusPanel.removeHeart();
    }

}
