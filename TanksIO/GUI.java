import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI implements ActionListener {
    private int clicks = 0;
    private JLabel label = new JLabel("Not Halted");
    private JLabel label2 = new JLabel("Loading...");
    private JFrame frame = new JFrame();

    public GUI() {
        
        JButton button = new JButton("Halt");
        button.addActionListener(this);
        button.setActionCommand("Halt");

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);
        panel.add(label2);
        JLabel label2 = new JLabel("Loading...");

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI");
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        
    }

    public void update() {
        label2.setText("Players: "+Server.r.size());
        
    }
}