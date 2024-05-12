package Window;

import Field.Field;
import Field.SquareField;
import Field.HexField;
import World.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Window extends JPanel {

    public final int WINDOW_OFFSET = 50;
    public final int FIELD_SIZE = 50;

    private final JFrame frame;
    private final JLabel dayLabel;

    private World world;

    public Window() {
        this.frame = new JFrame();
        this.frame.setTitle("Virtual World Simulation");
        this.frame.setResizable(false);

        this.dayLabel = new JLabel("Day: 0");
        this.dayLabel.setBounds(WINDOW_OFFSET / 4, WINDOW_OFFSET / 4, 100, 20);
        this.frame.add(this.dayLabel);
    }

    public void addField(Field field) {
        this.frame.add(field);
    }

    public void setWorld(World world) {
        this.world = world;
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

    public void setFieldsNeighbours() {
        ArrayList<Field> fields = this.getFields();
        int width = this.world.getWorldSettings().width();
        int height = this.world.getWorldSettings().height();

        if (this.world.getWorldSettings().worldType() == WorldType.HEXAGONAL) {
            this.addHexNeighbours(fields, width, height);
        } else {
            this.addRectangularNeighbours(fields, width, height);
        }
    }

    private void addRectangularNeighbours(ArrayList<Field> fields, int width, int height) {
        for (Field field : fields) {
            // Left neighbour
            if (field.getNumber() - height > 0 && fields.get(field.getNumber() - height - 1) != null && field.getY() == fields.get(field.getNumber() - height - 1).getY()) {
                field.addNeighbour(fields.get(field.getNumber() - height - 1));
            }

            // Right neighbour
            if (field.getNumber() + height <= width * height && fields.get(field.getNumber() + height - 1) != null && field.getY() == fields.get(field.getNumber() + height - 1).getY()) {
                field.addNeighbour(fields.get(field.getNumber() + height - 1));
            }

            // Up neighbour
            if (field.getNumber() - 1 > 0 && fields.get(field.getNumber() - 1 - 1) != null && field.getY() > fields.get(field.getNumber() - 1 - 1).getY()) {
                field.addNeighbour(fields.get(field.getNumber() - 1 - 1));
            }

            // Down neighbour
            if (field.getNumber() + 1 <= width * height && fields.get(field.getNumber() + 1 - 1) != null && field.getY() < fields.get(field.getNumber() + 1 - 1).getY()) {
                field.addNeighbour(fields.get(field.getNumber() + 1 - 1));
            }
        }
    }

    private void addHexNeighbours(ArrayList<Field> fields, int width, int height) {
        for (Field field : fields) {
            // Left neighbour
            if (field.getNumber() - height > 0 && fields.get(field.getNumber() - height - 1) != null && field.getY() == fields.get(field.getNumber() - height - 1).getY()) {
                field.addNeighbour(fields.get(field.getNumber() - height - 1));
            }
            // Right neighbour
            if (field.getNumber() + height <= width * height && fields.get(field.getNumber() + height - 1) != null && field.getY() == fields.get(field.getNumber() + height - 1).getY()) {
                field.addNeighbour(fields.get(field.getNumber() + height - 1));
            }
            // Down right neighbour
            if (field.getNumber() + 1 <= width * height && fields.get(field.getNumber() + 1 - 1) != null && field.getY() < fields.get(field.getNumber() + 1 - 1).getY()) {
                field.addNeighbour(fields.get(field.getNumber() + 1 - 1));
            }
            // Down left neighbour
            if (field.getNumber() - height + 1 > 0 && fields.get(field.getNumber() - height + 1 - 1) != null && field.getY() < fields.get(field.getNumber() - height + 1 - 1).getY()) {
                field.addNeighbour(fields.get(field.getNumber() - height + 1 - 1));
            }
            // Up left neighbour
            if (field.getNumber() - 1 > 0 && fields.get(field.getNumber() - 1 - 1) != null && field.getY() > fields.get(field.getNumber() - 1 - 1).getY()) {
                field.addNeighbour(fields.get(field.getNumber() - 1 - 1));
            }
            // Up right neighbour
            if (field.getNumber() + height - 1 <= width * height && fields.get(field.getNumber() + height - 1 - 1) != null  && field.getY() > fields.get(field.getNumber() + height - 1 - 1).getY()) {
                field.addNeighbour(fields.get(field.getNumber() + height - 1 - 1));
            }
        }
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

    public void setDayLabel(int day) {
        this.dayLabel.setText("Day: " + day);
    }

    public void addKeyListener() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(e -> {
                    if (e.getID() != KeyEvent.KEY_PRESSED) return false;
                    this.world.keyAction(e.getKeyCode());
                    return true;
                });

    }
}