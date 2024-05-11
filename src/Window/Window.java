package Window;

import Field.Field;
import Field.SquareField;
import Field.HexField;
import World.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window {

    public final int WINDOW_OFFSET = 50;
    public final int FIELD_SIZE = 30;

    private final JFrame frame;

    public Window() {
        this.frame = new JFrame();
        this.frame.setTitle("Virtual World Simulation");
        this.frame.setResizable(false);
    }

    public void addField(Field field) {
        this.frame.add(field);
    }

    public ArrayList<Field> getFields() {
        ArrayList<Field> fields = new ArrayList<>();
        Component[] components = this.frame.getContentPane().getComponents();
        for (Component c : components) {
            if (c.getClass().equals(SquareField.class) || c.getClass().equals(HexField.class)) {
                fields.add((Field) c);
            }
        }
        return fields;
    }

    public void displayFields() {
        ArrayList<Field> fields = this.getFields();
        for (Field field : fields) {
            field.showField(this.frame);
        }
        this.frame.setLayout(null);
    }

    public WorldSettings askForWorldSetting() {
        String[] options = {"Hexagonal", "Rectangular"};
        int worldType = JOptionPane.showOptionDialog(frame, "Choose the world type", "World type", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        int width = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the width of the world"));
        int height = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the height of the world"));
        return new WorldSettings(WorldType.values()[worldType], width, height);
    }

    public void setWindowSize(WorldType worldType, int worldWidth, int worldHeight) {
        if (worldType == WorldType.HEXAGONAL) {
            this.frame.setSize((worldWidth + worldHeight / 3 * 2) * FIELD_SIZE + WINDOW_OFFSET * 2, worldHeight * FIELD_SIZE + WINDOW_OFFSET * 3);
        } else {
            this.frame.setSize(worldWidth * FIELD_SIZE + WINDOW_OFFSET * 2, worldHeight * FIELD_SIZE + WINDOW_OFFSET * 3);
        }
    }

    public void setVisible(boolean visible) {
        this.frame.setVisible(visible);
    }
}