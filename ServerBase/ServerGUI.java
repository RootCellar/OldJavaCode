import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ServerGUI implements ActionListener {
    JFrame frame = new JFrame();
    InputUser user;
    TerminalPanel term = new TerminalPanel();
    TerminalPanel term2 = new TerminalPanel();
    DataDisplayPanel dataDisplay;
    public ServerGUI(int dataCount) {
        dataDisplay = new DataDisplayPanel(dataCount);
        
        JTabbedPane tabbedPane = new JTabbedPane();

        JTabbedPane tabbedPane2 = new JTabbedPane();
        /**
        JButton button = new JButton("Normal");
        button.addActionListener(this);
        button.setActionCommand("Normal");

        JButton button2 = new JButton("Christmas");
        button2.addActionListener(this);
        button2.setActionCommand("Christmas");

        JButton button3 = new JButton("Green");
        button3.addActionListener(this);
        button3.setActionCommand("Green");

        JButton button4 = new JButton("Purple");
        button4.addActionListener(this);
        button4.setActionCommand("Purple");

        JButton button5 = new JButton("Clear Log");
        button5.addActionListener(this);
        button5.setActionCommand("CLLOG");
         */
        frame.setLayout( new GridLayout(1, 2) );

        //Create Main tab
        
        JPanel panel = new JPanel();
        //panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(4, 2));

        panel.add( getNewButton("Halt Server","/halt") );
        panel.add( getNewButton("BLANK","BLANK") );
        panel.add( getNewButton("BLANK","BLANK") );
        panel.add( getNewButton("BLANK","BLANK") );
        panel.add( getNewButton("BLANK","BLANK") );
        panel.add( getNewButton("BLANK", "BLANK") );
        panel.add( getNewButton("BLANK", "BLANK") );
        panel.add( getNewButton("BLANK", "BLANK") );

        //Set up tabs
        
        tabbedPane.add("Main Tab", panel);
        
        tabbedPane.add("Info Tab", dataDisplay);
        
        //tabbedPane.add("Terminal Tab", term);
        
        //Set up panels and buttons for Tabcursion
        
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(4, 2));

        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );

        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(4, 2));

        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        
        JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayout(4, 2));

        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        
        JPanel panel6 = new JPanel();
        
        JProgressBar bar = new JProgressBar();
        
        //bar.setMinimum(0);
        //bar.setMaximum(100);
        //bar.setValue(43);
        bar.setIndeterminate(true);
        
        panel6.add( bar );

        //Set up the Tabcursion
        tabbedPane2.add("Buttons", panel3);
        tabbedPane2.add("More Buttons", panel4);
        tabbedPane2.add("Even More Buttons",panel5);
        
        tabbedPane.add("TEST",panel6);

        //Add the last tab
        tabbedPane.add("Tabcursion",tabbedPane2);

        //frame.add(panel, BorderLayout.CENTER);

        tabbedPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        term2.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        frame.add(tabbedPane);
        
        frame.add(term2);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Server GUI");
        frame.pack();
        //frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        user.inputText(command);
        /*
        if(false) {
            
        }
        else {
            r.GUIClick(e.getActionCommand());
        }
        */
    }


    public void update() {

    }

    public void out(String s) {
        
    }

    public JButton getNewButton(String name, String command) {
        JButton button = new JButton(name);
        button.addActionListener(this);
        button.setActionCommand(command);
        return button;
    }
}