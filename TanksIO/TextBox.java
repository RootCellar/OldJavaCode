import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextBox {
    private JTextArea text = new JTextArea(30,30);
    private JFrame frame = new JFrame();

    public TextBox() {
        text.setEditable(false);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        JScrollPane scroll = new JScrollPane(text);
        panel.add(scroll);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Log");
        frame.pack();
        frame.setVisible(true);
        text.append("Finished opening log!\n");
    }

    public void write(String w) {
        text.append(w+"\n");
        text.setCaretPosition(text.getDocument().getLength());
    }
}