import java.io.BufferedReader;
import java.util.Random;

/**
 * <h1>This is a class that creates an array and then sort the array by implementing
 * insertion sort (under 32 items), merge sort and quick sort if more items given.</h1>
 *
 * @author multiple authors
 */
public class Sorting {
    /**
     * The actual array of integers.
     */
    private static int[] arr;
    /**
     * A copy of the array of integers (arr).
     */
    private static int[] arrCopy;
    /**
     * A sub-array while merging or dividing.
     */
    private static int[] mergeArr;
    /**
     * It's a bufferedReader that will be storing running time.
     */
    private static BufferedReader read;
    /**
     * It's a random number generator.
     */
    private static Random randomGenerator;
    /**
     * Size of an array.
     */
    private static int size;
    /**
     * Storing the generated random number.
     */
    private static int random;

    /**
     * <b>Description:</b> It prints all items in an array
     *
     * @param msg a message from user
     */
    private static void printArray(String msg) {
        System.out.print(msg + " [" + arr[0]);
        for (int i = 1; i < size; i++) System.out.print(", " + arr[i]);
        System.out.println("]");
    }

    /**
     * <b>Description:</b> It swaps two items
     *
     * @param i an item from array
     * @param j another item from array
     */
    private static void exchangeItems(int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    private static void exchangeItemsArrCopy(int i, int j) {
        int t = arrCopy[i];
        arrCopy[i] = arrCopy[j];
        arrCopy[j] = t;
    }

    /**
     * <b>Description:</b> This method will check if an array is already sorted,
     * in order to "save time".
     *
     * @param low  the start of an (sub)array
     * @param high the end of an (sub)array
     * @return the bool will indicate if the array is sorted
     */
    private static boolean isSorted(int low, int high) {
        /*TODO: please write a method to check if a sub-array is sorted*/
        for (int i = low; i < high; i++) {
            if (arr[i] > arr[i + 1]) return false; // It is proven that the array is not sorted.
        }
        return true; // If this part has been reached, the array must be sorted.
    }

    /**
     * <b>Description:</b> It runs insertion sort of a sub-array by having its start and end place.
     *
     * @param left  starting point of one subarray/array
     * @param right end of an array
     */
    private static void insertSort(int left, int right) {
        // insertSort the sub-array arr[left, right]
        int i, j;
        for (i = left + 1; i <= right; i++) {
            int temp = arr[i];           // store a[i] in temp
            j = i;                       // start shifts at i
            // until one is smaller,
            while (j > left && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];        // shift item to right
                --j;                      // go left one position
            }
            arr[j] = temp;              // insert stored item
        }  // end for
    }  // end insertSort()

    /**
     * <b>Description:</b> It calls the insertSort method according
     * to the size of the array from this.size
     */
    private static void insertionSort() {
        insertSort(0, size - 1);
    } // end insertionSort()

