import javax.swing.*;
import java.util.LinkedList;
import java.util.Queue;

public class LiveHeartsStatusPanel extends JPanel {

    private int liveHearts;
    private final Queue<JLabel> livesHeartLabelsSet = new LinkedList<>();

    public LiveHeartsStatusPanel(int livesCount) {
        liveHearts = livesCount;
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        for (int i = 0; i < livesCount; i++) {
            JLabel liveHeartLabel = createLiveHeartLabel();
            add(liveHeartLabel);
            livesHeartLabelsSet.add(liveHeartLabel);
        }
    }

    private JLabel createLiveHeartLabel() {
        JLabel liveHeartLabel = new JLabel(new ImageIcon("resources/heart.png"));
        liveHeartLabel.setOpaque(false);
        return liveHeartLabel;
    }

    public void removeHeart(){
        if(liveHearts > 0) {
            JLabel removedHeart = livesHeartLabelsSet.poll();
            remove(removedHeart);
            liveHearts--;
            repaint();
        }
    }
}
