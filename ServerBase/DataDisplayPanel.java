
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DataDisplayPanel extends JPanel implements ActionListener {
    public JLabel[] labels;
    public DataDisplayPanel(int n) {
        //this.setSize(100,100);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        labels = new JLabel[n];
        //JPanel panel = new JPanel();
        this.setLayout(new GridLayout(20,10));
        this.setBorder(BorderFactory.createEmptyBorder(20,30,10,30));

        //Text Fields for numbers
        for(int i=0; i<n; i++) {
            labels[i]=new JLabel("NOT SET " + i);
            this.add(labels[i]);
        }
        //END TEXT FIELDS

        //Buttons
        /*
        JButton button = new JButton("Solve");
        button.addActionListener(this);
        button.setActionCommand("SOLVE");
        panel.add(button);
         */
        //END BUTTONS

        //this.add(panel);
        //this.setTitle("Data");
        //this.pack();
        //this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        
    }

    public void setText(int n, String s) {
        if(n>-1 && n<labels.length) {
            labels[n].setText(s);
        }
    }
}