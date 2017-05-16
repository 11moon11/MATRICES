import matrices.*;

/**
 * Created by kiril on 16.05.2017.
 */
public class InitExample {
    public static void run() {
        System.out.println("------------------------------------");
        System.out.println("How to init matrix example: ");

        // Method one - with 2d array
        int[][] arr1 =
                {
                        {1, 2},
                        {3, 4}
                };

        BasicMatrix mat1 = new Matrix(2, 2);
        mat1.set(arr1);
        BasicMatrix mat2 = new Matrix(arr1);

        System.out.println("Initialized matrix 1:" + mat1.toString());
        System.out.println("Initialized matrix 2:" + mat2.toString());

        // Method two - with 1d array
        int[] arr2 = {1, 2, 3, 4};
        int[] arr3 = {1, 2, 3, 4, 5, 6};

        BasicMatrix mat3 = new Vector(arr2);
        BasicMatrix mat6 = new Matrix(2, 3);
        BasicMatrix mat4 = new Matrix(2, 2);
        mat4.set(arr2);
        mat6.set(arr3);

        System.out.println("Initialized matrix 3:" + mat3.toString());
        System.out.println("Initialized matrix 4:" + mat4.toString());

        // Method three - element by element
        BasicMatrix mat5 = new Matrix(2, 2);
        for(int i : arr2) {
            mat5.setNextElement(i);
        }
        System.out.println("Initialized matrix 5:" + mat5.toString());
        System.out.println("Initialized matrix 6:" + mat6.toString());

        // Identity matrix example:
        BasicMatrix mat7 = new Identity(3);
        System.out.println("Initialized matrix 7:" + mat7.toString());
    }
}
