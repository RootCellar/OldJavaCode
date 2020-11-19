package com.Darian.RPG;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI implements ActionListener {
    JLabel label = new JLabel("Not Halted");
    JLabel label2 = new JLabel("Loading...");
    JLabel label3 = new JLabel("Loading...");
    JLabel label4 = new JLabel("Loading...");
    JLabel label5 = new JLabel("Loading...");
    JFrame frame = new JFrame();

    public GUI() {
        
        JButton button = new JButton("Halt");
        button.addActionListener(this);
        button.setActionCommand("Halt");
        
        JButton button2 = new JButton("Characters");
        button2.addActionListener(this);
        button2.setActionCommand("chars");
        
        JButton button3 = new JButton("Generate NPC");
        button3.addActionListener(this);
        button3.setActionCommand("genchar");

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(button2);
        panel.add(button3);
        panel.add(label);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        panel.add(label5);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI");
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Halt")) {
            Server.halt(!Server.isHalted);
        }
        if(e.getActionCommand().equals("chars")) {
            Server.gui2.write("Characters: \n"+Character.getList());
        }
        if(e.getActionCommand().equals("genchar")) {
            Server.genChar(true);
        }
    }

    public void update() {
        label2.setText("Players: "+Server.r.size());
        if(Server.isHalted) {
            label.setText("Halted");
        } else {
            label.setText("Not Halted");
        }
        label3.setText("TPS: "+Server.ticks2);
        label4.setText("Characters: "+Server.characters.size());
        double used=(double)Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        double max=(double)Runtime.getRuntime().totalMemory();
        label5.setText(used+"/"+max+"("+(used/max)*100+"%)");
    }
}