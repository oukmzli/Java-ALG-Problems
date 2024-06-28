import java.util.Objects;

enum Priority {
    RED,
    ORANGE,
    YELLOW,
    GREEN,
    BLUE
}

class Pacjent implements Comparable<Pacjent> {
    private String imie;
    private String nazwisko;
    private int wiek;
    private Priority priorytet;

    // konstruktor
    public Pacjent(String imie, String nazwisko, int wiek, Priority priorytet) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.wiek = wiek;
        this.priorytet = priorytet;
    }

    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Pacjent pacjent = (Pacjent) o;
        boolean equality = (wiek == pacjent.wiek) &&
                (Objects.equals(imie, pacjent.imie)) &&
                (Objects.equals(nazwisko, pacjent.nazwisko)) &&
                (priorytet == pacjent.priorytet);

        return equality;
    }

    // hashCode
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (imie != null ? imie.hashCode() : 0);
        result = 31 * result + (nazwisko != null ? nazwisko.hashCode() : 0);
        result = 31 * result + wiek;
        result = 31 * result + (priorytet != null ? priorytet.hashCode() : 0);
        return result;
    }

    // toString
    @Override
    public String toString() {
        return "Pacjent (imie: " + this.imie + ", nazwisko: " + this.nazwisko + ")\n" + "wiek: " + this.wiek + ",\n"
                + "priorytet: " + this.priorytet;
    }

    // compareTo
    @Override
    public int compareTo(Pacjent o) {
        return this.priorytet.compareTo(o.priorytet);
    }
}

class PriorityQueue {
    private Node head;

    private class Node {
        Pacjent dane;
        Node next;

        Node(Pacjent dane) {
            this.dane = dane;
            this.next = null;
        }
    }

    public void offer(Pacjent pacjent) {
        Node newNode = new Node(pacjent);
        if (isEmpty() || pacjent.compareTo(head.dane) < 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null && current.next.dane.compareTo(pacjent) <= 0) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
    }

    public Node findPacjent(Pacjent pacjent) {
        if (isEmpty())
            return null;

        Node current = head;
        while (current.next != null) {
            if (current.next.dane.equals(pacjent)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public boolean remove(Pacjent pacjent) {
        Node nodePacjent = findPacjent(pacjent);
        if (nodePacjent != null && nodePacjent.next != null) {
            nodePacjent.next = nodePacjent.next.next;
            return true;
        }
        return false;
    }

    public boolean contains(Pacjent pacjent) {
        return findPacjent(pacjent) != null;
    }

    public Pacjent poll() {
        if (isEmpty())
            return null;
        Pacjent result = head.dane;
        // remove head
        head = head.next;
        return result;
    }

    public Pacjent peek() {
        return head == null ? null : head.dane;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

}

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
}
