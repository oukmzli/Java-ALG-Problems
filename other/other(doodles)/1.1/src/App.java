public class App {

    static int getGap(int gap) {
        if (gap < 1) return 1;
        return (gap * 10) / 13;
    }

    static void combSort(int arr[]) {
        int gap = getGap(arr.length);
        boolean swapped = true;
        
        while (gap >= 1 || swapped) {
            gap = getGap(gap);
            swapped = false;
            for (int i = 0; i < arr.length - gap; i++) {
                if (arr[i] < arr[i + gap]) {
                    int temp = arr[i];
                    arr[i] = arr[i + gap];
                    arr[i + gap] = temp;

                    swapped = true;
                }
            }
        }
    }

    static int fibonacci(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibonacci(n-1) + fibonacci(n-2);
    }

    static int findRange(int arr[], int x, int y) {
        if (x == y) return 1;

        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] <= x) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        x = low;
        high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] <= y) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        y = low;
        return y - x;
    }

    public static int SearchBinFirst(int[] T, int left, int right, int x) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (T[mid] >= x) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }

    public static void main(String[] args) throws Exception {
        int[] arr = { 64, 34, 25, 12, 22, 11, 90 };

        System.out.println("Исходный массив:");
        for (int num : arr) {
            System.out.print(num + " ");
        }

        combSort(arr);

        System.out.println("\nотсортированный массив:");
        for (int num : arr) {
            System.out.print(num + " ");
        }

        System.out.println("\nrange is: " + (findRange(arr, 90, 9)));
    }
}
