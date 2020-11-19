import java.util.ArrayList;
public class Sudoku implements InputUser
{
    public Terminal term;
    public Display disp;
    public Solver solve;
    public static void main(String args[]) {
        new Sudoku();
    }
    
    public void Sudoku() {
        solve = new Solver(this);
        term = new Terminal();
        term.setUser(this);
        disp = new Display(this);
    }
    
    public void setBoard(ArrayList<String> b) {
        solve.setupBoard(b);
    }
    
    public void inputText(String i) {
        
    }
}