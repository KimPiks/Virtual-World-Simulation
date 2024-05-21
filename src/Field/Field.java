package Field;

import IconManager.IconManager;
import Organism.Organism;
import OrganismManager.*;
import World.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class Field extends JPanel {

    protected final int number;
    protected JButton button;
    protected Organism organism;
    protected ArrayList<Field> neighbours = new ArrayList<>();

    private final IconManager iconManager;
    private final World world;

    public Field(int fieldNumber, IconManager iconManager, World world) {
        this.number = fieldNumber;
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
            return;
        }

        try {
            int fieldSize = this.getButton().getWidth() / 3 * 2;
            if (this.world.getWorldSettings().worldType() == WorldType.RECTANGULAR && this.world.getWorldSettings().width() < this.world.getWorldSettings().height()) {
                fieldSize = this.getButton().getHeight() / 3 * 2;
            }
            Image image = this.iconManager.getIcon(organism.getData().getIconPath(), fieldSize);
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
                world.handleButtons();
                world.handleHumanAbility();
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

    public void showField(JPanel panel) {
        this.button.setFocusPainted(false);
        this.button.setContentAreaFilled(false);
        panel.add(this.button);
    }
}
