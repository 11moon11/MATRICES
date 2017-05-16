import matrices.BasicMatrix;
import matrices.Matrix;

/**
 * Created by kiril on 16.05.2017.
 */
public class GetInfoExample {
    public static void run() {
        System.out.println("------------------------------------");
        System.out.println("Get Matrix Information Example");
        int[][] arr = {
                {1, 2, 3, 4},
                {1, 2, 0, 4},
                {1, 2, 0, 0},
                {0, 2, 0, 4},
                {0, 2, 0, 0},
                {0, 0, 0, 0}
            };

        int cnt = 1;
        for(int[] a : arr) {
            BasicMatrix mat = new Matrix(2,2);
            mat.set(a);
            System.out.println("Matrix " + cnt + ": " + mat.toString());
            if(mat.isUpperTriangular())
                System.out.println("Is Upper Triangular");
            else
                System.out.println("Is not Upper Triangular");
            System.out.println();
        }
    }
}
