package Field;

import IconManager.IconManager;
import World.World;

import javax.swing.*;

public class SquareField extends Field {

    public SquareField(int fieldNumber, IconManager iconManager, World world) {
        super(fieldNumber, iconManager, world);
        this.button = new JButton();
    }
}
