import java.lang.Math;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedStack {
    private Node top;

    public LinkedStack() {
        this.top = null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void push(int x) {
        Node newElement = new Node(x);
        if (top != null) {
            newElement.next = top;
        }
        top = newElement;
    }

    public void pop() {
        if (top != null) {
            top = top.next;
        }
    }

    public int findMin() {
        if (top == null) return 0;

        Node current = top;
        int min = top.data;
        while (current.next != null) {
            if (current.data < min)
                min = current.data;
        }

        return min;
    }
}

class Point {
    int x;
    int y;

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
};

public class App {

    public static void delDupl(int[] array) {
        int end = array.length;

        for (int i = 0; i < end; i++) {
            for (int j = i + 1; j < end; j++) {
                if (array[i] == array[j]) {
                    for (int k = j; k < end - 1; k++) {
                        array[k] = array[k + 1];
                    }
                    end--;
                    j--;
                }
            }
        }
    }

    public static double distance(Point p) {
        return Math.sqrt((p.x * p.x + p.y * p.y));
    }

    public static void insertionSort(Point[] array) {
        for (int i = 1; i < array.length; i++) {
            Point element = array[i];
            int j = i - 1;

            while (j >= 0 && distance(array[j]) > distance(element)) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = element;
        }
    }

    public static void recursiveFibonacci(int i, int n) {
        
    }

    public static void main(String[] args) throws Exception {
        int[] array = { 1, 2, 1, 2, 3, 3 };
        delDupl(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();

        Point[] points = { new Point(5, 5), new Point(1, 1), new Point(4, 4), new Point(2, 2) };
        insertionSort(points);
        for (int i = 0; i < points.length; i++) {
            System.out.println(points[i] + " ");
        }
    }
}
