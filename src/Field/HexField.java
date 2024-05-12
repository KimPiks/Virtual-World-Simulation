package Field;

import javax.swing.*;

public class HexField extends Field {

    public HexField(int fieldNumber, int x, int y, int size) {
        super(fieldNumber, x, y, size);
    }

    @Override
    public void showField(JFrame frame) {
        this.button = new HexButton(this.size);
        this.button.setBounds(this.x, this.y, this.size, this.size);
        this.button.setContentAreaFilled(false);
        frame.add(this.button);
    }
}
