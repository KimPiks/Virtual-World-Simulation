package Window;

import Field.Field;
import LayoutManagers.HexLayoutManager;
import Settings.Settings;
import World.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class Window extends JPanel {

    private final JFrame frame;
    private JLabel dayLabel;
    private JLabel humanAbilityDurationLabel;
    private JLabel humanAbilityCooldownLabel;

    private final JPanel mainPanel = new JPanel();
    private final JPanel labelsPanel = new JPanel();
    private final JPanel panel = new JPanel();
    private final JPanel loggingPanel = new JPanel();
    private final JPanel buttonsPanel = new JPanel();
    private final JPanel steeringPanel = new JPanel();

    private final ArrayList<Field> fields = new ArrayList<Field>();

    private World world;

    public Window() {
        this.frame = new JFrame();
        this.frame.setTitle("Virtual World Simulation");
        this.frame.setResizable(true);

        this.addLabels();
        this.addMainButtons();
    }

    private void addLabels() {
        this.dayLabel = new JLabel("Day: 0");
        this.humanAbilityDurationLabel = new JLabel("Ability duration: 0");
        this.humanAbilityCooldownLabel = new JLabel("Ability cooldown: 0");

        this.labelsPanel.add(this.dayLabel);
        this.labelsPanel.add(this.humanAbilityDurationLabel);
        this.labelsPanel.add(this.humanAbilityCooldownLabel);
    }

    private void addMainButtons() {
        JButton newTurnButton = new JButton("New turn");
        newTurnButton.setEnabled(false);
        newTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.makeTurn();
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.getSaving().save();
            }
        });

        JButton specialAbility = new JButton("Special ability");
        specialAbility.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (world.getHuman() == null || world.getHuman().getAbilityCooldown() > 0) return;

                world.getHuman().setAbilityDuration(Settings.HUMAN_ABILITY_DURATION);
                world.getHuman().setAbilityCooldown(Settings.HUMAN_ABILITY_COOLDOWN);

                world.handleHumanAbility();
                specialAbility.setEnabled(false);
            }
        });

        this.buttonsPanel.add(newTurnButton);
        this.buttonsPanel.add(saveButton);
        this.buttonsPanel.add(specialAbility);
    }

    public ArrayList<JButton> getButtons() {
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        ArrayList<Component> components = new ArrayList<Component>();

        components.addAll(Arrays.asList(this.buttonsPanel.getComponents()));
        components.addAll(Arrays.asList(this.steeringPanel.getComponents()));

        for (Component component : components) {
            if (component instanceof JButton) {
                buttons.add((JButton) component);
            }
        }
        return buttons;
    }

    public void addField(Field field) {
        this.panel.add(field.getButton());
        this.fields.add(field);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public JPanel getLoggingPanel() {
        return this.loggingPanel;
    }

    public void addPanels() {
        this.mainPanel.setLayout(new BorderLayout());
        this.labelsPanel.setLayout(new GridLayout(1, 3));
        this.labelsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        this.labelsPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));

        this.loggingPanel.setLayout(new GridLayout(1, 1));
        this.loggingPanel.setMaximumSize(new Dimension(200, Integer.MAX_VALUE));
        this.loggingPanel.setPreferredSize(new Dimension(200, Integer.MAX_VALUE));

        this.buttonsPanel.setLayout(new GridLayout(1, 1));

        if (world.getWorldSettings().worldType() == WorldType.RECTANGULAR) {
            this.panel.setLayout(new GridLayout(this.world.getWorldSettings().height(), this.world.getWorldSettings().width()));
        } else {
            this.panel.setLayout(new HexLayoutManager(this.world.getWorldSettings().width(), this.world.getWorldSettings().height()));
        }

        this.addSteeringPanel();

        this.mainPanel.add(this.labelsPanel, BorderLayout.NORTH);
        this.mainPanel.add(this.panel, BorderLayout.CENTER);
        this.mainPanel.add(this.loggingPanel, BorderLayout.EAST);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2, 1));
        southPanel.add(this.buttonsPanel);
        southPanel.add(this.steeringPanel);

        this.mainPanel.add(southPanel, BorderLayout.SOUTH);
        getFrame().add(this.mainPanel);
    }

    private void addSteeringPanel() {
        this.addCommonSteering();

        if (this.world.getWorldSettings().worldType() == WorldType.HEXAGONAL) {
            this.addHexagonalSteering();
        } else {
            this.addRectangularSteering();
        }
    }

    private void addCommonSteering() {
        JButton leftMove = new JButton("Move left");
        JButton moveRight = new JButton("Move right");

        leftMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (world.getHuman() == null) return;

                world.playerMoveLeft();
            }
        });

        moveRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (world.getHuman() == null) return;

                world.playerMoveRight();
            }
        });

        this.steeringPanel.add(leftMove);
        this.steeringPanel.add(moveRight);
    }

    private void addRectangularSteering() {
        JButton moveUp = new JButton("Move up");
        JButton moveDown = new JButton("Move down");

        moveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (world.getHuman() == null) return;

                world.playerMoveUp();
            }
        });

        moveDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (world.getHuman() == null) return;

                world.playerMoveDown();
            }
        });

        this.steeringPanel.add(moveUp);
        this.steeringPanel.add(moveDown);
    }

    private void addHexagonalSteering() {
        JButton moveUpLeft = new JButton("Move up left");
        JButton moveUpRight = new JButton("Move up right");
        JButton moveDownLeft = new JButton("Move down left");
        JButton moveDownRight = new JButton("Move down right");

        moveUpLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (world.getHuman() == null) return;

                world.playerMoveUpLeft();
            }
        });

        moveUpRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (world.getHuman() == null) return;

                world.playerMoveUpRight();
            }
        });

        moveDownLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (world.getHuman() == null) return;

                world.playerMoveDownLeft();
            }
        });

        moveDownRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (world.getHuman() == null) return;

                world.playerMoveDownRight();
            }
        });

        this.steeringPanel.add(moveUpLeft);
        this.steeringPanel.add(moveUpRight);
        this.steeringPanel.add(moveDownLeft);
        this.steeringPanel.add(moveDownRight);
    }

    public ArrayList<Field> getFields() {
        return this.fields;
    }

    public void displayFields() {
        ArrayList<Field> fields = this.getFields();
        for (Field field : fields) {
            field.showField(this.panel);
            field.addActionListener(this.frame);
        }
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
            // Up neighbour
            if (field.getNumber() - width > 0 && fields.get(field.getNumber() - width - 1) != null && field.getButton().getY() > fields.get(field.getNumber() - width - 1).getButton().getY()) {
                field.addNeighbour(fields.get(field.getNumber() - width - 1));
            }

            // Down neighbour
            if (field.getNumber() + width <= width * height && fields.get(field.getNumber() + width - 1) != null && field.getButton().getY() < fields.get(field.getNumber() + width - 1).getButton().getY()) {
                field.addNeighbour(fields.get(field.getNumber() + width - 1));
            }

            // Left neighbour
            if (field.getNumber() - 1 > 0 && fields.get(field.getNumber() - 1 - 1) != null && field.getButton().getY() == fields.get(field.getNumber() - 1 - 1).getButton().getY()) {
                field.addNeighbour(fields.get(field.getNumber() - 1 - 1));
            }

            // Right neighbour
            if (field.getNumber() + 1 <= width * height && fields.get(field.getNumber() + 1 - 1) != null && field.getButton().getY() == fields.get(field.getNumber() + 1 - 1).getButton().getY()) {
                field.addNeighbour(fields.get(field.getNumber() + 1 - 1));
            }
        }
    }

    private void addHexNeighbours(ArrayList<Field> fields, int width, int height) {
        for (Field field : fields) {
            // Left neighbour
            if (field.getNumber() - 1 > 0 && fields.get(field.getNumber() - 1 - 1) != null && field.getButton().getY() == fields.get(field.getNumber() - 1 - 1).getButton().getY()) {
                field.addNeighbour(fields.get(field.getNumber() - 1 - 1));
            }
            // Right neighbour
            if (field.getNumber() + 1 <= width * height && fields.get(field.getNumber() + 1 - 1) != null && field.getButton().getY() == fields.get(field.getNumber() + 1 - 1).getButton().getY()) {
                field.addNeighbour(fields.get(field.getNumber() + 1 - 1));
            }
            // Down right neighbour
            if (field.getNumber() + width <= width * height && fields.get(field.getNumber() + width - 1) != null && field.getButton().getY() < fields.get(field.getNumber() + width - 1).getButton().getY()) {
                field.addNeighbour(fields.get(field.getNumber() + width - 1));
            }
            // Down left neighbour
            if (field.getNumber() + width - 1 <= width * height && fields.get(field.getNumber() + width - 1 - 1) != null && field.getButton().getY() < fields.get(field.getNumber() + width - 1 - 1).getButton().getY()) {
                field.addNeighbour(fields.get(field.getNumber() + width - 1 - 1));
            }
            // Up left neighbour
            if (field.getNumber() - width > 0 && fields.get(field.getNumber() - width - 1) != null && field.getButton().getY() > fields.get(field.getNumber() - width - 1).getButton().getY()) {
                field.addNeighbour(fields.get(field.getNumber() - width - 1));
            }
            // Up right neighbour
            if (field.getNumber() - width + 1 > 0 && fields.get(field.getNumber() - width + 1 - 1) != null  && field.getButton().getY() > fields.get(field.getNumber() - width + 1 - 1).getButton().getY()) {
                field.addNeighbour(fields.get(field.getNumber() - width + 1 - 1));
            }
        }
    }

    public WorldSettings askForWorldSetting() {
        String[] options = {"Hexagonal", "Rectangular"};
        int worldType = JOptionPane.showOptionDialog(frame, "Choose the world type", "World type", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (worldType == -1) System.exit(0);

        String widthStr = JOptionPane.showInputDialog(frame, "Enter the width of the world");
        if (widthStr == null || widthStr.isEmpty()) System.exit(0);
        int width = Integer.parseInt(widthStr);
        if (width < 1) System.exit(0);

        String heightStr = JOptionPane.showInputDialog(frame, "Enter the height of the world");
        if (heightStr == null || heightStr.isEmpty()) System.exit(0);
        int height = Integer.parseInt(heightStr);
        if (height < 1) System.exit(0);

        return new WorldSettings(WorldType.values()[worldType], width, height);
    }

    public MenuOption askForMenuOption() {
        String[] options = {"New simulation", "Load simulation", "Exit"};
        int option = JOptionPane.showOptionDialog(frame, "Choose an option", "Virtual World Simulation Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (option == -1 || option == 2) System.exit(0);
        return MenuOption.values()[option];
    }

    public String askForSaveFileName() {
        String fileName = JOptionPane.showInputDialog(frame, "Enter the name of the file");
        if (fileName == null || fileName.isEmpty()) System.exit(0);
        return fileName;
    }

    public void setVisible(boolean visible) {
        this.frame.setVisible(visible);
    }

    public void setDayLabel(int day) {
        this.dayLabel.setText("Day: " + day);
    }

    public void setHumanAbilityDurationLabel(int duration) {
        this.humanAbilityDurationLabel.setText("Ability duration: " + duration);
    }

    public void setHumanAbilityCooldownLabel(int cooldown) {
        this.humanAbilityCooldownLabel.setText("Ability cooldown: " + cooldown);
    }

    public void removeHumanAbilityComponents() {
        this.humanAbilityDurationLabel.setText("");
        this.humanAbilityCooldownLabel.setText("");
    }

    public JFrame getFrame() {
        return this.frame;
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