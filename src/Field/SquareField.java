package Field;

import javax.swing.*;

public class SquareField extends Field {

    public SquareField(int x, int y, int size) {
        super(x, y, size);
    }

    @Override
    public void showField(JFrame frame) {
        this.button = new JButton();
        this.button.setBounds(this.x, this.y, this.size, this.size);
        this.button.setFocusPainted(false);
        this.button.setContentAreaFilled(false);
        frame.add(this.button);
    }

}
