/* 
Cel: Otrzymac maksymalna podtablice zgodnie ze wzorem na sume:
maxSum = 3 * DODATNIE + 2 * UJEMNE.
Program zawiera logike prezentacji tablicy dwuwymiarowej w postaci tablicy jednowymiarowej oraz algorytm Kadane'a.

- Przeksztalcenie tablicy dwuwymiarowej na jednowymiarowa:
    Wybieramy dwa dowolne wiersze, ktore beda naszym tymczasowym przedzialem [i..j] z macierzy.
    Sumujemy wartosci kolumn dla tych wierszy (z uwzglednieniem wzoru na sume), zapisujac je do tablicy tymczasowej "temp".
    Przesylajac "temp" do funkcji z algorytmem Kadane'a, otrzymujemy wartosci maksymalnej sumy, poczatku i konca szukanych kolumn.
    Jesli otrzymana wartosc sumy jest wieksza od poprzedniej, zapisujemy wynik dzialania funkcji Kadane'a.
    Na koncu dzialania funkcji przesylamy otrzymany wynik dalej, dla dalszego wyswietlenia w konsoli.

    Funkcja maxSubmatrix zwraca zmienna MaxSubmatrixResult, ktora jest realizowana za pomoca klasy.
    Zawiera w sobie wartosc maxSum, a takze numery poczatku i konca (wierszy i kolumn), w przedziale ktorych znajduje sie szukana suma.

- Algorytm Kadana:
    Jest podstawowym algorytmem, przedstawionym na wykladzie.
    Uzyty jest bez zadnych zmian w logice jego dzialania.

    Funkcja kadane zwraca zmienna KadaneResult, ktora jest realizowana za pomoca klasy.
    Zawiera w sobie wartosc maxSum, a takze numery poczatku i konca przedzialu, w ktorym znajduje sie szukana suma.
*/

import java.util.Scanner;

// Tworzenie wlasnych zmiennych KadaneResult i MaxSubmatrixResult, potrzebnych do przechowywania resultatow dzialania algorymu
class KadaneResult {
    int maxSum; // kluczowa zmienna sumy w algorytmie Kadane
    int start;
    int end;

    KadaneResult(int maxSum, int start, int end) {
        this.maxSum = maxSum;
        this.start = start;
        this.end = end;
    }
}

class MaxSubmatrixResult {
    int maxSum; // suma, wycliczana za pomoca KadaneResult.maxSum
    int rowStart;
    int rowEnd;
    int colStart;
    int colEnd;
    boolean isEmpty;

    MaxSubmatrixResult(int maxSum, int rowStart, int rowEnd, int colStart, int colEnd) {
        this.maxSum = maxSum;
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
        this.colStart = colStart;
        this.colEnd = colEnd;
    }

    // uwzgednienie ujemnej macierzy.
    MaxSubmatrixResult(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
}

public class Source {
    public static Scanner scanner = new Scanner(System.in);

    private static boolean hasPositiveElement(int[][] matrix) {
        for (int[] row : matrix)
            for (int element : row)
                if (element >= 0)
                    return true;
        return false;
    }

    // realizacja algorytmu Kadane
    public static KadaneResult kadane(int[] array) {
        int maxSum = Integer.MIN_VALUE;
        int currentSum = 0;
        int start = 0;
        int end = 0;
        int tempStart = 0;

        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            currentSum += value;

            if (maxSum < currentSum) {
                maxSum = currentSum;
                start = tempStart;
                end = i;
            }
            if (currentSum <= 0) {
                currentSum = 0;
                tempStart = i + 1;
            }
        }
        return new KadaneResult(maxSum, start, end);
    }

    public static MaxSubmatrixResult maxSubmatrix(int[][] matrix) {
        // sprawdzenie zerowej dlugosci lub czy macierz nie jest null'em
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return new MaxSubmatrixResult(0, 0, 0, 0, 0);
        }

        // sprawdzienie ujemnosci macierzy. Jesli nie posiada dodatnich elementow -
        // zwracanie flagi "is empty"
        if (!hasPositiveElement(matrix))
            return new MaxSubmatrixResult(true);

        int rows = matrix.length, columns = matrix[0].length;
        int maxSum = Integer.MIN_VALUE;

        int rowStart = 0, rowEnd = 0;
        int colStart = 0, colEnd = 0;

        for (int i = 0; i < rows; i++) {
            int[] temp = new int[columns];
            for (int j = i; j < rows; j++) {
                for (int k = 0; k < columns; k++) {
                    if (matrix[j][k] > 0)
                        temp[k] += 3 * matrix[j][k];
                    else
                        temp[k] += 2 * matrix[j][k];
                }
                KadaneResult result = kadane(temp);
                if (maxSum < result.maxSum) {
                    maxSum = result.maxSum;
                    rowStart = i;
                    rowEnd = j;
                    colStart = result.start;
                    colEnd = result.end;
                } else if (maxSum == result.maxSum) {
                    if (maxSum < result.maxSum ||
                            (maxSum == result.maxSum && (i < rowStart ||
                                    (i == rowStart && j < rowEnd) ||
                                    (i == rowStart && j == rowEnd && result.start < colStart) ||
                                    (i == rowStart && j == rowEnd && result.start == colStart
                                            && result.end < colEnd)))) {
                        // przy rownej sumie wybieramy podtablice, ktora znajduje sie "wyzej",
                        maxSum = result.maxSum;
                        rowStart = i;
                        rowEnd = j;
                        colStart = result.start;
                        colEnd = result.end;
                    } else if ((j - i + 1) * (result.end - result.start + 1) < (rowEnd - rowStart + 1)
                            * (colEnd - colStart + 1)) {
                        // ma mniejszy rozmiar
                        maxSum = result.maxSum;
                        rowStart = i;
                        rowEnd = j;
                        colStart = result.start;
                        colEnd = result.end;
                    }
                }
            }
        }
        return new MaxSubmatrixResult(maxSum, rowStart, rowEnd, colStart, colEnd);

    }

    public static void main(String[] args) {
        int dataAmount = scanner.nextInt();
        for (int count = 0; count < dataAmount; count++) {
            if (scanner.hasNextInt())
                scanner.next();
            if (!scanner.hasNextInt())
                scanner.next();
            int rows = scanner.nextInt();
            int columns = scanner.nextInt();
            int[][] matrix = new int[rows][columns];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
            }
            MaxSubmatrixResult result = maxSubmatrix(matrix);
            System.out.print(count + 1 + ": ");
            if (result.isEmpty)
                System.out.println("ms_tab is empty");
            else
                System.out.print("ms_tab = a[" + result.rowStart + ".." + result.rowEnd + "]" + "["
                        + result.colStart + ".." + result.colEnd + "], msum=" + result.maxSum + "\n");
        }
    }
}

