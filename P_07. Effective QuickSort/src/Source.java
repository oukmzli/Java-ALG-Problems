/*
 Idea algorytmu:
 implementujemy iteracyjny QuickSort, aby uniknac nadmiernego zuzycia pamieci
 stosu, jednoczesnie uzywajac pesymistycznie O(1) dodatkowej pamieci. Algorytm
 wykorzystuje technike "mediana z trzech" do wyboru pivota oraz algorytm
 InsertionSort dla malych podzbiorow.

 Wybor pivota:
 W funkcji medianOfThree, pivot jest wybierany jako mediana z pierwszego,
 srodkowego i ostatniego elementu.

 Podzial tablicy:
 Funkcja partition dzieli tablice na dwie czesci wzgledem pivota -
 elementy mniejsze od pivota sa po lewej stronie, a wieksze po prawej.

 Sortowanie podzbiorow:
 Algorytm iteracyjnie sortuje podzbiory tablicy bez uzycia stosu.
 Dla podzbiorow mniejszych niz 20 stosuje sie algorytm InsertionSort.

 Zlozonosc czasowa:
 Algorytm dziala w oczekiwanym czasie O(n log n).

 Zlozonosc pamieciowa:
 Dzieki iteracyjnej implementacji i unikaniu wywolan rekurencyjnych,
 algorytm wykorzystuje O(1) dodatkowej pamieci w pesymistycznym
 przypadku.
 */
import java.util.Scanner;

public class Source {
    public static Scanner scanner = new Scanner(System.in);

