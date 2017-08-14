package matrices;

/**
 * Created by kiril on 15.05.2017.
 */

// TODO: Wrap it all in a Matrix Factory
public class Matrix extends BasicMatrix {
    public Matrix(int rows, int cols) {
        super(rows, cols);
    }

    public Matrix(double[][]val) {
        super(val.length, val[0].length);
        set(val);
    }

    public Matrix(Matrix mtx) {
        super(mtx.getRows(), mtx.getCols());
        set(mtx.toArray());
    }

    /**
     * Returns new matrix as a result of the sum
     * @param second Matrix that will be added
     * @return return new BasicMatrix
     */
    public BasicMatrix _add(BasicMatrix second) {
        BasicMatrix result = new Matrix(this);
        result.add(second);

        return result;
    }
}
