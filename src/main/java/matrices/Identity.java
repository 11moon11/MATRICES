package matrices;

/**
 * Created by kiril on 16.05.2017.
 */
public class Identity extends BasicMatrix {
    public Identity(int size) {
        super(size, size);

        int arr[] = new int[size*size];
        for(int i=0; i<size*size; i++)
            arr[i] = 0;
        for(int i=0; i<size; i++)
            arr[i*(size)+i] = 1;

        set(arr);
    }

}
