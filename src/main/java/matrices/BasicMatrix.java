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

        for(int[] a : val) {
            for(int v : a) {
                setNextElement(v);
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

    // TODO: optimize for diagonal matrices
    public void multiply(BasicMatrix second) throws MatrixException {
        if(cols != second.getRows())
            throw new MatrixException("Matrices can not be multiplied");

        if(!isFull() || !second.isFull())
            throw new MatrixException("At least one of the matrices is not full");

        int resultRows = rows;
        int resultCols = second.getCols();
        int[] newArr = new int[resultRows * resultCols];
        int ind = 0;

        for(int a=0; a<rows; a++) {
            for(int i=0; i<resultCols; i++) {
                Integer[] r = getRow(a);
                Integer[] c = second.getCol(i);

                if(r.length != c.length)
                    throw new MatrixException("Multiplication failed, invalid col and row lengths");

                int newEntire = 0;
                for(int x=0; x<r.length; x++) {
                    newEntire += r[x] * c[x];
                }

                newArr[ind] = newEntire;
                ind++;
            }
        }

        rows = resultRows;
        cols = resultCols;
        matrix = new Integer[rows][cols];

        ind = 0;
        for(int a=0; a<resultRows; a++) {
            for (int i = 0; i < resultCols; i++) {
                matrix[a][i] = newArr[ind];
                ind++;
            }
        }
    }

    public void scalarMultiplication(int scalar) {
        for(int a=0; a<rows; a++) {
            for(int i=0; i<cols; i++) {
                matrix[a][i] *= scalar;
            }
        }
    }

    /**
     * Transposes current matrix (Columns become rows and rows become columns)
     */
    public void transpose() {
        Integer[] arr = new Integer[rows * cols];
        int ind=0;

        // Copy old values
        for (int a=0; a<rows; a++) {
            for(int i=0; i<cols; i++) {
                arr[ind] = matrix[a][i];
                ind++;
            }
        }

        // Switch rows and cols
        int tmp = rows;
        rows = cols;
        cols = tmp;
        // Reinitialize array
        matrix = new Integer[rows][cols];

        ind = 0;

        // Assign old values to a new array
        for (int a=0; a<rows; a++) {
            for(int i=0; i<cols; i++) {
                matrix[a][i] = arr[ind];
                ind++;
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

    int getRows() {
        return rows;
    }

    int getCols() {
        return cols;
    }

    /**
     * @param r 2D Array row number, '0' based number.
     * @return 1D Array containing specified row
     */
    private Integer[] getRow(int r) {
        return matrix[r];
    }

    /**
     * @param c 2D Array column number, '0' based number.
     * @return 1D Array containing specified column
     */
    private Integer[] getCol(int c) {
        Integer[] col = new Integer[rows];
        for (int a=0; a<rows; a++)
            col[a] = matrix[a][c];

        return col;
    }

    private void addAt(int r, int c, int value) {
        validateBounds(r, c);

        matrix[r-1][c-1] += value;
    }





    private boolean haveSameDimensions(BasicMatrix second) {
        return !(second.getRows() != rows || second.getCols() != cols);
    }

    private void validateBounds(int r, int c) throws IndexOutOfBoundsException {
        if(r < 1 || r > rows)
            throw new IndexOutOfBoundsException("Row " + r + " is out of bounds!");
        if(c < 1 || c > cols)
            throw new IndexOutOfBoundsException("Col " + c + " is out of bounds!");
    }

    private boolean isFull() {
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

    public boolean isDiagonal() {
        if(!isSquare())
            return false;

        for(int a=0; a<rows; a++) {
            for (int i = 0; i < cols; i++) {
                if(a == i)
                    if(matrix[a][i] == 0) return false;
                else
                    if(matrix[a][i] != 0) return false;
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
        StringBuilder result = new StringBuilder("\n");

        for(int a=0; a<rows; a++) {
            result.append("|");
            for(int i=0; i<cols; i++) {
                result.append(matrix[a][i]);
                if(i < cols-1)
                    result.append(" ");
            }

            result.append("|");
            if(a < rows-1)
                result.append("\n");
        }

        return result.toString();
    }
}
