package Field;

import javax.swing.*;

public abstract class Field extends JPanel {

    protected final int x;
    protected final int y;
    protected final int size;
    protected JButton button;

    public Field(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public JButton getButton() {
        return this.button;
    }

    public abstract void showField(JFrame frame);
}
