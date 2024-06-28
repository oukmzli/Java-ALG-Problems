import java.util.Arrays;

public class Sort {

  // CombSort
  static int getNextGap(int gap) {
    if (gap < 1)
      return 1;
    return (gap * 10) / 13;
  }

  static void combSort(int arr[]) {
    int gap = arr.length;
    boolean swapped = true;

    while (gap != 1 || swapped == true) {
      swapped = false;
      gap = getNextGap(gap);
      for (int i = 0; i < arr.length - gap; i++) {
        if (arr[i] > arr[i + gap]) {
          int temp = arr[i];
          arr[i] = arr[i + gap];
          arr[i + gap] = temp;
        }
      }
    }
  }

  // Selection Sort
  static void selectionSort(int arr[][]) {
    for (int i = 0; i < arr.length - 1; i++) {
      int min = i;
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[j][0] < arr[min][0])
          min = j;
      }
      if (min != i) {
        int temp = arr[i][0];
        arr[i][0] = arr[min][0];
        arr[min][0] = temp;
        int indexTemp = arr[i][1];
        arr[i][1] = arr[min][1];
        arr[min][1] = indexTemp;
      }
    }
  }

  static void insertionSort(int arr[][]) {
    for (int i = 1; i < arr.length; i++) {
        int[] key = arr[i];
        int j = i - 1;

        while (j >= 0 && arr[j][0] > key[0]) {
            arr[j + 1] = arr[j];
            j = j - 1;
        }
        arr[j + 1] = key;
    }
}


  public static void main(String[] args) {
    // CombSort
    int arr[] = {2, 3, 4, 5, 1, 6};
    System.out.println("Initial array: \t" + Arrays.toString(arr));
    combSort(arr);
    System.out.println("Sorted array: \t" + Arrays.toString(arr));

    ///////
    System.out.println("\n\nSELECTION SORT\n");
    ///////

    // Selection Sort
    int[][] selectionSortArrays = {{4, 0}, {5, 1}, {3, 2},
                                   {2, 3}, {4, 4}, {1, 5}};

                                   insertionSort(selectionSortArrays);
    System.out.println(Arrays.deepToString(selectionSortArrays));


  }
}