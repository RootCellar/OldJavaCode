
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Display extends JFrame implements ActionListener {
    public JTextField[][] inputs;
    public JPanel panel;
    public int w,h;
    public LinearSystemsSolver l = new LinearSystemsSolver();
    public Display(int width, int height) {
        w=width;
        h=height;
        inputs = new JTextField[h][w];
        this.setSize(100,100);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new GridLayout(width,height));
        panel.setBorder(BorderFactory.createEmptyBorder(20,30,10,30));

        //Text Fields for numbers
        for(int i=0; i<inputs.length; i++) {
            for(int i2=0; i2<inputs[0].length; i2++) {
                inputs[i][i2] = new JTextField();
                panel.add(inputs[i][i2]);
                //inputs[i][i2].setText(i+" "+i2);
            }
        }
        //END TEXT FIELDS

        //Buttons
        JButton button = new JButton("Solve");
        button.addActionListener(this);
        button.setActionCommand("SOLVE");
        panel.add(button);
        //END BUTTONS

        this.add(panel);
        this.setTitle("Linear Systems of Equations");
        this.pack();
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("SOLVE")) {
            String[][] s = new String[h][w];
            for(int i=0; i<s.length; i++) {
                for(int i2=0; i2<s[0].length; i2++) {
                    s[i][i2]=inputs[i][i2].getText();

                }
            }
            l.setMatrix(s);
            l.solve();
            for(int i=0; i<s.length; i++) {
                for(int i2=0; i2<s[0].length; i2++) {
                    inputs[i][i2].setText(l.matrix[i][i2]+"");

                }
            }
        }
    }
}