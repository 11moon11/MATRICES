import matrices.*;

public class AddExample {
    public static void run() {
        System.out.println("------------------------------------");
        System.out.println("Adding 2 matrices together example: ");
        int[][] arr1 =
                {
                        {1, 2},
                        {3, 4}
                };
        int[][] arr2 =
                {
                        {4, 3},
                        {2, 1}
                };

        BasicMatrix mat1 = new Matrix(arr1);
        BasicMatrix mat2 = new Matrix(arr2);

        System.out.println("Matrix one: " + mat1.toString());
        System.out.println("Matrix two: " + mat2.toString());

        // '_add'   - return new Matrix
        // 'add'    - updates mat1
        mat1.add(mat2);

        System.out.println("Matrix one + two: " + mat1.toString());
    }
}
