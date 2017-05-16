package matrices;

/**
 * Created by kiril on 16.05.2017.
 */
public class Zero extends BasicMatrix {
    public Zero(int size) {
        super(size, size);

        int arr[] = new int[size*size];
        for(int i=0; i<size*size; i++)
            arr[i] = 0;

        set(arr);
    }
}
