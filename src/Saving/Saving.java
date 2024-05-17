package Saving;

import Animal.Animal;
import Human.Human;
import Organism.Organism;
import OrganismManager.*;
import Window.Window;
import World.*;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Saving {

    private World world;
    private final Window window;
    private final JFrame frame;

    private final String fileExtension = ".txt";

    public Saving(Window window, JFrame frame) {
        this.window = window;
        this.frame = frame;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void save() {
        String fileName = this.getFileName();

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        writer.println(world.getWorldId());
        writer.println(world.getWorldSettings().worldType());
        writer.println(world.getDay());
        writer.println(world.getWorldSettings().width());
        writer.println(world.getWorldSettings().height());

        Human human = world.getHuman();
        if (human != null) {
            writer.println(human.getAbilityDuration());
            writer.println(human.getAbilityCooldown());
        } else {
            writer.println(0);
            writer.println(0);
        }

        ArrayList<Organism> organisms = world.getOrganisms();
        writer.println(organisms.size());
        for (Organism organism : organisms) {
            writer.print(organism.getType() + " ");
            writer.print(organism.getData().getId() + " ");
            writer.print(organism.getData().getStrength() + " ");
            writer.print(organism.getCurrentField().getNumber() + " ");
            writer.print(organism.getData().getParent1Id() + " ");
            writer.print(organism.getData().getParent2Id() + " ");
            writer.print(organism.getData().getAge() + " ");

            if (organism instanceof Animal) {
                writer.print(((Animal) organism).getLastField().getNumber() + " ");
            } else {
                writer.print("-1 ");
            }

            writer.print(organism.getData().isBorn() + " ");
            writer.println(organism.getData().isAlive());
        }

        writer.close();

        this.showSavedDialog();
    }

    public World load(String fileName) {
        fileName += this.fileExtension;

        Path path = Paths.get(fileName);
        String[] read;
        try {
            read = Files.readAllLines(path).toArray(new String[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String line : read) {
            System.out.println(line);
        }

        String id = read[0];
        String worldType = read[1];
        int day = Integer.parseInt(read[2]);
        int width = Integer.parseInt(read[3]);
        int height = Integer.parseInt(read[4]);
        int abilityDuration = Integer.parseInt(read[5]);
        int abilityCooldown = Integer.parseInt(read[6]);

        WorldSettings worldSettings = new WorldSettings(WorldType.valueOf(worldType), width, height);
        if (worldType.equals("HEXAGONAL")) {
            this.world = new HexWorld(window, worldSettings);
        } else if (worldType.equals("RECTANGULAR")) {
            this.world = new RectangleWorld(window, worldSettings);
        }

        this.world.placeFields();
        this.world.setWorldId(id);
        this.world.setDay(day);

        ArrayList<Organism> organisms = new ArrayList<Organism>();
        int organismsCount = Integer.parseInt(read[7]);

        for (int i = 0; i < organismsCount; i++) {
            String[] organismData = read[8 + i].split(" ");
            String type = organismData[0];
            int organismId = Integer.parseInt(organismData[1]);
            int strength = Integer.parseInt(organismData[2]);
            int fieldNumber = Integer.parseInt(organismData[3]);
            int parent1Id = Integer.parseInt(organismData[4]);
            int parent2Id = Integer.parseInt(organismData[5]);
            int age = Integer.parseInt(organismData[6]);
            int lastFieldNumber = Integer.parseInt(organismData[7]);
            boolean born = Boolean.parseBoolean(organismData[8]);
            boolean alive = Boolean.parseBoolean(organismData[9]);

            Organism organism = null;
            if (type.equals("Human")) {
                organism = new Human(this.world, this.world.getField(fieldNumber), parent1Id, parent2Id);
                organism.getData().setId(organismId);
                organism.getData().setStrength(strength);
                organism.getData().setAge(age);
                organism.getData().setBorn(born);
                organism.getData().setAlive(alive);
                ((Human) organism).setAbilityDuration(abilityDuration);
                ((Human) organism).setAbilityCooldown(abilityCooldown);
            } else {
                organism = OrganismManager.createOrganism(OrganismType.valueOf(type), this.world, this.world.getField(fieldNumber));
                organism.getData().setId(organismId);
                organism.getData().setStrength(strength);
                organism.getData().setParent1Id(parent1Id);
                organism.getData().setParent2Id(parent2Id);
                organism.getData().setAge(age);
                organism.getData().setBorn(born);
                organism.getData().setAlive(alive);

                if (organism instanceof Animal) {
                    ((Animal) organism).setLastField(this.world.getField(lastFieldNumber));
                }
            }
            organisms.add(organism);
        }

        // get maximum organism id
        int maxId = 0;
        for (Organism organism : organisms) {
            if (organism.getData().getId() > maxId) {
                maxId = organism.getData().getId();
            }
        }
        this.world.setNextOrganismId(maxId+1);
        this.world.addOrganisms(organisms);

        this.window.setDayLabel(this.world.getDay());
        this.window.setHumanAbilityDurationLabel(abilityDuration+1);
        if (abilityDuration == 0) {
            this.window.setHumanAbilityCooldownLabel(abilityCooldown);
        }

        return this.world;
    }

    private void showSavedDialog() {
        JOptionPane.showMessageDialog(this.frame, "Simulation saved to: " + this.getFileName(), "Saved", JOptionPane.INFORMATION_MESSAGE);
    }

    private String getFileName() {
        return this.world.getWorldId() + "-" + this.world.getDay() + this.fileExtension;
    }
}
