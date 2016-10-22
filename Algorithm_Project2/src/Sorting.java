import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Arrays;


/**
 * <h1>This is a class that creates an array and then sort the array by implementing
 * insertion sort (under 32 items), merge sort and quick sort if more items given.</h1>
 *
 * @author multiple authors
 */
public class Sorting {

    /** The actual array of integers. */
    private static int[] arr;
    /** A copy of the array of integers (arr). */
    private static int[] arrCopy;
    /** A sub-array while merging or dividing. */
    private static int[] mergeArr;
    /** It's a bufferedReader that will be storing running time. */
    private static BufferedReader read;
    /** It's a random number generator. */
    private static Random randomGenerator;
    /** Size of an array. */
    private static int size;
    /** Storing the generated random number. */
    private static int random;

    /**
     * <b>Description:</b> It prints all items in an array
     * @param msg a message from user
     *
     * @author unknown (most likely Prof. Zhang)
     */
    private static void printArray(String msg) {
        System.out.print(msg + " [" + arr[0]);
        for(int i=1; i<size; i++) System.out.print(", " + arr[i]);
        System.out.println("]");
    }

    /**
     * <b>Description:</b> It swaps two items
     * @param i an item from array
     * @param j another item from array
     *
     * @author unknown (most likely Prof. Zhang)
     */
    public static void exchangeItems(int i, int j){
        int t=arr[i];
        arr[i]=arr[j];
        arr[j]=t;
    }

    /**
     * <b>Description:</b> It runs insertion sort of a sub-array by
     * having its start and end place.
     *
     * @param left starting point of one subarray/array
     * @param right end of an array
     */
    public static void insertSort(int left, int right) {
        // insertSort the sub-array arr[left, right]
        int i, j;

        for(i=left+1; i<=right; i++) {
            int temp = arr[i];           // store a[i] in temp
            j = i;                       // start shifts at i
            // until one is smaller,
            while(j>left && arr[j-1] >= temp) {
                arr[j] = arr[j-1];        // shift item to right
                --j;                      // go left one position
            }
            arr[j] = temp;              // insert stored item
        }  // end for
    }  // end insertSort()

    /**
     * <b>Description:</b> It calls the insertSort method according
     * to the size of the array from this.size
     */
    public static void insertionSort() {
        insertSort(0, size-1);
    } // end insertionSort()


    /**
     * <b>Description:</b> This adjusts subtree to rotate the MAX to the root.
     * @param i the left and right subtrees of node i are max heaps.
     * @param n make the tree rooted at node i as max heap of n nodes.
     */
    public static void maxHeapify(int i, int n) {
        // Pre: the left and right subtrees of node i are max heaps.
        // Post: make the tree rooted at node i as max heap of n nodes.
        int max = i;
        int left=2*i+1;
        int right=2*i+2;

        if(left < n && arr[left] > arr[max]) max = left;
        if(right < n && arr[right] > arr[max]) max = right;

        if (max != i) {  // node i is not maximal
            exchangeItems(i, max);
            maxHeapify(max, n);
        }
    }

    /**
     * <b>Description:</b> This method practices heapsort.
     */
    public static void heapsort(){
        // Build an in-place bottom up max heap
        for (int i=size/2; i>=0; i--) maxHeapify(i, size);

        for(int i=size-1;i>0;i--) {
            exchangeItems(0, i);       // move max from heap to position i.
            maxHeapify(0, i);     // adjust heap
        }
    }

    /**
     * <b>Description:</b> This method sorts an array using merge sort.
     * @param low   starting place in a (sub)array
     * @param high  ending place in a (sub)array
     */
    private static void mergeSort(int low, int high) {
        // sort arr[low, high-1]
        // Check if low is smaller than high, if not then the array is sorted
        if (low < high-1) {
            // Get the index of the element which is in the middle
            int middle = (high + low) / 2;
            // Sort the left side of the array
            mergeSort(low, middle);
            // Sort the right side of the array
            mergeSort(middle, high);
            // Combine them both
            merge(low, middle, high);
        }
    }

    /**
     * <b>Description:</b> This method merges two array in order.
     * From merge arr[low, middle-1] and arr[middle, high-1] into arr[low, high-1].
     * @param low       as the beginning point in the first sub-array
     * @param middle    the ending point of the first sub-array and
     *                  the beginning point in the second sub-array
     * @param high      the ending point of the second sub-array
     */
    private static void merge(int low, int middle, int high) {
        // merge arr[low, middle-1] and arr[middle, high-1] into arr[low, high-1]

        // Copy first part into the arrCopy array
        for (int i = low; i < middle; i++) mergeArr[i] = arr[i];

        int i = low;
        int j = middle;
        int k = low;

        // Copy the smallest values from either the left or the right side back        // to the original array
        while (i < middle && j < high)
            if (mergeArr[i] <= arr[j])
                arr[k++] = mergeArr[i++];
            else
                arr[k++] = arr[j++];

        // Copy the rest of the left part of the array into the original array
        while (i < middle) arr[k++] = mergeArr[i++];
    }

