import java.util.ArrayList;
public class Solver
{
    public Square[][] board;
    public Sudoku sudo;
    public Solver(Sudoku s) {
        sudo = s;
        board = new Square[9][9];
    }

    public int[] getNext() {
        for(int x=0; x<9; x++) {
            for(int y=0; y<9; y++) {
                if(board[x][y].known==false && board[x][y].num==-1) {
                    int[] nums = {x,y};
                    return nums;
                }
            }
        }
        int[] nums = {-1,-1};
        return nums;
    }

    public void setupBoard(ArrayList<String> s) {
        for(int x=0; x<9; x++) {
            for(int y=0; y<9; y++) {
				board[x][y] = new Square();
				board[x][y].num = Integer.parseInt(s.get((y*9)+x));
                if(board[x][y].num!=-1) {
                    board[x][y].known=true;
                }
            }
        }
    }

    public void solve() {
        if(isValid()==false) {
            sudo.term.write("INVALID START POSITION");
            return;
        }
        if(recSolveStart(getNext())==false) {
			sudo.term.write("Well, that was awkward. Puzzle could not be solved!");
        }
		else {
			sudo.term.write("Solved!");
		}
    }

    public boolean recSolveStart(int[] loc) {
		for(int i=1; i<=9; i++) {
			recSolve(getNext());
		}
        return false;
    }
	
	public boolean recSolve(int[] loc) {
		Square s = board[loc[0]][loc[1]];
		for(int i=1; i<=9; i++) {
			s.num=i;
			if(isValid() && getNext()[0]==-1) {
				return true;
			}
			if(isValid() && getNext()[0]!=-1) {
				if(recSolve(getNext())) {
					return true;
				}
			}
		}
		s.num=-1;
		return false;
	}

    public boolean isValid() {
        for(int x=0; x<9; x++) {
            for(int y=0; y<9; y++) {

                for(int x1=0; x1<9; x1++) {
                    if(x!=x1 && board[x][y].num!=-1 && board[x1][y].num!=-1 && board[x][y].num==board[x1][y].num) {
                        return false;
                    }
                }

                for(int y1=0; y1<9; y1++) {
                    if(y!=y1 && board[x][y].num!=-1 && board[x][y1].num!=-1 && board[x][y].num==board[x][y1].num) {
                        return false;
                    }
                }

                int[] nums = getBox(x,y);
                for(int x1=nums[0]; x1<=nums[1]; x1++) {
                    for(int y1=nums[2]; y1<=nums[3]; y1++) {
                        if(x1!=x && y1!=y && board[x][y].num!=-1 && board[x1][y].num!=-1 && board[x][y].num==board[x1][y1].num) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public int[] getBox(int x, int y) {
        int[] ret = new int[4];

        if(x<=9) {
            ret[0]=6;
            ret[1]=8;
        }
        if(x<=6) {
            ret[0]=3;
            ret[1]=5;
        }
        if(x<=3) {
            ret[0]=0;
            ret[1]=2;
        }

        if(y<=9) {
            ret[2]=6;
            ret[3]=8;
        }
        if(y<=6) {
            ret[2]=3;
            ret[3]=5;
        }
        if(y<=3) {
            ret[2]=0;
            ret[3]=2;
        }
        return ret;
    }
}