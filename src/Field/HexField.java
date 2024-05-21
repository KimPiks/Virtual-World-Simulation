package Field;

import IconManager.IconManager;
import World.World;

import javax.swing.*;

public class HexField extends Field {

    public HexField(int fieldNumber, IconManager iconManager, World world) {
        super(fieldNumber, iconManager, world);
        this.button = new HexButton();
    }
}
