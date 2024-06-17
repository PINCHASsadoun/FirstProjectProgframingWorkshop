import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovingButton implements ActionListener {
    private AnimationPanel panel;

    public MovingButton(AnimationPanel panel) {
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.updatePosition();
    }
}
