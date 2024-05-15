package Field;

import IconManager.IconManager;
import Organism.Organism;
import OrganismManager.*;
import World.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private final World world;

    public Field(int fieldNumber, int x, int y, int size, IconManager iconManager, World world) {
        this.number = fieldNumber;
        this.x = x;
        this.y = y;
        this.size = size;

        this.iconManager = iconManager;
        this.world = world;
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


    public void addActionListener(JFrame frame) {
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (organism != null) return;

                OrganismType organismType = OrganismManager.openOrganismSelection(frame);
                if (organismType == null) return;

                Organism organism = OrganismManager.createOrganism(organismType, world, Field.this);
                if (organism == null) return;

                Field.this.setOrganism(organism);
                world.addOrganism(organism);
            }
        });
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
