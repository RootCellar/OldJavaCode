
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Display extends JFrame implements ActionListener {
    public ArrayList<JTextField> inputs;
    public Sudoku sudo;
    public Display(Sudoku s) {
        sudo=s;
        inputs = new ArrayList<JTextField>();
        this.setSize(100,100);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10,9));
        panel.setBorder(BorderFactory.createEmptyBorder(20,30,10,30));

        //Text Fields for numbers
        for(int i=0; i<81; i++) {
            JTextField input = new JTextField();
            inputs.add(input);
            panel.add(input);
        }
        //END TEXT FIELDS

        //Buttons
        JButton button = new JButton("Solve");
        button.addActionListener(this);
        button.setActionCommand("SOLVE");
        panel.add(button);

        JButton button2 = new JButton("Validate");
        button2.addActionListener(this);
        button2.setActionCommand("VALIDATE");
        panel.add(button2);
        //END BUTTONS

        this.add(panel);
        this.setTitle("Sudoku Solver");
        this.pack();
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("SOLVE")) {
            String[][] s = new String[9][9];
            sudo.setBoard(getBoard());
            sudo.solve.solve();
        }
        if(e.getActionCommand().equals("VALIDATE")) {
            sudo.setBoard(getBoard());
            sudo.term.write(""+sudo.solve.isValid());
        }
    }

    public ArrayList<String> getBoard() {
        ArrayList<String> s = new ArrayList<String>();
        for(int y=0; y<9; y++) {
            for(int x=0; x<9; x++) {
                s.add(inputs.get( (y*9)+x ).getText());
                sudo.term.write(inputs.get( (y*9)+x ).getText());
                if(s.get( (y*9)+x ).equals("")) s.set( (y*9)+x, "-1");
            }
        }
        return s;
    }

    public void setBoard(Square[][] s) {
        for(int x=0; x<9; x++) {
            for(int y=0; y<9; y++) {
                inputs.get( (y*9)+x ).setText(s[x][y].num+"");
            }
        }
    }
}