    /**
     * <b>Description:</b> Natural merge sort sorts an array by splitting
     * an array to several smaller arrays using a
     * fixed length.
     */
    public static void naturalMergeSort() {
        int run[], i, j, s, t, m;

        run = new int[size/2];

        // Step 1: identify runs from the input array arr[]
        i = m = 1;
        run[0] = 0;
        while (i < size) {
            if (arr[i-1] > arr[i])
                if (run[m-1]+1 == i) {     // make sure each run has at least two

                    j = i+1;
                    s = 0;
                    while (j < size && arr[j-1] >= arr[j]) j++;     // not stable

                    // reverse arr[i-1, j-1];
                    s = i - 1;
                    t = j - 1;
                    while (s < t) exchangeItems(s++, t--);

                    i = j;
                } else
                    run[m++] = i++;
            else i++;
        }

        // Step 2: merge runs bottom-up into one run                                                                       
        t = 1;
        while (t < m) {
            s = t;
            t = s<<1;
            i = 0;
            while (i+t < m) {
                merge(run[i], run[i+s], run[i+t]);
                i += t;
            }
            if (i+s < m) merge(run[i], run[i+s], size);
        }

    }

    /**
     * <b>Description:</b> This method uses quick sort.
     * It sets a pivot to a random number then put
     * all numbers greater than the pivot to one side,
     * and other numbers less than the pivot to the other.
     * @param low   starting point in an array
     * @param high  ending point in an array
     */
    private static void quickSort(int low, int high) {
        int i = low, j = high;

        // Get the pivot element from the middle of the list
        int pivot = arr[(high+low)/2];

        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller then the pivot
            // element then get the next element from the left list
            while (arr[i] < pivot) i++;

            // If the current value from the right list is larger then the pivot
            // element then get the next element from the right list
            while (arr[j] > pivot) j--;

            // If we have found a value in the left list which is larger than
            // the pivot element and if we have found a value in the right list
            // which is smaller then the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i < j) {
                exchangeItems(i, j);
                i++;
                j--;
            } else if (i == j) { i++; j--; }
        }

        // Recursion
        if (low < j)
            quickSort(low, j);
        if (i < high)
            quickSort(i, high);
    }

    /**
     * <b>Description:</b>This method is a demo program. It asks user the number
     * of wanted elements, then sort them in heap, merge, natural merge,
     * and quick sort.
     * @param input This is a string of whatever it has in the main.
     *              Now, it's set to "random", and it could be changed
     *              in main.
     */
    public static void demo1 (String input) {
        // demonstration of sorting algorithms on random input

        long start, finish;
        System.out.println();

        // Heap sort      
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        heapsort();
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("heap sort on " + input + " input: " + (finish-start) + " milliseconds.");

        // Merge sort
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        mergeSort(0, size);
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("merge sort on " + input + " input: " + (finish-start) + " milliseconds.");

        // Natural Merge sort
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        naturalMergeSort();
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("natural merge sort on " + input + " input: " + (finish-start) + " milliseconds.");

        // Quick sort
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        quickSort(0, size-1);
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("quick sort on " + input + " input: " + (finish-start) + " milliseconds.");
    }

    /**
     * <b>Description:</b> This method utilizes heap, merge, natural merge, and quick sort
     * to sort a nearly sorted array. (It tries to show that insertion
     * sort is faster when it comes to a nearly sorted array)
     * @param input a descriptor for the array
     */

    public static void demo2 (String input) {
        // demonstration of sorting algorithms on nearly sorted input

        long start, finish;

        demo1(input);

        // Insert sort on nearly-sorted array      
        for(int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        insertionSort();
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("insert sort on " + input + " input: " + (finish-start) + " milliseconds.");
    }

    public static void main(String[] args) {

        read = new BufferedReader(new InputStreamReader(System.in));

        randomGenerator = new Random();

        try {
            System.out.print("Please enter the array size : ");
            size = Integer.parseInt(read.readLine());
        } catch(Exception ex){
            ex.printStackTrace();
        }

        // create array
        arr = new int[size];
        arrCopy = new int[size];
        mergeArr = new int[size];

        // fill array
        random = size*10;
        for(int i=0; i<size; i++)
            arr[i] = arrCopy[i] = randomGenerator.nextInt(random);
        demo1("random");

        // arr[0..size-1] is already sorted. We randomly swap 100 pairs to make it nearly-sorted.
        for (int i = 0; i < 100; i++) {
            int j  = randomGenerator.nextInt(size);
            int k  = randomGenerator.nextInt(size);
            exchangeItems(j, k);
        }
        for(int i=0; i<size; i++) arrCopy[i] = arr[i];
        demo2("nearly sorted");

        for(int i=0; i<size; i++) arrCopy[i] = size-i;
        demo1("reversely sorted");
    }
}