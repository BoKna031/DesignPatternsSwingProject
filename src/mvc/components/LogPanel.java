package mvc.components;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel{

    private final JTextArea logArea;

    public LogPanel(Dimension dimension){
        this.setLayout(new BorderLayout());
        logArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(dimension);
        logArea.setEditable(false);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public JTextArea getLogArea(){
        return logArea;
    }

    public void addLog(String log){
        logArea.append(log + "\n");
    }

    public void clearLogArea(){
        logArea.setText("");
    }
}
