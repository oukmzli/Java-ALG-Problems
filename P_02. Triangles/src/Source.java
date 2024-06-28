/*
Algorytm zostal zaimplementowany przez skrzyzowanie dwoch prostszych algorytmow: 
SearchBinFirst (wymaganego przez zadanie) i MergeSort (do sortowania tablicy). 
Oczywiscie MergeSort mozna zastapic jakimkolwiek innym algorytmem, ktory posortuje tablice.

SearchBinFirst znajduje pierwszy element o danej wartosci (x) w tablicy. 
Funkcja jest dostosowana do wyszukiwania elementow w okreslonym segmencie tablicy []T.

Warunki istnienia trojkata:
Niech i, j, k beda bokami trojkata. Wtedy
1. i + j > k,
2. i + k > j,
3. j + k > i.

findTriangles wybiera dwa elementy z naszej tablicy (i & j. Tablica juz powinna byc posortowana, wiec i < j), 
a nastepnie uruchamia SearchBinFirst, ktory znajduje element mniejszy niz ich suma (i + j < k). 
Jesli taki element nie zostanie znaleziony (j >= k) - nic nie robimy. 
Pozostale dwa warunki istnienia trojkata sa spelnione automatycznie, 
poniewaz tablica jest posortowana (i + k > j i j + k > i, gdyz i < j < k). 
Otrzymujemy trzy spelnione warunki na istnienie trojkata!

Ogolna zlozonosc algorytmu to O(n^2 * logn), O(n) dla pamieci. 
SearchBinFirst ma O(logn) i pamieciowa O(1), czyli zgodnie z poleceniem
*/
import java.util.Scanner;

public class Source {
    public static Scanner scanner = new Scanner(System.in);

    // MergeSort - O(nlogn), pamieciowa O(n)
    public static void merge(int arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int L[] = new int[n1];
        int R[] = new int[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public static void MergeSort(int arr[], int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;

            MergeSort(arr, l, m);
            MergeSort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

    // SearchBinFirst - O(logn), pamieciowa - O(1)
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

    // findTriangles - O(n^2 * O(SearchBinFirst)) czyli O(n^2*logn), pamieciowa O(1)
    public static void findTriangles(int[] T) {
        int triangleCount = 0; // najwazniejsza zmienna, liczaca ilosc trojkatow
        // jest zwiekszana, jesli trzy warunki na istnienie trojkatu sa spelnione
        int outputCount = 0;

        for (int i = 0; i < T.length - 2; i++) {
            for (int j = i + 1; j < T.length - 1; j++) {
                int k = SearchBinFirst(T, j + 1, T.length - 1, T[i] + T[j]);
                if (k > j) {
                    triangleCount += (k - j);
                    for (int l = j + 1; l <= k && outputCount++ < 10; l++) {
                        System.out.printf("(" + i + "," + j + "," + l + ") ");
                    }
                }
            }
        }
        if (triangleCount != 0)
            System.out.println();
        System.out.println("Total number of triangles is: " + triangleCount);
    }

    public static void main(String[] args) {
        int sets = scanner.nextInt();
        int count = 0;

        while (count++ < sets) {
            int n = scanner.nextInt();
            int[] T = new int[n];
            for (int i = 0; i < n; i++)
                T[i] = scanner.nextInt();

            System.out.println(count + ": n= " + n);
            MergeSort(T, 0, T.length - 1);
            for (int i = 0; i < T.length; i++) {
                System.out.print(T[i]);
                if ((i > 0 && (i + 1) % 25 == 0) || i == T.length - 1)
                    System.out.println();
                else
                    System.out.printf(" ");
            }
            findTriangles(T);
        }

        scanner.close();
    }
}

// test.in
/*
 * 8
 * 3
 * 2 3 4
 * 3
 * 1 2 10
 * 5
 * 2 2 2 2 2
 * 6
 * 1 1 2 2 3 3
 * 3
 * 2147483647 2147483647 2147483647
 * 8
 * 5 5 5 5 7 7 7 8
 * 3
 * 1 2 2147483647
 * 50
 * 6 6 7 5 2 3 2 9 8 7 7 6 1 2 3 7 8 3 9 1 3 6 3 3 9 6 2 1 5 1 7 5 6 7 3 9 2 4 6
 * 6 8 5 3 5 8 4 6 4 2 3
 */

// test.out
/*
 * 1: n= 3
 * (0,1,2)
 * Total number of triangles is: 1
 * 2: n= 3
 * 1 2 10
 * Total number of triangles is: 0
 * 3: n= 5
 * 2 2 2 2 2
 * (0,1,2) (0,1,3) (0,1,4) (0,2,3) (0,2,4) (0,3,4) (1,2,3) (1,2,4) (1,3,4)
 * (2,3,4)
 * Total number of triangles is: 10
 * 4: n= 6
 * 1 1 2 2 3 3
 * (0,2,3) (0,4,5) (1,2,3) (1,4,5) (2,3,4) (2,3,5) (2,4,5) (3,4,5)
 * Total number of triangles is: 8
 * 5: n= 3
 * 2147483647 2147483647 2147483647
 * Total number of triangles is: 0
 * 6: n= 8
 * 5 5 5 5 7 7 7 8
 * (0,1,2) (0,1,3) (0,1,4) (0,1,5) (0,1,6) (0,1,7) (0,2,3) (0,2,4) (0,2,5)
 * (0,2,6)
 * Total number of triangles is: 56
 * 7: n= 3
 * 1 2 2147483647
 * Total number of triangles is: 0
 * 8: n= 50
 * 1 1 1 1 2 2 2 2 2 2 3 3 3 3 3 3 3 3 3 4 4 4 5 5 5
 * 5 5 6 6 6 6 6 6 6 6 6 7 7 7 7 7 7 8 8 8 8 9 9 9 9
 * (0,1,2) (0,1,3) (0,2,3) (0,4,5) (0,4,6) (0,4,7) (0,4,8) (0,4,9) (0,5,6)
 * (0,5,7)
 * Total number of triangles is: 10331
 */