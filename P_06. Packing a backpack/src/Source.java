/*
Algorytm polega na znajdowaniu kombinacji elementów, których łączna waga jest równa wadze docelowej. 
Zaczynamy od pierwszego elementu i zmniejszamy wagę docelową o jego wagę. 
Następnie próbujemy dodawać kolejne elementy, sprawdzając wszystkie możliwe kombinacje, aż do osiągnięcia docelowej wagi. 
Jeśli suma przekroczy wagę docelową, wracamy i próbujemy z innym elementem, powtarzając ten proces, 
aż znajdziemy odpowiednią kombinację lub wyczerpiemy wszystkie możliwości.

Funkcja rekurencyjna tego algorytmu uzywa "stos rekurencyjny", czyli "pamieta" poprzednie znaczenia np. wagi docelowej.
Funkcja iteracyjna nie ma mozliwosci uzycia tej specyfiki rekurencji, dlatego musimy imitowac taki stos
za pomoca struktury danych Stack.
 */
import java.util.Scanner;

public class Source {
    public static Scanner scanner = new Scanner(System.in);

    // iteracyjnie

    static class Node {
        int index, capacity, resultIndex;
        Node next;

        Node(int index, int capacity, int resultIndex) {
            this.index = index;
            this.capacity = capacity;
            this.resultIndex = resultIndex;
        }
    }

    static class Stack {
        private Node head;

        void push(int index, int capacity, int resultIndex) {
            Node node = new Node(index, capacity, resultIndex);
            node.next = head;
            head = node;
        }

        Node pop() {
            if (head == null)
                return null;
            Node node = head;
            head = head.next;
            return node;
        }

        boolean isEmpty() {
            return head == null;
        }
    }

    static boolean stackSimPackage(int[] weights, int capacity, int result[]) {
        Stack stack = new Stack();
        stack.push(0, capacity, 0);

        // imitacja rekurencji za pomoca stosu
        while (!stack.isEmpty()) {
            Node node = stack.pop();

            // Jesli pojemnosc jest rowna 0, znaleziono rozwiazanie
            if (node.capacity == 0) {
                return true;
            }

            // Jesli biezacy element przekracza wage, przechodzimy do kolejnego elementu
            if (node.capacity - weights[node.index] >= 0) {
                continue;
            }

            // Dodanie kolejnego elementu do staka bez obecnego elementu
            stack.push(node.index + 1, node.capacity, node.resultIndex);
            if (node.capacity - weights[node.index] >= 0) {
                // jesli przedmiot spelnia ten warunek - moze byc czescia rozwiazania
                stack.push(node.index + 1, node.capacity - weights[node.index], node.resultIndex + 1);
                result[node.resultIndex] = weights[node.index];
            }
        }

        return false;
    }

    // rekurencyjnie

    static boolean recPackage(int[] weights, int capacity, int index, int result[], int resultIndex) {
        if (capacity == 0)
            // znalezione rozwiazanie, wagi przedmiotow wyzerowali wage docelowa
            return true;
        if (index >= weights.length || capacity < 0)
            // wagi przedmiotow przekroczyli wage docelowa lub przedmioty sie skonczyli
            return false;

        if (recPackage(weights, capacity - weights[index], index + 1, result, resultIndex + 1)) {
            // jesli przedmiot spelnia warunek tego wylowania - moze byc czescia rozwiazania
            result[resultIndex] = weights[index];
            return true;
        }

        // jesli nie udalo sie znalezc rozwiazania, przechodzimy do kolejnego elementu
        return recPackage(weights, capacity, index + 1, result, resultIndex);
    }

    public static void main(String[] args) {
        int numberOfSets = scanner.nextInt();
        while (numberOfSets-- > 0) {
            int capacity = scanner.nextInt();
            int length = scanner.nextInt();
            int[] weights = new int[length];

            for (int i = 0; i < length; i++) {
                weights[i] = scanner.nextInt();
            }

            int[] result = new int[length];
            if (recPackage(weights, capacity, 0, result, 0)) {
                System.out.print("REC: " + capacity + " = ");
                for (int i = 0; i < length; i++) {
                    if (result[i] > 0) {
                        System.out.print(result[i] + " ");
                    }
                }
                stackSimPackage(weights, capacity, result);
                System.out.print("\nITER: " + capacity + " = ");
                for (int i = 0; i < length; i++) {
                    if (result[i] > 0) {
                        System.out.print(result[i] + " ");
                    }
                }
            } else {
                System.out.print("BRAK");
            }
            System.out.println();
        }
    }
}

/*
 * 6
 * 10
 * 4
 * 5 4 6 3
 * 7
 * 3
 * 1 2 3
 * 8
 * 5
 * 1 5 3 7 2
 * 10
 * 3
 * 10 5 3
 * 15
 * 5
 * 10 5 3 7 1
 * 1
 * 2
 * 2 3
 */