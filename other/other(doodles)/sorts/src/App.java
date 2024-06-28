import java.util.Arrays;

public class App {

    static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min])
                    min = j;
            }
            int temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
    }

    static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int element = arr[i];
            int j;
            for (j = i - 1; j >= 0 && arr[j] > element; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = element;
        }
    }

    static int getGap(int gap) {
        return (gap * 10) / 13;
    }

    static void shellSort(int[] arr) {
        int gap = arr.length/2;

        while (gap > 0) {
            for (int i = gap; i < arr.length; i++) {
                int temp = arr[i];
                int j = i;

                while (j >= gap && arr[j - gap] > temp) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }

                arr[j] = temp;
            }

            gap /= 2;
        }
    }

    public static void main(String[] args) throws Exception {
        int[] array = { 1, 6, 2, 54, 54, 2, 345 };
        shellSort(array);
        System.out.println(Arrays.toString(array));
    }
}
