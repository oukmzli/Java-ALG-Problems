/*
Idea algorytmu: quickSelect, ale wybieramy pivot za pomoca magicznych piatek, co zwieksza
efektywnosc.

    Magiczne piatki zostaly zaimplementowane w metodzie select() - znajduje optymalny pivot
    jako mediany median malych podtablic dlugosci 5, co jest odporne na najgorsze przypadki wyboru
    pivota i sprawia liniowa zlozonosc - nie musimy przeszukiwac calej tabblicy. 
    Sortujemy male podtablice w insertionSort, a nastepnie przenosimy mediane z kazdej piatki 
    na kolejne indeksy od poczatku tablicy. Sortujemy ten poczatkowy fragment i bierzemy jego mediane.
    Teraz mozemy uzyc ten element jako pivot dla podzialu tablicy

    Partition w partion() - dzieli podtablice wzgledem elementu-pivot, umieszczajac elementy
    mniejsze od pivot po lewej, a wieksze lub rowne po prawej stronie. Zwraca nowy indeks pivota. Jest
    identyczne do realizacji rozdzielania w zadaniu P_07, tak samo wedlug Lomuto (co prawda uzywamy int,
    a nie long)

    quickSelect w select() - rekurencyjna metoda do znajdowania k-tego najmniejszego elementu.
    Uzywa magicznych piatek do wyboru pivota i 'partition' do podzielenia tablicy. Nastepnie,
    w zaleznosci od k i liczby elementow po lewej stronie od pivota, zwraca pivot lub rekurencyjnie
    szuka w odpowiedniej czesci tablicy. Pewnosc w odpowiednsosci czesci tablicy otrzymujemy z zasady
    dzialania partion. Elementy mniejsze od pivot zawsze sa po lewej stronie, co oznacza, ze jesli k ==
    pivot - pivot jest szukanym k-tym najmnieszym elementym (gdyz wszystko co przed pivot jest
    mniejsze).
 */
import java.util.Scanner;

public class Source {
    public static Scanner scanner = new Scanner(System.in);

    // sortowanie przez wstawianie ze straznikiem
    public static void insertionSort(int[] arr, int left, int right) {
        int minIndex = left;
        for (int i = left + 1; i <= right; i++) {
            if (arr[i] < arr[minIndex]) {
                minIndex = i;
            }
        }
        swap(arr, minIndex, left);
    
        for (int i = left + 1; i <= right; i++) {
            int current = arr[i];
            int j = i - 1;
            while (arr[j] > current) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = current;
        }
    }

    private static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    private static int partition(int[] A, int left, int right, int pivotIndex) {
        int pivotValue = A[pivotIndex];
        swap(A, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (A[i] < pivotValue) {
                swap(A, i, storeIndex);
                storeIndex++;
            }
        }
        swap(A, storeIndex, right);
        return storeIndex;
    }

    public static int select(int[] A, int left, int right, int k) {
        if (left == right) {
            return A[left];
        }

        // Metoda magicznych piatek, mediana median, szukamy indeks mediany
        int n = right - left + 1; // ilosc elementow w podtablicy
        int numMedians = (n + 4) / 5; // ilosc pelnych grup po 5

        for (int i = 0; i < numMedians; ++i) {
            // wyliczamy indeksy piatek
            int subLeft = left + i * 5;
            int subRight = subLeft + 4;
            if (subRight > right) subRight = right;
            // sortujemy piatke
            insertionSort(A, subLeft, subRight);
            // wybieramy mediane
            int medianIndex = subLeft + (subRight - subLeft) / 2;
            // Przesuwamy mediane kazdej grupy na poczatek
            swap(A, left + i, medianIndex);
        }
    
        int pivotIndex = left + numMedians - 1;
        pivotIndex = partition(A, left, right, pivotIndex);

        int length = pivotIndex - left + 1;
        if (k == length) {
            // Jesli k jest rowne ilosci elementow w lewej czesci - return pivot
            return A[pivotIndex];
        } else if (k < length) {
             // szukamy w lewej czesci, zmniejszamy prawa granice
            return select(A, left, pivotIndex - 1, k);
        } else {
            // Szukamy w prawej czesci, zmniejszamy k ba ilosc elementow w lewej czesci
            return select(A, pivotIndex + 1, right, k - length);
        }
    }

    // Funkcja do wypisania wyniku
    public static void findKthElement(int[] A, int k) {
        if (k < 1 || k > A.length) {
            System.out.println(k + " brak");
        } else {
            System.out.println(k + " " + select(A, 0, A.length - 1, k));
        }
    }

    public static void main(String[] args) throws Exception {
        int numberOfSets = scanner.nextInt();
        while (numberOfSets-- > 0) {
            int n = scanner.nextInt();
            int[] A = new int[n];
            for (int j = 0; j < n; j++) {
                A[j] = scanner.nextInt();
            }
            int m = scanner.nextInt();
            while (m-- > 0) {
                int k = scanner.nextInt();
                findKthElement(A, k);
            }
        }
    }
}

/*
test.in: 
3
9
9 8 7 6 5 4 3 2 1
3
1 9 5
15
11 3 9 6 14 2 8 5 12 1 15 4 10 7 13
4
1 15 3 8
16
11 3 9 6 14 2 8 5 12 1 15 4 10 7 13 16
5
1 15 16 3 8

test.out:
1 1
9 9
5 5
1 1
15 15
3 3
8 8
1 1
15 15
16 16
3 3
8 8
 */
