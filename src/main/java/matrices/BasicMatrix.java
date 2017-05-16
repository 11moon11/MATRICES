package matrices;

/**
 * Created by kiril on 15.05.2017.
 */

// TODO: Refactor this class to work with 'Arithmetic Expression' instead of 'Integer' class.
public abstract class BasicMatrix {
    private Integer[][] matrix;
    private int rows;
    private int cols;

    public BasicMatrix(int rows, int cols) throws MatrixException {
        if(rows < 1 || cols < 1)
            throw new MatrixException("Unable to set size to r=" + rows + " c=" + cols + ". Minimum matrix size is '1x1'");

        this.rows = rows;
        this.cols = cols;

        matrix = new Integer[rows][cols];
        for(int a=0; a<rows; a++) {
            for(int i=0; i<cols; i++) {
                matrix[a][i] = null;
            }
        }
    }

    public void set(int[][] val) throws MatrixException {
        int r = val.length;
        int c = val[0].length;

        if(r != rows || c != cols)
            throw new MatrixException("Array has wrong dimensions");

        for(int a=0; a<r; a++) {
            for(int i=0; i<c; i++) {
                matrix[a][i] = val[a][i];
            }
        }
    }

    public void set(int[] val) throws MatrixException {
        int r = val.length;

        if(r != rows * cols)
            throw new MatrixException("Array has wrong length");

        for(int v : val)
            setNextElement(v);
    }

    public void setNextElement(Integer val) {
        for(int a=0; a<rows; a++) {
            for(int i=0; i<cols; i++) {
                if(matrix[a][i] == null) {
                    matrix[a][i] = val;
                    return;
                }
            }
        }

        throw new MatrixException("Matrix is already complete");
    }

    public Integer getVal(int r, int c) {
        validateBounds(r, c);

        return matrix[r-1][c-1];
    }

    public void addAt(int r, int c, int value) {
        validateBounds(r, c);

        matrix[r-1][c-1] += value;
    }

    public void add(BasicMatrix second) throws MatrixException {
        if(!haveSameDimensions(second))
            throw new MatrixException("Matrices must be the same size");

        if(!isFull() || !second.isFull())
            throw new MatrixException("At least one of the matrices is not full");

        for(int a=1; a<=rows; a++) {
            for(int i=1; i<=cols; i++) {
                addAt(a, i, second.getVal(a, i));
            }
        }
    }

    public boolean equals(BasicMatrix second) {
        if(!haveSameDimensions(second))
            return false;

        for(int a=0; a<rows; a++) {
            for(int i=0; i<cols; i++) {
                if(!getVal(a, i).equals(second.getVal(a, i)))
                    return false;
            }
        }

        return true;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    private boolean haveSameDimensions(BasicMatrix second) {
        if(second.getRows() != rows || second.getCols() != cols)
            return false;
        return true;
    }

    private void validateBounds(int r, int c) throws IndexOutOfBoundsException {
        if(r < 1 || r > rows)
            throw new IndexOutOfBoundsException("Row " + r + " is out of bounds!");
        if(c < 1 || c > cols)
            throw new IndexOutOfBoundsException("Col " + c + " is out of bounds!");
    }

    public boolean isFull() {
        for(int a=0; a<rows; a++) {
            for(int i=0; i<cols; i++) {
                if(matrix[a][i] == null)
                    return false;
            }
        }

        return true;
    }

    public boolean isSquare() {
        return (rows == cols);
    }

    public boolean isUpperTriangular() {
        for(int a=0; a<rows; a++) {
            for(int i=0; i<cols; i++) {
                if(a > i && matrix[a][i] != 0)
                    return false;
            }
        }

        return true;
    }

    public int[][] toArray() {
        int[][] res = new int[rows][cols];
        for(int a=0; a<rows; a++) {
            for (int i = 0; i < cols; i++) {
                res[a][i] = matrix[a][i];
            }
        }

        return res;
    }

    // TODO: return formatted string (with aligned cols)
    public String toString() {
        String result = "\n";

        for(int a=0; a<rows; a++) {
            result += "|";
            for(int i=0; i<cols; i++) {
                result += matrix[a][i];
                if(i < cols-1)
                    result += " ";
            }

            result += "|";
            if(a < rows-1)
                result += "\n";
        }

        return result;
    }
}
