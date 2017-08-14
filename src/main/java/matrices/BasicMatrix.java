package matrices;

/**
 * Created by kiril on 15.05.2017.
 */

// TODO: Refactor this class to work with 'Arithmetic Expression' instead of 'Double' class.
public abstract class BasicMatrix {
    private Double[][] matrix;
    private int augmentInd;
    private int rows;
    private int cols;

    BasicMatrix(int rows, int cols) throws MatrixException {
        if(rows < 1 || cols < 1)
            throw new MatrixException("Unable to set size to r=" + rows + " c=" + cols + ". Minimum matrix size is '1x1'");

        this.rows = rows;
        this.cols = cols;
        augmentInd = -1;

        matrix = new Double[rows][cols];
        for(int a=0; a<rows; a++) {
            for(int i=0; i<cols; i++) {
                matrix[a][i] = null;
            }
        }
    }

    public void set(double[][] val) throws MatrixException {
        int r = val.length;
        int c = val[0].length;

        if(r != rows || c != cols)
            throw new MatrixException("Array has wrong dimensions");

        for(double[] a : val) {
            for(double v : a) {
                setNextElement(v);
            }
        }
    }

    public void set(double[] val) throws MatrixException {
        int r = val.length;

        if(r != rows * cols)
            throw new MatrixException("Array has wrong length");

        for(double v : val)
            setNextElement(v);
    }

    public void setNextElement(Double val) {
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

    public Double getVal(int r, int c) {
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
    private double[] performMultiplication(BasicMatrix second) throws MatrixException {
        if(cols != second.getRows())
            throw new MatrixException("Matrices can not be multiplied");

        if(!isFull() || !second.isFull())
            throw new MatrixException("At least one of the matrices is not full");

        int resultRows = rows;
        int resultCols = second.getCols();

        double[] newArr = new double[resultRows * resultCols];
        int ind = 0;

        for(int a=0; a<rows; a++) {
            for(int i=0; i<resultCols; i++) {
                Double[] r = getRow(a);
                Double[] c = second.getCol(i);

                if(r.length != c.length)
                    throw new MatrixException("Multiplication failed, invalid col and row lengths");

                double newEntire = 0;
                for(int x=0; x<r.length; x++) {
                    newEntire += r[x] * c[x];
                }

                newArr[ind] = newEntire;
                ind++;
            }
        }

        return newArr;
    }

    public void multiply(BasicMatrix second) {
        double[] newArr = performMultiplication(second);

        cols = second.getCols();
        matrix = new Double[rows][cols];

        int ind = 0;
        for(int a=0; a<rows; a++) {
            for (int i = 0; i <cols; i++) {
                matrix[a][i] = newArr[ind];
                ind++;
            }
        }
    }

    public void scalarMultiplication(int scalar) {
        for(int a=0; a<rows; a++) {
            multiplyRow(a+1, scalar);
        }
    }

    /**
     * Transposes current matrix (Columns become rows and rows become columns)
     */
    public void transpose() {
        Double[] arr = new Double[rows * cols];
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
        matrix = new Double[rows][cols];

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

    public boolean isInverse(BasicMatrix second) {
        if(!haveSameDimensions(second))
            return false;

        double[] multRes = performMultiplication(second);

        for(int i=0; i<multRes.length; i++)
            if(multRes[i] != 1)
                return false;

        return true;
    }

    public void augment(BasicMatrix second) {
        if(rows != second.getRows())
            throw new MatrixException("Matrices must have the same amount of rows be augmented.");

        Double[][] newArr = new Double[rows][cols+second.getCols()];

        // Copy our elements first
        for(int a=0; a<rows; a++) {
            for(int i=0; i<cols; i++) {
                newArr[a][i] = matrix[a][i];
            }
        }

        augmentInd = cols;

        // Add other elements
        for(int a=0; a<second.getRows(); a++) {
            for(int i=cols; i<cols+second.getCols(); i++) {
                newArr[a][i] = second.getVal(a+1, i-cols+1);
            }
        }

        cols = cols+second.getCols();
        matrix = newArr;
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
    private Double[] getRow(int r) {
        Double[] row = new Double[cols];

        for(int i=0; i<cols; i++)
            row[i] = matrix[r][i];

        return row;
    }

    /**
     * @param c 2D Array column number, '0' based number.
     * @return 1D Array containing specified column
     */
    private Double[] getCol(int c) {
        Double[] col = new Double[rows];

        for (int a=0; a<rows; a++)
            col[a] = matrix[a][c];

        return col;
    }

    private void addAt(int r, int c, double value) {
        validateBounds(r, c);

        matrix[r-1][c-1] += value;
    }

    /**
     * Switches specified rows of the matrix
     * @param r1 First row id, '1' based
     * @param r2 Second row id, '1' based
     */
    public void switchRows(int r1, int r2) throws MatrixException {
        if(rows < 2)
            throw new MatrixException("Not enough rows");

        if(r1 < 1 ||  r1 > rows)
            throw new MatrixException("Invalid first row index: " + r1);
        if(r2 < 1 || r2 > rows)
            throw new MatrixException("Invalid second row index: " + r2);

        if(r1 == r2)
            return;

        Double[] row1 = getRow(r1-1);
        Double[] row2 = getRow(r2-1);

        for(int i=0; i<cols; i++) {
            matrix[r1-1][i] = row2[i];
            matrix[r2-1][i] = row1[i];
        }
    }

    /**
     * Multiplies selected row by specified scalar
     * @param r Row number, '1' based
     * @param scalar Scalar
     * @throws IndexOutOfBoundsException Thrown when 'r' is out of bounds
     */
    public void multiplyRow(int r, double scalar) throws IndexOutOfBoundsException {
        if(r < 1 || r > rows)
            throw new IndexOutOfBoundsException("Row number " + r + " is out of bounds");

        for(int i=0; i<cols; i++)
            matrix[r-1][i] *= scalar;
    }
    
    public void toEchelon() {
        if(!isFull())
            throw new MatrixException("Matrix not Full");

        for(int a=0; a<rows; a++) {
            double iVal = matrix[a][a];

            double dMult = 1 / iVal;
            multiplyRow(a+1, dMult);

            // TODO: Do row subtraction...
            for(int i=a+1; i<cols; i++) {

            }
        }
    }

    public void toReducedEchelon() {

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

    double[][] toArray() {
        double[][] res = new double[rows][cols];
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
                if(i == augmentInd)
                    result.append("= ");

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
