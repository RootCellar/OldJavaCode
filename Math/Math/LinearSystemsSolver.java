public class LinearSystemsSolver
{
    public double[][] matrix;

    public void setMatrix(double[][] m) {
        matrix=m;
    }

    public void setMatrix(String[][]s) {
        matrix = new double[s.length][s[0].length];
        for(int i=0; i<s.length; i++) {
            for(int i2=0; i2<s[0].length; i2++) {
                matrix[i][i2]=Double.parseDouble(s[i][i2]);
            }
        }
    }

    public void solve() {
        long start = System.nanoTime();
        for(int i=0; i<matrix.length; i++) {
            reduce(i);
        }
        
        

        System.out.println(System.nanoTime()-start+" ns to solve");
    }

    public void reduce(int w) {
        divideRow(w,matrix[w][w]);
    }

    public void divideRow(int w, double b) {
        for(int i=0; i<matrix[0].length; i++) {
            matrix[w][i]/=b;
        }
    }
}