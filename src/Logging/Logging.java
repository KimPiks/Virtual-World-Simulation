package Logging;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Logging {

    private final JFrame frame;
    private final JTextArea logArea;
    private final JScrollPane scrollPane;
    private final Queue<String> logQueue;

    public Logging() {
        this.frame = new JFrame();
        this.frame.setTitle("Virtual World Simulation Logging");
        this.frame.setResizable(true);

        this.logArea = new JTextArea();
        this.logArea.setEditable(false);

        this.scrollPane = new JScrollPane(this.logArea);
        this.scrollPane.setBounds(0, 0, 800, 600);
        this.frame.add(this.scrollPane);

        this.logQueue = new LinkedList<String>();

        this.frame.setLayout(null);
        this.frame.setSize(300, 600);
        this.frame.setVisible(true);

        this.frame.setLocation(1000, 0);
    }

    public void addLog(String log) {
        this.logQueue.add(log);
    }

    public void showLogs() {
        this.logArea.setText("");
        int i = 1;
        while (!this.logQueue.isEmpty()) {
            this.logArea.append(i++ + ") " + this.logQueue.poll() + "\n");
        }
    }
}
