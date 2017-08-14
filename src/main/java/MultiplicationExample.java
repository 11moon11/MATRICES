import matrices.*;

/**
 * Created by kiril on 16.05.2017.
 */
public class MultiplicationExample {
    public static void run() {
        System.out.println("------------------------------------");
        System.out.println("Matrices multiplication example:");

        double[] arr1 = {1, 2, 3, 4};
        double[] arr2 = {1, 0, 6, 7};
        double[] arr3 = {1, 2, 3};

        BasicMatrix mat1 = new Matrix(2, 2);
        BasicMatrix mat2 = new Matrix(2, 2);
        mat1.set(arr1);
        mat2.set(arr2);

        // Scalar Multiplication
        mat1.scalarMultiplication(2);

        System.out.println("Matrix 1: " + mat1.toString());
        System.out.println("x");
        System.out.println("Matrix 2: " + mat2.toString());

        mat1.multiply(mat2);

        System.out.println("=" + mat1.toString());

        BasicMatrix mat3 = new Vector(arr3);
        BasicMatrix mat4 = new Vector(arr3);
        mat4.transpose();
        System.out.println("Vector 1: " + mat3.toString());
        System.out.println("x");
        System.out.println("Vector 2: " + mat4.toString());


        mat3.multiply(mat4);
        System.out.println("=" + mat3.toString());
    }
}
