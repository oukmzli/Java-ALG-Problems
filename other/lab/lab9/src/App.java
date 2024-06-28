public class App {
    // MergeSort - O(nlogn), pamieciowa O(n)



    /*
        rekurencyjnie rozwiazujemy dwie tablicy, kazda o rozmiarze 1/2 od poczatkowej, czyli 2T(n/2).
        Scalanie dziala w czasie liniowym O(n) - przechodzimy po tablicy az niee bedzie wypelniona do konca
        Rozwiazanie polega na dzieleniu az nie zostanie nam jeden element, czyli n=1, O(1)
        Stad:
            dla n=1: O(1)
            dla n>1: T(n) = 2T(n/2) + O(n)
        podstawiajac:
            dla dowolnej stalej 'k' zachodzi T(n) = T(k*n * logn) 
        Mamy:
            T(n) = 2(k*(n/2) * log(k*(n/2))) + l*n = kn logn - kn + ln =
            = kn logn - (l - k)n = n (klogn  - l + k) 
            ￼
     */
    public static void merge(int arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int L[] = new int[n1];
        int R[] = new int[n2];

        for (int i = 0; i < n1; ++i) L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j) R[j] = arr[m + 1 + j];

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
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
}