// test.in
/*
 * // 1: Test na obsluge liczb dodatnich.
 * // 2: Test na obsluge liczb ujemnych(test na "is empty").
 * // 3: Test na obsluge elementow zerowych.
 * // 4: Test na skomplikowana kombinacje liczb dodatnich i ujemnych.
 * // 5: Test na duza macierz z roznorodnymi liczbami.
 * // 6: Test na jednorodne elementy.
 * // 7: Podstawowy test na malej macierzy z naprzemiennym rozmieszczeniem.
 * // 8: Test na sredniej macierzy z rownomiernym naprzemiennoscia.
 * // 9: Test na duzej macierzy z ekstremalnymi wartosciami.
 * // 10: Zlozony test na duzej macierzy z wyraźnym centrum.
 * // 11: Test na duzej macierzy z pelnym naprzemiennoscia.
 * // 12: Test na pierwszy zerowy wiersz.
 * 11
 * 
 * 1: 2 2
 * 1 2
 * 3 4
 * 
 * 2: 3 3
 * -1 -2 -3
 * -4 -5 -6
 * -7 -8 -9
 * 
 * 3: 2 3
 * 0 0 0
 * 0 0 0
 * 
 * 4: 3 4
 * 1 -1 2 -2
 * 3 -3 4 -4
 * 5 -5 6 -6
 * 
 * 5: 4 5
 * 1 2 -1 -4 5
 * -1 3 4 -2 -3
 * 2 -3 -4 5 1
 * -4 2 3 -1 -2
 * 
 * 6: 2 2
 * 5 5
PP * 5 5
 * 
 * 7: 2 2
 * 1 -1
 * -1 1
 * 
 * 8: 3 3
 * 1 -1 1
 * -1 1 -1
 * 1 -1 1
 * 
 * 9: 5 5
 * 100000 -100000 100000 -100000 100000
 * -100000 100000 -100000 100000 -100000
 * 100000 -100000 100000 -100000 100000
 * -100000 100000 -100000 100000 -100000
 * 100000 -100000 100000 -100000 100000
 * 
 * 10: 10 10
 * -1 -1 -1 -1 -1 -1 -1 -1 -1 -1
 * -1 2 2 2 2 2 2 2 2 -1
 * -1 2 -1 -1 -1 -1 -1 -1 2 -1
 * -1 2 -1 100 100 -1 -1 -1 2 -1
 * -1 2 -1 100 100 -1 -1 -1 2 -1
 * -1 2 -1 -1 -1 -1 -1 -1 2 -1
 * -1 2 2 2 2 2 2 2 2 -1
 * -1 -1 -1 -1 -1 -1 -1 -1 -1 -1
 * -1 -1 -1 -1 -1 -1 -1 -1 -1 -1
 * -1 -1 -1 -1 -1 -1 -1 -1 -1 -1
 * 
 * 11: 10 10
 * 1 -1 1 -1 1 -1 1 -1 1 -1
 * -1 1 -1 1 -1 1 -1 1 -1 1
 * 1 -1 1 -1 1 -1 1 -1 1 -1
 * -1 1 -1 1 -1 1 -1 1 -1 1
 * 1 -1 1 -1 1 -1 1 -1 1 -1
 * -1 1 -1 1 -1 1 -1 1 -1 1
 * 1 -1 1 -1 1 -1 1 -1 1 -1
 * -1 1 -1 1 -1 1 -1 1 -1 1
 * 1 -1 1 -1 1 -1 1 -1 1 -1
 * -1 1 -1 1 -1 1 -1 1 -1 1
 * 
 * 12: 7 1
 * 0
 * 1
 * 0
 * 1
 * 1
 * 2
 * 0
 */

// test.out
/*
 * 1: ms_tab = a[0..1][0..1], msum=30
 * 2: ms_tab is empty
 * 3: ms_tab = a[0..0][0..0], msum=0
 * 4: ms_tab = a[0..2][0..2], msum=45
 * 5: ms_tab = a[0..3][1..4], msum=35
 * 6: ms_tab = a[0..1][0..1], msum=60
 * 7: ms_tab = a[0..0][0..0], msum=3
 * 8: ms_tab = a[0..2][0..2], msum=7
 * 9: ms_tab = a[0..4][0..4], msum=1500000
 * 10: ms_tab = a[1..6][1..8], msum=1304
 * 11: ms_tab = a[0..9][0..9], msum=50
 * 12: ms_tab = a[1..5][0..0], msum=15
 */