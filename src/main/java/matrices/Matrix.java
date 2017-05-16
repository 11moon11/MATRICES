package matrices;

/**
 * Created by kiril on 15.05.2017.
 */

// TODO: Wrap it all in a Matrix Factory
public class Matrix extends BasicMatrix {
    public Matrix(int rows, int cols) {
        super(rows, cols);
    }

    public Matrix(int[][]val) {
        super(val.length, val[0].length);
        set(val);
    }

    public Matrix(Matrix mtx) {
        super(mtx.getRows(), mtx.getCols());
        set(mtx.toArray());
    }

    public BasicMatrix _add(BasicMatrix second) {
        BasicMatrix result = new Matrix(this);
        result.add(second);

        return result;
    }
}
