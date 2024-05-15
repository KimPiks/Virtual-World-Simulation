package Field;

import IconManager.IconManager;
import World.World;

import javax.swing.*;

public class SquareField extends Field {

    public SquareField(int fieldNumber, int x, int y, int size, IconManager iconManager, World world) {
        super(fieldNumber, x, y, size, iconManager, world);
    }

    @Override
    public void showField(JFrame frame) {
        this.button = new JButton();
        this.button.setBounds(this.x, this.y, this.size, this.size);
        this.button.setFocusPainted(false);
        this.button.setContentAreaFilled(false);
        frame.add(this.button);

        // Set font size and field number
        button.setFont(button.getFont().deriveFont(7f));
        button.setText("" + this.getNumber());
    }
}
