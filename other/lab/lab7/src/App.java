public class App {

    public static void insertionSort(int[] array) {
        int counter = 0;
        for (int i = 1; i < array.length; i++) {
            int element = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > element) {
                array[j + 1] = array[j];
                j--;
                counter++;
            }
            array[j + 1] = element;
        }

        System.out.println("insertion counter: " + counter);
    }

    public static int getH(int[] array) {
        int h = 1;
        while (h < array.length) {
            h = 3 * h + 1;
        }
        return (h - 1) / 3;
    }

    public static void shellSort(int[] array) {
        int h = getH(array);
        int counter = 0;

        while (h >= 1) {
            for (int i = h; i < array.length; i++) {
                int element = array[i];
                int j = i;
                while (j >= h && array[j - h] > element) {
                    array[j] = array[j - h];
                    j -= h;
                    counter++;
                }
                array[j] = element;
            }
            h = h / 3;
        }
        System.out.println("shell counter: " + counter);
    }

    public static int minIndex(int[] array) {
        int minIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    public static void insertionSortSentinel(int[] array) {
        int minIndex = minIndex(array);

        int temp = array[minIndex];
        array[minIndex] = array[0];
        array[0] = temp;

        for (int i = 1; i < array.length; i++) {
            int element = array[i];
            int j = i - 1;
            while (array[j] > element) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = element;
        }
    }

    public static void stableSelectionSort(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j][0] < array[minIndex][0]) {
                    minIndex = j;
                }
            }

            int minValue = array[minIndex][0];
            int minid = array[minIndex][1];

            int j = minIndex;
            while (j > i) {
                array[j][0] = array[j - 1][0];
                array[j][1] = array[j - 1][1];
                j--;
            }
            array[i][0] = minValue;
            array[i][1] = minid;
        }
    }

    public static void main(String[] args) {
        int[] array1 = { 3, 1, 4, 7, 0, 9, 2 };
        int[] array2 = { 3, 1, 4, 7, 0, 9, 2 };

        shellSort(array1);
        insertionSort(array2);
        /*
         * int[][] stableArray = new int[][] { { 4, 0 }, { 5, 1 }, { 3, 2 }, { 2, 3 }, {
         * 4, 4 }, { 1, 5 } };
         * stableSelectionSort(stableArray);
         * 
         * for (int i = 0; i < stableArray.length; i++) {
         * System.out.print("[" + stableArray[i][0] + ", " + stableArray[i][1] + "]");
         * }
         * System.out.println();
         */
        for (int num : array1)
            System.out.print(num + " ");
        System.out.println();
        for (int num : array2)
            System.out.print(num + " ");
    }
}
