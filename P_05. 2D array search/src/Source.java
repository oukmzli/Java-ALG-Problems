/*
Idea glownych funkcji:
1. recFirst - 
    Wykorzystuje rekurencje do przechodzenia po wierszach i wykonuje rekurencyjny binarny poszukiwanie.

2. recLast - 
    To rowniez jest realizowane za pomoca rekurencji na podobnej zasadzie, ale rozni sie tym, ze po znalezieniu pasujacego elementu,
    poszukiwania kontynuowane sa w prawej czesci zakresu, a nie w lewej, jak to ma miejsce w funkcji recFirst. Cel tego podejscia
    polega na tym, aby upewnic sie, ze znajdujemy faktycznie ostatnie wystapienie elementu, nawet jesli ten sam element pojawia sie
    wielokrotnie w jednym wierszu. W ten sposob, po odnalezieniu elementu, algorytm dazy do przesuniecia 'mid' w kierunku konca wiersza,
    aby sprawdzic, czy istnieja kolejne, identyczne wystapienia.

3. iterFirst - 
    Przechodzi przez wiersze macierzy w petli i stosuje binarny poszukiwanie.

4. iterLast - 
    Dziala na tej samej zasadzie co iterFirst, ale kontynuuje poszukiwania nawet po znalezieniu pasujacego elementu,
    aby upewnic sie, ze to ostatnie wystapienie, tak samo jak i w recLast.

Wszystkie funkcje wykorzystuja wspolna globalna tablice `occurrence` do przechowywania wynikow (indeks wiersza i kolumny).

Wazniejsze zmienne:
     low, high - kluczowe dla binarnego dzielenia przeszukiwanego zakresu na mniejsze czesci, co pozwala na szybkie znalezienie elementu 'x'.
     mid - srodkowy indeks w aktualnym zakresie przesukiwanua
     rowIndex - zmienna, uzywana dla iteracji po wierszam w funkcjach rekurencyjnych
 */

import java.util.Scanner;

public class Source {
    public static Scanner scanner = new Scanner(System.in);

    static int[] occurrence = { -1, -1 };

    static void recFirst(int[][] matrix, int x) {
        searchRowsFirst(matrix, x, 0);
    }

    static void searchRowsFirst(int[][] matrix, int x, int rowIndex) {
        if (rowIndex >= matrix.length) {
            // warunek koncowy, przeszukano wszystkie wiersze
            return;
        }

        binarySearchFirst(matrix, rowIndex, 0, matrix[rowIndex].length - 1, x);
        if (occurrence[1] != -1) {
            // warunek koncowy, znaleziono element szukany
            return;
        }
        // Przechodzimy do nastepnego wiersza, jesli nie znalezlismy elementu szukanego
        searchRowsFirst(matrix, x, rowIndex + 1);
    }

    static void binarySearchFirst(int[][] matrix, int row, int low, int high, int x) {
        if (low > high) {
            return;
        }
        int mid = low + (high - low) / 2;
        if (matrix[row][mid] == x) {

            if (mid == 0 || matrix[row][mid - 1] != x) {
                occurrence[0] = row;
                occurrence[1] = mid;
                return;
            }
            // Szukamy w lewej polowie, gdyz macierz jest posortowana niemalejaco
            binarySearchFirst(matrix, row, low, mid - 1, x);
        } else if (matrix[row][mid] < x) {
            // Szukamy w prawej polowie, jesli element wiekszy
            binarySearchFirst(matrix, row, mid + 1, high, x);
        } else {
            // Szukamy w lewej polowie, jesli element mniejszy
            binarySearchFirst(matrix, row, low, mid - 1, x);
        }
    }

    static void recLast(int[][] matrix, int x) {
        searchRowsLast(matrix, x, 0);
    }