    /**
     * <b>Description:</b> This adjusts subtree to rotate the MAX to the root.
     *
     * @param i the left and right subtrees of node i are max heaps.
     * @param n make the tree rooted at node i as max heap of n nodes.
     */
    private static void maxHeapify(int i, int n) {
        // Pre: the left and right subtrees of node i are max heaps.
        // Post: make the tree rooted at node i as max heap of n nodes.
        int max = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && arr[left] > arr[max]) max = left;
        if (right < n && arr[right] > arr[max]) max = right;
        if (max != i) {  // node i is not maximal
            exchangeItems(i, max);
            maxHeapify(max, n);
        }
    }

    /**
     * <b>Description:</b> This method practices heapsort.
     */
    private static void heapsort() {
        // Build an in-place bottom up max heap
        for (int i = size / 2; i >= 0; i--) maxHeapify(i, size);
        for (int i = size - 1; i > 0; i--) {
            exchangeItems(0, i);       // move max from heap to position i.
            maxHeapify(0, i);     // adjust heap
        }
    }

    /**
     * <b>Description:</b> This method sorts an array using merge sort.
     *
     * @param low  starting place in a (sub)array
     * @param high ending place in a (sub)array
     */
    private static void mergeSort(int low, int high) {
        // sort arr[low, high-1]
        // Check if low is smaller than high, if not then the array is sorted
        if (low < high - 1) {
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
     * <b>Description:</b> This method sorts an array using merge sort, but
     * when the sub-arrays have less than 32 elements, it switches to insertion.
     *
     * @param low  starting place in a (sub)array
     * @param high ending place in a (sub)array
     */
    private static void mergeSort2(int low, int high) {
        /* TODO: please implement a method that runs as the descriptin above. */
        if (high - low >= 31) {
            int middlePoint = (high + low) / 2;
            mergeSort2(low, middlePoint);
            mergeSort2(middlePoint, high);
            merge(low, middlePoint, high);
        } else insertSort(low, high - 1);
    }

    /**
     * <b>Description:</b> This method merges two array in order.
     * From merge arr[low, middle-1] and arr[middle, high-1] into arr[low, high-1].
     *
     * @param low    as the beginning point in the first sub-array
     * @param middle the ending point of the first sub-array and
     *               the beginning point in the second sub-array
     * @param high   the ending point of the second sub-array
     */
    private static void merge(int low, int middle, int high) {
        // merge arr[low, middle-1] and arr[middle, high-1] into arr[low, high-1]
        // Copy first part into the arrCopy array
        /*for (int i = low; i < middle; i++) mergeArr[i] = arr[i];*/
        System.arraycopy(arr, low, mergeArr, low, (middle - low));
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
    private static void naturalMergeSort() {
        int run[], i, j, s, t, m;
        run = new int[size / 2];
        // Step 1: identify runs from the input array arr[]
        i = m = 1;
        run[0] = 0;
        while (i < size) {
            if (arr[i - 1] > arr[i])
                if (run[m - 1] + 1 == i) {     // make sure each run has at least two
                    j = i + 1;
                    s = 0;
                    while (j < size && arr[j - 1] >= arr[j]) j++;     // not stable
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
            t = s << 1;
            i = 0;
            while (i + t < m) {
                merge(run[i], run[i + s], run[i + t]);
                i += t;
            }
            if (i + s < m) merge(run[i], run[i + s], size);
        }
    }

    /**
     * <b>Description:</b> This method uses quick sort.
     * It sets a pivot to a random number then put
     * all numbers greater than the pivot to one side,
     * and other numbers less than the pivot to the other.
     *
     * @param low  starting point in an array
     * @param high ending point in an array
     */
    private static void quickSort(int low, int high) {
        int i = low, j = high;
        // Get the pivot element from the middle of the list
        int pivot = arr[(high + low) / 2];
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
            } else if (i == j) {
                i++;
                j--;
            }
        }
        // Recursion
        if (low < j) quickSort(low, j);
        if (i < high) quickSort(i, high);
    }


    /**
     * <b>Description:</b> This method runs the quick sort
     * first and then when the sub-array has less than 32
     * elements, it runs in insertion sort. It picks the
     * pivot depending on which method it's using.
     *
     * @param low  starting point in an array
     * @param high ending point in an array
     * @param pivotSelectionMethod 2 for the method itself,
     *                             4 for quickSort4,
     *                             5 for quickSort5.
     */
    private static void quickSort2(int low, int high, int pivotSelectionMethod) {
        /*TODO: this quick sort should be implemented as description above*/
        /*recursive solution*/
        int i = low, j = high;
        int pivot = 0;

        if(high - low >= 3) {
            switch (pivotSelectionMethod) {
                case 2:
                    pivot = arr[(high + low) / 2]; // it picks one, and that's it
                    break;
                case 4:
                    if ((high - low) >= 3) {
                        pivot = quickSort4(low, high); // it picks 3 pivots and choose the median
                    } else pivot = arr[(high + low) / 2];
                    break;
                case 5:
                    if((high - low) >= 9) {
                        pivot = quickSort5(low, high); // it picks 9 pivots and choose the median
                    } else pivot = arr[(high + low) / 2];
                    break;
                default: // by default, it picks one, and that's it
                    pivot = arr[(high + low) / 2];
                    break;
            }
        }

        while (i <= j) {
            while (arr[j] > pivot) j--;
            while (arr[i] < pivot) i++;
            if (i == j) { // notice the order here is slightly different from quickSort's to avoid IDE warnings
                j--;
                i++;
            } else if (i < j) {
                exchangeItems(i, j);
                j--;
                i++;
            }
        }
        // Recursion
        if (low < j) {
            if (j - low >= 32) quickSort2(low, j, pivotSelectionMethod);
            else insertSort(low, j); //uses insertion sort when sub-array has less than 32 items
        }
        if (i < high) {
            if (high - i >= 32) quickSort2(i, high, pivotSelectionMethod);
            else insertSort(i, high); //uses insertion sort when sub-array has less than 32 items
        }
    }

    /**
     * <b>Description:</b> This quick sort checks if the sub-array is sorted every time
     * before it actually sorts that sub-array.
     *
     * @param low  the starting point of a sub-array
     * @param high the ending point of a sub-array.
     */
    private static void quickSort3(int low, int high) {
        /*TODO: this quick sort should be implemented as description above*/
        if (!isSorted(low, high)) {
            /*recursive solution*/
            int i = low, j = high;
            int pivot = arr[(high + low) / 2];
            while (i <= j) {
                while (arr[i] < pivot) i++;
                while (arr[j] > pivot) j--;
                if (i == j) {
                    i++;
                    j--;
                } else if (i < j) {
                    exchangeItems(i, j);
                    i++;
                    j--;
                }
            }
            // Recursion
            if (low < j) {
                if (j - low >= 32) quickSort3(low, j);
                else insertSort(low, j); //uses insertion sort when sub-array has less than 32 items
            }
            if (i < high) {
                if (high - i >= 32) quickSort3(i, high);
                else insertSort(i, high); //uses insertion sort when sub-array has less than 32 items
            }
        }
        /*easy way to test out if it's functioning*/
        //else System.out.format("It's sorted: %d : %d \n", low, high);
    }

    /**
     * <b>Requirement from Prof.:</b> One uses the median of three elements,
     * i.e., first, middle, and last, as pivot for partition.
     * @param low starting point of the to-be-sorted array
     * @param high end point of the sorting array
     * @return pivot value we want
     */
    private static int quickSort4(int low, int high) {
        /*TODO: this quick sort should be implemented as description above*/
        int[] pivot = new int[3];
        pivot[0] = arr[low] ;
        pivot[1] = arr[(high + low) /2];
        pivot[2] = arr[high];
        int i, j;
        for (i = 0; i <= 2; i++) {
            int temp = pivot[i];           // store a[i] in temp
            j = i;                       // start shifts at i
            // until one is smaller,
            while (j > 0 && pivot[j - 1] >= temp) {
                pivot[j] = pivot[j - 1];        // shift item to right
                --j;                      // go left one position
            }
            pivot[j] = temp;              // insert stored item
        }  // end for
        return pivot[1];
    }

    /**
     * <b>Words from the Prof.:</b> One first selects 9 elements equally spreading out in the array,
     * inclduing the first and the last element. Compute the median of the first three,
     * the median of the next three, and the median of the last three.
     * Then use the median of three medians as pivot.
     * @param low starting point of the to-be-sorted array
     * @param high end point of the sorting array
     * @return pivot value we want
     */
    private static int quickSort5 (int low, int high) {
        /*TODO: this quick sort should be implemented as description above*/
        int[] piv = new int[9];
        int divisible = (high - low + 1) - ((high - low + 1) % 9);
        int aPiece = divisible / 9;
        for (int i = 0; i < 8; i++) {
            piv[i] = arr[low + aPiece * i];
        }
        piv[8] = arr[high];
        int i, j;
        for (i = 0; i < 9; i++) {
            int temp = piv[i];           // store a[i] in temp
            j = i;                       // start shifts at i
            // until one is smaller,
            while (j > 0 && piv[j - 1] >= temp) {
                piv[j] = piv[j - 1];        // shift item to right
                --j;                      // go left one position
            }
            piv[j] = temp;              // insert stored item
        }  // end for
        return piv[4];
    }

    /**
     * <b>Description:</b>This method is a demo program. It asks user the number
     * of wanted elements, then sort them in heap, merge, natural merge,
     * and quick sort.
     *
     * @param input This is a string of whatever it has in the main.
     *              Now, it's set to "random", and it could be changed
     *              in main.
     */
    private static void demo1(String input) {
        // demonstration of sorting algorithms on random input
        long start, finish;
        System.out.println();
        // Heap sort
        System.arraycopy(arrCopy, 0, arr, 0, size);
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        heapsort();
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("heap sort on " + input + " input: " + (finish - start) + " milliseconds.");
        // Merge sort
        System.arraycopy(arrCopy, 0, arr, 0, size);
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        mergeSort(0, size);
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("merge sort on " + input + " input: " + (finish - start) + " milliseconds.");
        // Natural Merge sort
        System.arraycopy(arrCopy, 0, arr, 0, size);
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        naturalMergeSort();
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("natural merge sort on " + input + " input: " + (finish - start) + " milliseconds.");
        // Quick sort
        System.arraycopy(arrCopy, 0, arr, 0, size);
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        quickSort(0, size - 1);
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("quick sort on " + input + " input: " + (finish - start) + " milliseconds.");
    }

    /**
     * <b>Description:</b> This method utilizes heap, merge, natural merge, and quick sort
     * to sort a nearly sorted array. (It tries to show that insertion
     * sort is faster when it comes to a nearly sorted array)
     *
     * @param input a descriptor for the array
     */
    public static void demo2(String input) {
        // demonstration of sorting algorithms on nearly sorted input
        long start, finish;
        demo1(input);
        // Insert sort on nearly-sorted array
        System.arraycopy(arrCopy, 0, arr, 0, size);
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        insertionSort();
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("insert sort on " + input + " input: " + (finish - start) + " milliseconds.");
    }

    /**
     * <b>Description</b>This is a method that compares four different sorting algorithms
     * (mergeSort, mergeSort2, quickSort, quickSort2) by calculating the
     * time spent while generating and sorting 100 random arrays of size
     * 10,000,000.<br><br>
     * <b>Test run result:</b><br>
     * <i>merge sort</i> 106,764 ms.<br>
     * <i>merge sort II</i> 98,823 ms. (-8s  improvement)<br>
     * <i>quick sort</i> 81,978 ms.<br>
     * <i>quick sort II</i> 71,078 ms. (-11s improvement)<br><br>
     * <b>Conclusion:</b> when the size of an sub-array is less than 32, insertion sort
     * works better. However, it's not a significant improvement on running speed.
     */
    private static void task1(String input) {
        /*TODO: please implement this method follow the description above.*/
        long start = 0, finish = 0;
        final int REPEATTIMES = 100;
        System.out.println();
        long mergeRunningTimeSum = 0;
        long merge2RunningTimeSum = 0;
        long quickRunningTimeSum = 0;
        long quick2RunningTimeSum = 0;
        size = 10000000;
        System.out.println("======== Task 1 ========");
        System.out.println("Because it has 10,000,000 elements in 100 arrays,\n" +
                "it could take up to 7 minutes.");
        randomGenerator = new Random();
        for (int i = 0; i < REPEATTIMES; i++) {
            // create array
            arr = new int[size];
            arrCopy = new int[size];
            mergeArr = new int[size];
            // fill array
            random = size * 10;
            for (int j = 0; j < size; j++)
                arr[j] = arrCopy[j] = randomGenerator.nextInt(random);
            /*for (int j = 0; j < size; j++) arr[j] = arrCopy[j];*/
            // Merge sort
            System.arraycopy(arrCopy, 0, arr, 0, size);
            if (size < 101) printArray("in");
            start = System.currentTimeMillis();
            mergeSort(0, size);
            finish = System.currentTimeMillis();
            if (size < 101) printArray("out");
            mergeRunningTimeSum += (finish - start);
            // Merge sort 2
            System.arraycopy(arrCopy, 0, arr, 0, size);
            if (size < 101) printArray("in");
            start = System.currentTimeMillis();
            mergeSort2(0, size);
            finish = System.currentTimeMillis();
            if (size < 101) printArray("out");
            merge2RunningTimeSum += (finish - start);
            // Quick sort
            System.arraycopy(arrCopy, 0, arr, 0, size);
            if (size < 101) printArray("in");
            start = System.currentTimeMillis();
            quickSort(0, size - 1);
            finish = System.currentTimeMillis();
            if (size < 101) printArray("out");
            quickRunningTimeSum += (finish - start);
            // Quick sort 2
            System.arraycopy(arrCopy, 0, arr, 0, size);
            if (size < 101) printArray("in");
            start = System.currentTimeMillis();
            quickSort2(0, size - 1, 2); // 2 means the pivot is picked at the middle of the array
            finish = System.currentTimeMillis();
            if (size < 101) printArray("out");
            quick2RunningTimeSum += (finish - start);
        }
        //print all the running time for the four sorting algorithms we used.
        System.out.println("merge sort on " + input + " input: " + mergeRunningTimeSum + " milliseconds.");
        System.out.println("merge sort 2 on " + input + " input: " + merge2RunningTimeSum + " milliseconds.");
        System.out.println("quick sort on " + input + " input: " + quickRunningTimeSum + " milliseconds.");
        System.out.println("quick sort 2 on " + input + " input: " + quick2RunningTimeSum + " milliseconds.");
    }

    /**
     * <b>Description:</b> This is a method that compares the running time of quickSort1,
     * quickSort2, quickSort3. It creates 100 different random arrays with 10,000,000 elements,
     * and uses three different sorting algorithms mentioned above.<br>
     * <b>Conclusion:</b> I used size of 10 arrays, and quick3 is slower than the other two, but when the size
     * is increased to 10,000, there's a significantly better efficiency for quick3.
     */
    private static void task2() {
        /*TODO: please implement this method follow the description above.*/
        long start = 0, finish = 0;
        final int REPEATTIMES = 10;// this shows how many arrays are going to be generated.
        System.out.println();
        long quickRunningTimeSum = 0;
        long quick2RunningTimeSum = 0;
        long quick3RunningTimeSum = 0;
        // initialize size to 10,000,000 elements
        size = 10000000;
        //size = 10000;
        System.out.println("======== Task 2 ========");
        for (int k = 1; k <= 3; k++) {
            quickRunningTimeSum = 0;
            quick2RunningTimeSum = 0;
            quick3RunningTimeSum = 0;
            switch(k) {
                case 1:
                    System.out.println("======== Random array ========");
                    break;
                case 2:
                    System.out.println("======== Sorted array ========");
                    break;
                case 3:
                    System.out.println("======== Reverse sorted array ========");
            }
            for (int i = 0; i < REPEATTIMES; i++) {
                // fill array
                switch (k) {
                    case 1://a new random array
                        // create array
                        arr = new int[size];
                        arrCopy = new int[size];
                        mergeArr = new int[size];
                        randomGenerator = new Random();
                        random = size * 10;
                        for (int j = 0; j < size; j++)
                            arrCopy[j] = randomGenerator.nextInt(random);
                        break;
                    case 2: // using a sorted array
                        for (int j = 0; j < size; j++) arrCopy[j] = j + 1;
                        break;
                    case 3: // a reverse sorted array
                        for (int j = 0; j < size; j++) arrCopy[j] = size - j;
                        break;
                }
                // Quick sort
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                quickSort(0, size - 1);
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                quickRunningTimeSum += (finish - start);
                // Quick sort 2
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                quickSort2(0, size - 1, 2); // 2 means pivot picked at the middle of the array
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                quick2RunningTimeSum += (finish - start);
                // Quick sort 3
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                quickSort3(0, size - 1);
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                quick3RunningTimeSum += (finish - start);
            }
            switch (k) {
                case 1:
                    System.out.println
                            ("quick sort on random arrays input: " + quickRunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 2 on random arrays input: " + quick2RunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 3 on random arrays input: " + quick3RunningTimeSum + " milliseconds.");
                    break;
                case 2:
                    System.out.println
                            ("quick sort on sorted input: " + quickRunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 2 on sorted input: " + quick2RunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 3 on sorted input: " + quick3RunningTimeSum + " milliseconds.");
                    break;
                case 3:
                    System.out.println
                            ("quick sort on reverse sorted input: " + quickRunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 2 on reverse sorted input: " + quick2RunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 3 on reverse sorted input: " + quick3RunningTimeSum + " milliseconds.");
                    break;
            }
        }
    }

    /**
     *
     */
    private static void task3() {
        /*TODO: please implement this method follow the description above.*/
        long start = 0, finish = 0;
        final int REPEATTIMES = 10;// this shows how many arrays are going to be generated.
        System.out.println();
        long heapRunningTimeSum = 0;
        long quickRunningTimeSum = 0;
        long quick3RunningTimeSum = 0;
        long quick4RunningTimeSum = 0;
        long quick5RunningTimeSum = 0;
        long naturalRunningTimeSum = 0;

        // initialize size to 10,000,000 elements
        //size = 10000000;
        //size = 10; //testing numbers
        size = 10000000; //testing numbers

        System.out.println("======== Task 3 ========");
        for (int k = 1; k <= 3; k++) {
            heapRunningTimeSum = 0;
            quickRunningTimeSum = 0;
            quick3RunningTimeSum = 0;
            quick4RunningTimeSum = 0;
            quick5RunningTimeSum = 0;
            naturalRunningTimeSum = 0;
            switch(k) {
                case 1:
                    System.out.println("======== Random array ========");
                    break;
                case 2:
                    System.out.println("======== Reverse sorted array ========");
                    break;
                case 3:
                    System.out.println("======== Organ-pipe shaped array ========");
                    break;
            }
            for (int i = 0; i < REPEATTIMES; i++) {
                // fill array
                switch (k) {
                    case 1://a new random array
                        // create array

                        arr = new int[size];
                        arrCopy = new int[size];
                        mergeArr = new int[size];
                        randomGenerator = new Random();
                        random = size * 10;
                        for (int j = 0; j < size; j++)
                            arrCopy[j] = randomGenerator.nextInt(random);
                        break;
                    case 2: // a reverse sorted array
                        for (int j = 0; j < size; j++) arrCopy[j] = size - j;
                        break;
                    case 3: // organ-pipe shaped array
                        // create array
                        arr = new int[size];
                        arrCopy = new int[size];
                        mergeArr = new int[size];
                        randomGenerator = new Random();
                        random = size * 10;
                        //if(size % 2 == 0) \\
                        /* I was going to check for odd/even array sizes,
                         * but I found that natural merge sort has some design-related
                         * problems with odd numbers. Since that method was given by the prof,
                         * I decided to skip checking odd/even sizes, and use even numbers,
                         * in this case.
                         */
                        for (int j = 0; j < size/2; j++) {
                            arrCopy[j] = size / 2 - j;
                            arrCopy[size - 1 - j] = size / 2 - j;
                        }
                        break;
                }
                // heap sort
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                heapsort();
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                heapRunningTimeSum += (finish - start);
                /*System.out.println
                        ("heap sort on organ-pipe shaped arrays input: " + (finish - start) + " milliseconds.");*/
                // Quick sort
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                quickSort(0, size - 1);
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                quickRunningTimeSum += (finish - start);
                // Quick sort 3
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                quickSort3(0, size - 1);
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                quick3RunningTimeSum += (finish - start);
                // Quick sort 4
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                quickSort2(0, size - 1, 4); // 4 means it picks three pivots then gets the best one (median)
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                quick4RunningTimeSum += (finish - start);
                // Quick sort 5
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                quickSort2(0, size - 1, 5); // 5 means it picks 9 pivots then gets the best one (median)
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                quick5RunningTimeSum += (finish - start);
                // Natural merge sort
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                naturalMergeSort();
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                naturalRunningTimeSum += (finish - start);
            }
            switch (k) {
                case 1:
                    System.out.println
                            ("heap sort on random arrays input: " + heapRunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort on random arrays input: " + quickRunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 3 on random arrays input: " + quick3RunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 4 on random arrays input: " + quick4RunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 5 on random arrays input: " + quick5RunningTimeSum + " milliseconds.");
                    System.out.println
                            ("natural merge sort on random arrays input: " + naturalRunningTimeSum + " milliseconds.");
                    break;
                case 2:
                    System.out.println
                            ("heap sort on reversely sorted arrays input: " + heapRunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort on reversely sorted arrays input: " + quickRunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 3 on reversely sorted arrays input: " + quick3RunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 4 on reversely sorted arrays input: " + quick4RunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 5 on reversely sorted arrays input: " + quick5RunningTimeSum + " milliseconds.");
                    System.out.println
                            ("natural merge sort on reversely sorted arrays input: " + naturalRunningTimeSum + " milliseconds.");
                    break;
                case 3:
                    System.out.println
                            ("heap sort on organ-pipe shaped arrays input: " + heapRunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort on organ-pipe shaped arrays input: " + quickRunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 3 on organ-pipe shaped arrays input: " + quick3RunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 4 on organ-pipe shaped arrays input: " + quick4RunningTimeSum + " milliseconds.");
                    System.out.println
                            ("quick sort 5 on organ-pipe shaped arrays input: " + quick5RunningTimeSum + " milliseconds.");
                    System.out.println
                            ("natural merge sort on organ-pipe shaped arrays input: " + naturalRunningTimeSum + " milliseconds.");
                    break;
            }
        }
    }
    public static void task4() {
        /*TODO: please implement this method follow the description above.*/
        long start = 0, finish = 0;
        final int REPEATTIMES = 100;// this shows how many arrays are going to be generated.
        System.out.println();
        long heapRunningTimeSum;
        long mergeRunningTimeSum;
        long quickRunningTimeSum;
        long quick2RunningTimeSum;
        long quick3RunningTimeSum;
        long quick4RunningTimeSum;
        long quick5RunningTimeSum;
        long naturalRunningTimeSum;

        boolean quickSortIsFaster = false;
        int kExchanges = 100;
        int kDistance = 10;

        long[] quickSortWorst = new long[5];
        long[] everythingElseBest = new long[3];
        // initialize size to 10,000,000 elements
        //size = 10000000;
        //size = 10; //testing numbers
        size = 10000000; //testing numbers

        // create array
        arr = new int[size];
        arrCopy = new int[size];
        mergeArr = new int[size];
        for (int j = 0; j < size; j++) arrCopy[j] = j + 1;



        System.out.println("======== Task 4 ========");
        for (int k = 1; k <= 2; k++) {
            heapRunningTimeSum = 0;
            mergeRunningTimeSum = 0;
            quickRunningTimeSum = 0;
            quick2RunningTimeSum = 0;
            quick3RunningTimeSum = 0;
            quick4RunningTimeSum = 0;
            quick5RunningTimeSum = 0;
            naturalRunningTimeSum = 0;
            quickSortIsFaster = false;

            while (!quickSortIsFaster) {



                /*a title for tester to understand which part the program is at*/
                switch (k) {
                    case 1:
                        System.out.println("======== k exchanges ========");
                        System.out.format("The current k exchange is at: %d%n", kExchanges);
                        System.out.println("calculating...");
                        break;
                    case 2:
                        System.out.println("======== k distance ========");
                        System.out.format("The current k distance is at: %d%n", kDistance);
                        System.out.println("calculating...");
                        break;
                }
                for (int i = 0; i < REPEATTIMES; i++) {
                    // fill array
                    switch (k) {
                        case 1:
                            //k exchanges
                            randomGenerator = new Random();
                            for (int a = 0; a < kExchanges / 2; a++) {
                                random = size - 1;
                                exchangeItemsArrCopy(randomGenerator.nextInt(random), randomGenerator.nextInt(random));
                                //if two random numbers collide, that's fine.
                            }
                            break;
                        case 2:
                            //k distance
                            randomGenerator = new Random();
                            int startingPoint = (randomGenerator.nextInt(size - kDistance)); // make sure it's in range
                            for (int a = 0; a < kDistance*1000 / 2; a++) {
                                exchangeItemsArrCopy(startingPoint + randomGenerator.nextInt(kDistance),
                                        startingPoint + randomGenerator.nextInt(kDistance));
                            }
                            break;
                    }
                    // heap sort
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    heapsort();
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    heapRunningTimeSum += (finish - start);
                    // merge sort
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    mergeSort2(0, size); // mergeSort2 is faster from previous experiment
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    mergeRunningTimeSum += (finish - start);
                    // Quick sort
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    quickSort(0, size - 1);
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    quickRunningTimeSum += (finish - start);
                    // Quick sort 2
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    quickSort2(0, size - 1, 2);
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    quick2RunningTimeSum += (finish - start);
                    // Quick sort 3
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    quickSort3(0, size - 1);
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    quick3RunningTimeSum += (finish - start);
                    // Quick sort 4
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    quickSort2(0, size - 1, 4); // 4 means it picks three pivots then gets the best one (median)
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    quick4RunningTimeSum += (finish - start);
                    // Quick sort 5
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    quickSort2(0, size - 1, 5); // 5 means it picks 9 pivots then gets the best one (median)
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    quick5RunningTimeSum += (finish - start);
                    // Natural merge sort
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    naturalMergeSort();
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    naturalRunningTimeSum += (finish - start);
                }
                switch (k) {
                    case 1:
                        System.out.println
                                ("heap sort on k exchanges input: " + heapRunningTimeSum + " milliseconds.");
                        everythingElseBest[0] = heapRunningTimeSum;
                        System.out.println
                                ("merge sort on k exchanges input: " + mergeRunningTimeSum + " milliseconds.");
                        everythingElseBest[1] = mergeRunningTimeSum;
                        System.out.println
                                ("quick sort on k exchanges input: " + quickRunningTimeSum + " milliseconds.");
                        quickSortWorst[0] = quickRunningTimeSum;
                        System.out.println
                                ("quick sort 2 on k exchanges input: " + quick2RunningTimeSum + " milliseconds.");
                        quickSortWorst[1] = quick2RunningTimeSum;
                        System.out.println
                                ("quick sort 3 on k exchanges input: " + quick3RunningTimeSum + " milliseconds.");
                        quickSortWorst[2] = quick3RunningTimeSum;
                        System.out.println
                                ("quick sort 4 on k exchanges input: " + quick4RunningTimeSum + " milliseconds.");
                        quickSortWorst[3] = quick4RunningTimeSum;
                        System.out.println
                                ("quick sort 5 on k exchanges input: " + quick5RunningTimeSum + " milliseconds.");
                        quickSortWorst[4] = quick5RunningTimeSum;
                        System.out.println
                                ("natural merge sort on k exchanges input: " + naturalRunningTimeSum + " milliseconds.");
                        everythingElseBest[2] = naturalRunningTimeSum;
                        break;
                    case 2:
                        System.out.println
                                ("heap sort on k distance arrays input: " + heapRunningTimeSum + " milliseconds.");
                        System.out.println
                                ("merge sort on k distance input: " + mergeRunningTimeSum + " milliseconds.");
                        System.out.println
                                ("quick sort on k distance input: " + quickRunningTimeSum + " milliseconds.");
                        System.out.println
                                ("quick sort 2 on k distance input: " + quick2RunningTimeSum + " milliseconds.");
                        System.out.println
                                ("quick sort 3 on k distance input: " + quick3RunningTimeSum + " milliseconds.");
                        System.out.println
                                ("quick sort 4 on k distance input: " + quick4RunningTimeSum + " milliseconds.");
                        System.out.println
                                ("quick sort 5 on k distance input: " + quick5RunningTimeSum + " milliseconds.");
                        System.out.println
                                ("natural merge sort on k distance input: " + naturalRunningTimeSum + " milliseconds.");
                        break;
                }
                switch(k) {
                    case 1:
                        kExchanges += 100; // increment kForNearlySorted 100 ici.
                        break;
                    case 2:
                        kDistance += 10;
                }
                // insertSort the sub-array arr[left, right]
                for (int i = 0; i < 5; i++) {
                    long temp = quickSortWorst[i];           // store a[i] in temp
                    int j = i;                       // start shifts at i
                    // until one is smaller,
                    while (j > 0 && quickSortWorst[j - 1] >= temp) {
                        quickSortWorst[j] = quickSortWorst[j - 1];        // shift item to right
                        --j;                      // go left one position
                    }
                    quickSortWorst[j] = temp;              // insert stored item
                }  // end for
                for (int i = 0; i < 3; i++) {
                    long temp = everythingElseBest[i];           // store a[i] in temp
                    int j = i;                       // start shifts at i
                    // until one is smaller,
                    while (j > 0 && everythingElseBest[j - 1] >= temp) {
                        everythingElseBest[j] = everythingElseBest[j - 1];        // shift item to right
                        --j;                      // go left one position
                    }
                    everythingElseBest[j] = temp;              // insert stored item
                }  // end for

                if(everythingElseBest[0] > quickSortWorst[4]) quickSortIsFaster = true;
                    // quickSort is faster by all means at k.
            }
        }
    }

    public static void main(String[] args) {
        /*read = new BufferedReader(new InputStreamReader(System.in));
        randomGenerator = new Random();
        try {
            System.out.print("Please enter the array size : ");
            size = Integer.parseInt(read.readLine());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // create array
        arr = new int[size];
        arrCopy = new int[size];
        mergeArr = new int[size];
        // fill array
        random = size * 10;
        for (int i = 0; i < size; i++)
            arr[i] = arrCopy[i] = randomGenerator.nextInt(random);*/
            /*demo1("random");*/
        // arr[0..size-1] is already sorted. We randomly swap 100 pairs to make it nearly-sorted.
        /*for (int i = 0; i < 100; i++) {
            int j  = randomGenerator.nextInt(size);
            int k  = randomGenerator.nextInt(size);
            exchangeItems(j, k);
        }
        for(int i=0; i<size; i++) arrCopy[i] = arr[i];
        demo2("nearly sorted");
        for(int i=0; i<size; i++) arrCopy[i] = size-i;
        demo1("reversely sorted");*/
        task1("task 1");
        task2();
        task3();
        task4();
    }
}
