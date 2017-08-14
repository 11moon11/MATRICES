package matrices;

/**
 * Created by kiril on 16.05.2017.
 */

// TODO: Wrap it all in a Matrix Factory
public class Vector extends BasicMatrix {
    public Vector(int rows, int cols) {
        super(rows, cols);

        checkDimensions(rows, cols);
    }

    public Vector(double[] val) {
        super(1, val.length);
        set(val);

        checkDimensions(1, val.length);
    }

    private void checkDimensions(int r, int c) {
        if(r != 1 && c != 1)
            throw new MatrixException("Number of rows and/or cols must be 1 in vector");
    }
}