    static void searchRowsLast(int[][] matrix, int x, int rowIndex) {
        if (rowIndex >= matrix.length) {
            // warunek koncowy, przeszukano wszystkie wiersze
            return;
        }

        binarySearchLast(matrix, rowIndex, 0, matrix[rowIndex].length - 1, x);
        // Przechodzimy do nastepnego wiersza
        searchRowsLast(matrix, x, rowIndex + 1);
    }

    static void binarySearchLast(int[][] matrix, int row, int low, int high, int x) {
        if (low > high) {
            return;
        }
        int mid = low + (high - low) / 2;
        if (matrix[row][mid] == x) {
            if (mid == matrix[row].length - 1 || matrix[row][mid + 1] != x) {
                if (row > occurrence[0] || (row == occurrence[0] && mid > occurrence[1])) {
                    occurrence[0] = row;
                    occurrence[1] = mid;
                }
                return;
            }
            // Szukamy w prawej polowie
            binarySearchLast(matrix, row, mid + 1, high, x);
        } else if (matrix[row][mid] < x) {
            // Szukamy w prawej polowie, jesli element wiekszy
            binarySearchLast(matrix, row, mid + 1, high, x);
        } else {
            // Szukamy w lewej polowie, jesli element mniejszy
            binarySearchLast(matrix, row, low, mid - 1, x);
        }
    }

    // Czasowa zlozonosc: O(n * logm), przechodzimy po wierszach (n wierszy po m
    // elementow), szukajac elementu zadanego 'x'
    static void iterFirst(int[][] matrix, int x) {
        // for dla iteracji po wierszach
        for (int i = 0; i < matrix.length; i++) {
            // realizacja algorytmu binary search, O(logm)
            int low = 0;
            int high = matrix[i].length - 1;
            int result = -1;

            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (matrix[i][mid] == x) {
                    result = mid;
                    // przechodzimy do lewej polowy, gdyz macierz jest posortowana niemalejaco
                    high = mid - 1;
                } else if (matrix[i][mid] < x) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            if (result != -1) {
                occurrence[0] = i;
                occurrence[1] = result;
                return;
            }
        }
    }

    // taka sama, jak i w iterFirst, ale praktycznie dluzsza
    static void iterLast(int[][] matrix, int x) {
        // for dla iteracji po wierszach
        for (int i = 0; i < matrix.length; i++) {
            // realizacja algorytmu binary search, O(logm)
            int low = 0;
            int high = matrix[i].length - 1;
            int result = -1;

            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (matrix[i][mid] == x) {
                    result = mid;
                    // przechodzimy do prawej polowy
                    low = mid + 1;
                } else if (matrix[i][mid] < x) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            if (result != -1 && (i > occurrence[0] || (i == occurrence[0] && result > occurrence[1]))) {
                occurrence[0] = i;
                occurrence[1] = result;
            }
        }
    }

    static void output(int target) {
        System.out.print(target);
        if (occurrence[0] != -1)
            System.out.println(" = a[" + occurrence[0] + "][" + occurrence[1] + "]");
        else
            System.out.println(" missing in array");
        occurrence[0] = -1;
        occurrence[1] = -1;
    }

    public static void main(String[] args) {
        int numberOfSets = scanner.nextInt();
        scanner.nextLine();

        while (numberOfSets-- > 0) {
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();

            int[][] matrix = new int[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
            }

            int target = scanner.nextInt();

            recFirst(matrix, target);
            System.out.print("recFirst: ");
            output(target);

            recLast(matrix, target);
            System.out.print("recLast: ");
            output(target);

            iterFirst(matrix, target);
            System.out.print("iterFirst: ");
            output(target);

            iterLast(matrix, target);
            System.out.print("iterLast: ");
            output(target);

            System.out.println("---");
        }
    }
}

/*
 * 3
 * 3 3
 * 1 2 3
 * 4 5 6
 * 7 8 9
 * 5
 * 2 2
 * 1 2
 * 3 4
 * 5
 * 3 3
 * 1 2 3
 * 4 5 6
 * 7 8 9
 * 9
 */