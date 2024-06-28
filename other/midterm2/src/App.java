enum Wartosc { DZIEWIATKA, DZIESIATKA, WALET, DAMA, KROL, AS }
enum Kolor { TREFL, KARO, KIER, PIK }

class Karty24 {
    Wartosc wartosc;
    Kolor kolor;

    Karty24(Wartosc wartosc, Kolor kolor) {
        this.wartosc = wartosc;
        this.kolor = kolor;
    }

    @Override
    public String toString() {
        return "( " + wartosc + ", " + kolor + " )";
    }

    // "przeladowanie" operatora <=, uzywane dla podzialu lomuto
    public boolean isLessThanOrEqual(Karty24 other) {
        if (this.wartosc.ordinal() < other.wartosc.ordinal()) {
            return true;
        } else if (this.wartosc.ordinal() == other.wartosc.ordinal()) {
            return true;
        }
        return false;
    }
}

public class App {
    public static void main(String[] args) {
        App app = new App();

        Karty24[] tab = {
                new Karty24(Wartosc.WALET, Kolor.TREFL),
                new Karty24(Wartosc.KROL, Kolor.PIK),
                new Karty24(Wartosc.DZIESIATKA, Kolor.TREFL),
                new Karty24(Wartosc.WALET, Kolor.TREFL), 
                new Karty24(Wartosc.WALET, Kolor.KIER),
                new Karty24(Wartosc.DZIEWIATKA, Kolor.KIER), 
                new Karty24(Wartosc.DAMA, Kolor.KARO),
                new Karty24(Wartosc.AS, Kolor.KARO)
        };

        // Zadanie 1
        System.out.println("Przed posortowaniem: ");
        for (int i = 0; i < tab.length; i++) {
            System.out.println((i + 1) + ": " + tab[i]);
        }
        System.out.println();

        int k = 6;
        System.out.println(k + "-ty najmniejszy to:");
        System.out.print(app.ktyElement(tab, 0, tab.length - 1, k) + "\n");

        System.out.println();

        // Zadanie 2
        app.quickSort(tab, 0, tab.length - 1);
        System.out.println("Po posortowaniu (dla sprawdzenia): ");
        for (int i = 0; i < tab.length; i++) {
            System.out.println((i + 1) + ": " + tab[i]);
        }
        System.out.println();

        System.out.println("Po sortowaniu zliczaniem: ");
        Karty24[] posortowaneZliczaniem = app.sortowanieZliczanie(tab);
        for (int i = 0; i < posortowaneZliczaniem.length; i++) {
            System.out.println(posortowaneZliczaniem[i]);
        }
        System.out.println();
    }

    // Zadanie 1
    Karty24 ktyElement(Karty24[] tablica, int beg, int end, int k) {
        // musimy stworzyc kopie, zeby tablica orginalna pozostala w swoim poczatkowym stanie
        Karty24[] tempArray = new Karty24[tablica.length];
        for (int i = 0; i < tablica.length; i++) {
            tempArray[i] = tablica[i];
        }
        return quickSelect(tempArray, beg, end, k);
    }

    // uzywam algorytmu quick select. jest podobny do quickSort'a, ale przy pivot rownemu k zwracamy
    // element z tym indexem (tab[pi])
    Karty24 quickSelect(Karty24[] tablica, int beg, int end, int k) {
        if (beg <= end) {
            int pi = podzialLomuto(tablica, beg, end);
            int elementIndex = pi - beg + 1;
            if (elementIndex == k) {
                return tablica[pi];
            } else if (elementIndex > k) {
                return quickSelect(tablica, beg, pi - 1, k);
            } else {
                return quickSelect(tablica, pi + 1, end, k - elementIndex);
            }
        }
        return null;
    }

    void quickSort(Karty24[] tablica, int beg, int end) {
        if (beg < end) {
            int pi = podzialLomuto(tablica, beg, end);
            quickSort(tablica, beg, pi - 1);
            quickSort(tablica, pi + 1, end);
        }
    }

    // Zadanie 2
    int podzialLomuto(Karty24[] tablica, int beg, int end) {
        Karty24 pivot = tablica[end];
        int i = (beg - 1);
        for (int j = beg; j < end; j++) {
            if (tablica[j].isLessThanOrEqual(pivot)) {
                i++;
                Karty24 temp = tablica[i];
                tablica[i] = tablica[j];
                tablica[j] = temp;
            }
        }
        Karty24 temp = tablica[i + 1];
        tablica[i + 1] = tablica[end];
        tablica[end] = temp;
        return i + 1;
    }

    Karty24[] sortowanieZliczanie(Karty24[] zliczanieTab) {
        // tworzymy tablice zliczalna count, w ktorej bedziemy przechowywac ilosc poszczegolnych
        // elemwntow Kolor.
        // jako dlugosc uzywamy ilosci elementow w naszym enum
        int length = Kolor.values().length; // 4
        int[] count = new int[length];
        Karty24[] posortowaneTab = new Karty24[zliczanieTab.length];

        // zliczamy kolory
        for (Karty24 karta : zliczanieTab) {
            count[karta.kolor.ordinal()]++;
        }

        // zsuwamy index'y, zeby miec ich na miejscach swoich odpowieniokow w kolorach
        for (int i = 1; i < length; i++) {
            count[i] += count[i - 1];
        }

        // uzupelniamy posortowaneTab[] uwzgledniajac ilosci kolorow w cout[]
        for (int i = zliczanieTab.length - 1; i >= 0; i--) {
            Karty24 karta = zliczanieTab[i];
            posortowaneTab[count[karta.kolor.ordinal()] - 1] = karta;
            count[karta.kolor.ordinal()]--;
        }

        return posortowaneTab;
    }
}