    private static void insertionSort(long[] A, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            long key = A[i];
            int j = i - 1;
            while (j >= left && A[j] > key) {
                A[j + 1] = A[j];
                j--;
            }
            A[j + 1] = key;
        }
    }

    private static void swap(long[] A, int i, int j) {
        long temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    private static int medianOfThree(long[] A, int left, int right) {
        int mid = left + (right - left) / 2;
        if (A[left] > A[mid]) swap(A, left, mid);
        if (A[left] > A[right]) swap(A, left, right);
        if (A[mid] > A[right]) swap(A, mid, right);
        return mid;
    }

    // Funkcja wykonujaca podzial tablicy wzgledem pivota
    private static int partition(long[] A, int left, int right, int pivotIndex) {
        long pivotValue = A[pivotIndex];
        // Przenosimy pivot na koniec
        swap(A, pivotIndex, right);
        int storeIndex = left;
        // przenosimy elementy, mniejsze od pivot, do lewej czesci
        for (int i = left; i < right; i++) {
            if (A[i] < pivotValue) {
                swap(A, i, storeIndex);
                storeIndex++;
            }
        }
        // Przenosimy pivot na swoje ostateczne miejsce
        swap(A, storeIndex, right);
        return storeIndex;
    }

    public static void quickSort(long[] A) {
        int left = 0;
        int right = A.length - 1;

        // Glowna petla algorytmu QuickSort
        while (left < right) {
            // jezeli podtablica jest mniejsza niz prog, uzywamy InsertionSort
            if (right - left < 20) {
                insertionSort(A, left, right);
                break;
            } else {
                // wybieramy pivot za pomoca mediany z trzech
                int pivotIndex = medianOfThree(A, left, right);
                int partitionIndex = partition(A, left, right, pivotIndex);

                // Decydujemy, ktory podzbior sortowac w pierwsza czerge
                if (partitionIndex - left < right - partitionIndex) {
                    // Sortujemy najpierw lewy podzbior
                    insertionSort(A, left, partitionIndex - 1);
                    left = partitionIndex + 1;
                } else {
                    // Sortujemy najpierw prawy podzbior
                    insertionSort(A, partitionIndex + 1, right);
                    right = partitionIndex - 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        int numberOfSets = scanner.nextInt();
        while (numberOfSets-- > 0) {
            int length = scanner.nextInt();

            long[] A = new long[length];
            for (int i = 0; i < length; i++) {
                A[i] = scanner.nextInt();
            }
            quickSort(A);

            for (int i = 0; i < length; i++) {
                System.out.print(A[i] + " ");
            }
            System.out.println();
        }
    }
}

/*
test.in:
9
50
49 48 47 46 45 44 43 42 41 40 39 38 37 36 35 34 33 32 31 30 29 28 27 26 25 24 23 22 21 20 19 18 17
16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1 0 100 1 3 5 7 9 11 13 15 17 19 21 23 25 27 29 31 33 35 37 39
41 43 45 47 49 51 53 55 57 59 61 63 65 67 69 71 73 75 77 79 81 83 85 87 89 91 93 95 97 99 101 103
105 107 109 111 113 115 117 119 121 123 125 127 129 131 133 135 137 139 141 143 145 147 149 151 153
155 157 159 161 163 165 167 169 171 173 175 177 179 181 183 185 187 189 191 193 195 197 199 100 2 4
6 8 10 12 14 16 18 20 22 24 26 28 30 32 34 36 38 40 42 44 46 48 50 52 54 56 58 60 62 64 66 68 70 72
74 76 78 80 82 84 86 88 90 92 94 96 98 100 102 104 106 108 110 112 114 116 118 120 122 124 126 128
130 132 134 136 138 140 142 144 146 148 150 152 154 156 158 160 162 164 166 168 170 172 174 176 178
180 182 184 186 188 190 192 194 196 198 200 15 -1000 -999 -998 -997 -996 -995 -994 -993 -992 -991
-990 -989 -988 -987 -986 30 1000 999 998 997 996 995 994 993 992 991 990 989 988 987 986 985 984 983
982 981 980 979 978 977 976 975 974 973 972 971 40 -50 -45 -40 -35 -30 -25 -20 -15 -10 -5 0 5 10 15
20 25 30 35 40 45 50 55 60 65 70 75 80 85 90 95 100 105 110 115 120 125 130 135 140 145 150 50 -200
-190 -180 -170 -160 -150 -140 -130 -120 -110 -100 -90 -80 -70 -60 -50 -40 -30 -20 -10 0 10 20 30 40
50 60 70 80 90 100 110 120 130 140 150 160 170 180 190 200 210 220 230 240 250 260 270 280 290 100
51 53 55 57 59 61 63 65 67 69 71 73 75 77 79 81 83 85 87 89 91 93 95 97 99 101 103 105 107 109 111
113 115 117 119 121 123 125 127 129 131 133 135 137 139 141 143 145 147 149 151 153 155 157 159 161
163 165 167 169 171 173 175 177 179 181 183 185 187 189 191 193 195 197 199 201 203 205 207 209 211
213 215 217 219 221 223 225 227 229 231 233 235 237 239 241 243 245 247 249 100 6 8 10 12 14 16 18
20 22 24 26 28 30 32 34 36 38 40 42 44 46 48 50 52 54 56 58 60 62 64 66 68 70 72 74 76 78 80 82 84
86 88 90 92 94 96 98 100 102 104 106 108 110 112 114 116 118 120 122 124 126 128 130 132 134 136 138
140 142 144 146 148 150 152 154 156 158 160 162 164 166 168 170 172 174 176 178 180 182 184 186 188
190 192 194 196 198 200 202 204


test.out:
 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35
36 37 38 39 40 41 42 43 44 45 46 47 48 49

 1 3 5 7 9 11 13 15 17 19 21 23 25 27 29 31 33 35 37 39 41 43 45 47 49 51 53 55 57 59 61 63 65 67 69
71 73 75 77 79 81 83 85 87 89 91 93 95 97 99 101 103 105 107 109 111 113 115 117 119 121 123 125 127
129 131 133 135 137 139 141 143 145 147 149 151 153 155 157 159 161 163 165 167 169 171 173 175 177
179 181 183 185 187 189 191 193 195 197 199

 2 4 6 8 10 12 14 16 18 20 22 24 26 28 30 32 34 36 38 40 42 44 46 48 50 52 54 56 58 60 62 64 66 68
70 72 74 76 78 80 82 84 86 88 90 92 94 96 98 100 102 104 106 108 110 112 114 116 118 120 122 124 126
128 130 132 134 136 138 140 142 144 146 148 150 152 154 156 158 160 162 164 166 168 170 172 174 176
178 180 182 184 186 188 190 192 194 196 198 200

 -1000 -999 -998 -997 -996 -995 -994 -993 -992 -991 -990 -989 -988 -987 -986

 971 972 973 974 975 976 977 978 979 980 981 982 983 984 985 986 987 988 989 990 991 992 993 994 995
996 997 998 999 1000

 -50 -45 -40 -35 -30 -25 -20 -15 -10 -5 0 5 10 15 20 25 30 35 40 45 50 55 60 65 70 75 80 85 90 95
100 105 110 115 120 125 130 135 140 145

 -200 -190 -180 -170 -160 -150 -140 -130 -120 -110 -100 -90 -80 -70 -60 -50 -40 -30 -20 -10 0 10 20
30 40 50 60 70 80 90 100 110 120 130 140 150 160 170 180 190 200 210 220 230 240 250 260 270 280 290

 51 53 55 57 59 61 63 65 67 69 71 73 75 77 79 81 83 85 87 89 91 93 95 97 99 101 103 105 107 109 111
113 115 117 119 121 123 125 127 129 131 133 135 137 139 141 143 145 147 149 151 153 155 157 159 161
163 165 167 169 171 173 175 177 179 181 183 185 187 189 191 193 195 197 199 201 203 205 207 209 211
213 215 217 219 221 223 225 227 229 231 233 235 237 239 241 243 245 247 249

 6 8 10 12 14 16 18 20 22 24 26 28 30 32 34 36 38 40 42 44 46 48 50 52 54 56 58 60 62 64 66 68 70 72
74 76 78 80 82 84 86 88 90 92 94 96 98 100 102 104 106 108 110 112 114 116 118 120 122 124 126 128
130 132 134 136 138 140 142 144 146 148 150 152 154 156 158 160 162 164 166 168 170 172 174 176 178
180 182 184 186 188 190 192 194 196 198 200 202 204

 */
