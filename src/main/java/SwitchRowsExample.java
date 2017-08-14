import matrices.*;

/**
 * Created by kiril on 16.05.2017.
 */
public class SwitchRowsExample {
    public static void run() {
        System.out.println("------------------------------------");
        System.out.println("Switching Rows Example: ");

        double[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        BasicMatrix mat1 = new Matrix(3, 3);
        mat1.set(arr);

        System.out.println("Initial matrix: " + mat1.toString());

        mat1.switchRows(1, 3);

        System.out.println("Modified matrix: " + mat1.toString());
    }
}
