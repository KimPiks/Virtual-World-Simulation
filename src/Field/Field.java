package Field;

import IconManager.IconManager;
import Organism.Organism;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public abstract class Field extends JPanel {

    protected final int number;
    protected final int x;
    protected final int y;
    protected final int size;
    protected JButton button;
    protected Organism organism;
    protected ArrayList<Field> neighbours = new ArrayList<>();

    private final IconManager iconManager;

    public Field(int fieldNumber, int x, int y, int size, IconManager iconManager) {
        this.number = fieldNumber;
        this.x = x;
        this.y = y;
        this.size = size;

        this.iconManager = iconManager;
    }

    public JButton getButton() {
        return this.button;
    }

    public Organism getOrganism() {
        return this.organism;
    }

    public void setOrganism(Organism organism) {
        this.organism = organism;

        if (organism == null) {
            button.setIcon(null);
            button.setText("" + this.getNumber());
            return;
        }

        try {
            Image image = this.iconManager.getIcon(organism.getData().getIconPath());
            button.setText("");
            button.setIcon(new ImageIcon(image));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addNeighbour(Field field) {
        this.neighbours.add(field);
    }

    public ArrayList<Field> getNeighbours() {
        return this.neighbours;
    }

    public int getNumber() {
        return this.number;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public abstract void showField(JFrame frame);
}
