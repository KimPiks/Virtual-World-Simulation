package Logging;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Queue;

public class Logging {

    private final JTextArea logArea;
    private final Queue<String> logQueue;

    public Logging(JPanel panel) {
        this.logArea = new JTextArea();
        this.logArea.setEditable(false);
        this.logArea.setText("Simulation logging: \n");

        JScrollPane scrollPane = new JScrollPane(this.logArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 800, 600);
        panel.add(scrollPane);

        this.logQueue = new LinkedList<String>();
    }

    public void addLog(String log) {
        this.logQueue.add(log);
    }

    public void showLogs() {
        this.logArea.setText("Simulation logging: \n");
        int i = 1;
        while (!this.logQueue.isEmpty()) {
            this.logArea.append(i++ + ") " + this.logQueue.poll() + "\n");
        }
    }
}
