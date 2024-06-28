// Tablica jednowymiarowa
//==============================================
import java.util.Random;
import java.util.Scanner;

class Wektor {
  private int[] vector;    // referencja do wektora
  private int maxSize;     // maksymalna dlugosc wektora
  private int currentSize; // aktualna dlugosc wektora
  // ============================================= konstruktor

  public Wektor(int maxSize_) {
    maxSize = maxSize_;
    currentSize = 0;
    vector = new int[maxSize];
  }

  public int[] getVector() { return vector.clone(); }

  public int getMaxSize() { return maxSize; }

  public int getCurrentSize() { return currentSize; }

  // ==================================== ustawienie wektora
  public void setVector(int[] vector) {
    if (maxSize < vector.length) {
      this.vector = vector;
      // Nadpisuje wczesniejsze ustawienie maxSize.
      maxSize = vector.length;
    } else {
      System.arraycopy(vector, 0, this.vector, 0, vector.length);
    }
    // Nadpisuje wczesniejsze ustawienie currentSize dla obu powyzszych
    // przypadkow.
    currentSize = vector.length;
  }

  public void setCurrentSize(int currentSize) {
    if (currentSize >= 0 && currentSize <= maxSize) {
      this.currentSize = currentSize;
    } else {
      throw new IllegalArgumentException("nieprawidlowy rozmiar");
    }
  }

  // ==================================== wczytywanie wektora
  public void readVector(int nrOfIntegers, Scanner sc) {
    for (int i = 0; i < nrOfIntegers;) {
      if (sc.hasNextInt()) {
        vector[i] = sc.nextInt();
        ++i;
      } else {
        // Zbiera to, co zostalo wpisane i nie bylo typu int.
        sc.nextLine();
      }
    }
  }

  // ===================================== losowanie wektora
  /** Losowanie nrOfIntegers liczb z przedzialu [min,max] do wektora vector. */
  public void randVector(int nrOfIntegers, int min, int max) {
    System.out.println("Losowanie " + nrOfIntegers + " liczb z przedzialu [" +
                       min + "," + max + "]");
    for (int i = 0; i < nrOfIntegers; i++)
      // max+1, zeby przedzial byl domkniety z gory.
      vector[i] = new Random().nextInt(max + 1 - min) + min;
  }

  // ===================================== wyswietlanie wektora
  public void display() {
    System.out.println("Liczba elementow = " + currentSize);
    System.out.println("Zawartosc wektora ");
    for (int i = 0; i < currentSize; i++) {
      System.out.print(vector[i] + ", ");
      if ((i + 1) % 10 == 0)
        System.out.println();
    }
    System.out.println();
  }

  public void display(int begin, int end) {
    int size = end - begin + 1;
    if (size > currentSize) {
      System.out.println("Liczba elementow nie moze byc wieksza niz " +
                         currentSize);
      return;
    }
    System.out.println("Liczba elementow = " + size);
    System.out.print("Indeks poczatku=" + begin + " Indeks konca=" + end +
                     ". ");
    System.out.println("Zawartosc podwektora ");
    // i<=end mniejsze lub rowne, poniewaz end jest indeksem.
    for (int i = begin; i <= end; i++) {
      System.out.print(vector[i] + ", ");
      if ((i - begin + 1) % 10 == 0)
        System.out.println();
    }
    System.out.println();
  }

  // metoda Hornera
  public String hornersMethod(int x) {
    int result = 0;
    for (int i = 0; i < (currentSize); i++) {
      result = vector[i] + (x * res);
    }
    return ("x=" + result);
  }

  // rekurencja
  public String hornersMethodRecursive(int x) {
    return "x=" + hornersMethodHelper(x, 0, 0);
  }

  private int hornersMethodHelper(int x, int i, int res) {
    if (i < currentSize)
      return hornersMethodHelper(x, i + 1, vector[i] + (x * res));
    else
      return res;
  }

} // End of class wektor
  ////////////////////////////////////////////////////////////

class GrowingSubArrayResult {
  public int maxSum;
  public int start;
  public int end;
  // ============================================= konstruktor

  public GrowingSubArrayResult(int maxSum, int start, int end) {
    this.maxSum = maxSum;
    this.start = 0;
    this.end = 0;
  }
}

/**
Klasa wektorApp, zawierajaca metode main(), pozwalajaca na wybor i ilustracje
dzialania podanych operacji na tablicy.
 */
class WektorApp {
  /** Zapobiega bledom gdy nie int. */
  public static int getInt(Scanner sc) {
    if (sc.hasNextInt()) {
      return sc.nextInt();
    }
    // Zbiera to, co wpisane i nie typu int.
    sc.nextLine();
    return -1;
  }

  /** Ustawia i wyswietla elementy wektora (recznie, losowo, predefiniowany) */
  public static void setVectorElements(Scanner sc, Wektor wektor, int[] vec) {
    System.out.print("Podaj aktualna dlugosc wektora, "
                     + " <= " + wektor.getMaxSize() + ": ");
    int currentSizeTmp = getInt(sc);
    while (currentSizeTmp <= 0 || currentSizeTmp > wektor.getMaxSize()) {
      System.out.println("Niepoprawna dlugosc wektora");
      System.out.print("Podaj nowa dlugosc wektora: ");
      currentSizeTmp = getInt(sc);
    }
    wektor.setCurrentSize(currentSizeTmp);
    System.out.println(
        "Wybierz: 1-czytanie, 2-losowanie, 3-predefiniowany, inne - koniec");
    int choice = getInt(sc);
    switch (choice) {
    case 1:
      System.out.println("Czytanie " + currentSizeTmp + " liczb integer");
      wektor.readVector(currentSizeTmp, sc);
      break;
    case 2:
      System.out.println("Losowanie " + currentSizeTmp + " liczb integer");
      System.out.print("Podaj minimum: ");
      int min = sc.nextInt();
      System.out.print("Podaj maksimum: ");
      int max = sc.nextInt();
      if (max < min)
        max = min;
      wektor.randVector(currentSizeTmp, min, max);
      break;
    case 3:
      wektor.setVector(vec);
      break;
    default:
      return;
    }
    wektor.display();
  }

  /**
  setVectorElements(Scanner sc, Wektor wektor, int[] vec = new
  int[]{2,-6,2,-1})
   */
  public static void setVectorElements(Scanner sc, Wektor wektor) {
    setVectorElements(sc, wektor, new int[] {2, -6, 2, -1});
  }

  public static void insertVectorElement(Scanner sc, Wektor wektor) {
    if (wektor.getCurrentSize() < wektor.getMaxSize()) {
      System.out.println("wprowadz wartosc nowego elementu: ");
      int newValue = sc.nextInt();
      int vector[] = wektor.getVector();
      vector[wektor.getCurrentSize()] = newValue;
      wektor.setVector(vector);
      wektor.setCurrentSize(wektor.getCurrentSize() + 1);
      System.out.println("element zostal dodany");
    } else {
      System.out.println("brak miejsca w wektorze");
    }
    wektor.display();
  }

  public static void deleteElement(Wektor wektor, int elementIndex) {
    int[] vector = wektor.getVector();
    for (int i = elementIndex; i < wektor.getCurrentSize(); i++) {
      vector[i] = vector[i + 1];
    }
    vector[wektor.getCurrentSize()] = 0;
    wektor.setCurrentSize(wektor.getCurrentSize() - 1);
    wektor.setVector(vector);
  }

  public static void deleteVectorElements(Scanner sc, Wektor wektor) {
    System.out.println("wprowadz wartosc usuwanego elementu: ");
    int removeValue = sc.nextInt();
    System.out.println("Wybierz: 1-usun pierwsze wystapienie, 2-usun kazde "
                       + "wystapienie, inne - koniec");
    int choice = getInt(sc);
    boolean isInVector = false;
    int[] vector = wektor.getVector();
    switch (choice) {
    case 1:
      // pierwsze
      for (int i = 0; i < wektor.getCurrentSize(); i++) {
        if (vector[i] == removeValue) {
          deleteElement(wektor, i);
          isInVector = true;
          break;
        }
      }
      if (!isInVector)
        System.out.println("Wektor nie posiada rzadanego elementu");
      else
        System.out.println("Element zostal usuniety");
      break;
    case 2:
      // wszystkie
      for (int i = 0; i < wektor.getCurrentSize(); i++) {
        if (vector[i] == removeValue) {
          deleteElement(wektor, i);
          isInVector = true;
        }
      }
      if (!isInVector)
        System.out.println("Wektor nie posiada rzadanego elementu");
      else
        System.out.println("Elementy zostaly usuniete");
      break;
    default:
      return;
    }
    wektor.display();
  }

  public static boolean[] checkOrder(Wektor wektor) {
    boolean increasingDecreasingStable[] = {true, true, true};
    // boolean increasing = true, decreasing = true, stable = true;
    int[] vector = wektor.getVector();
    for (int i = 0; i < wektor.getCurrentSize() - 1; i++) {
      if (vector[i] <= vector[i + 1])
        increasingDecreasingStable[1] = false;
      // decreasing = false;
      else if (vector[i] >= vector[i + 1])
        increasingDecreasingStable[0] = false;
      // increasing = false;
      if (vector[i] != vector[i + 1])
        increasingDecreasingStable[2] = false;
      // stable = false;
    }
    return increasingDecreasingStable;
  }

  public static boolean[] checkOrder(Wektor wektor, int start, int end) {
    boolean increasingDecreasingStable[] = {true, true, true};
    // boolean increasing = true, decreasing = true, stable = true;
    int[] vector = wektor.getVector();
    for (int i = start; i < end; i++) {
      if (vector[i] <= vector[i + 1])
        increasingDecreasingStable[1] = false;
      // decreasing = false;
      else if (vector[i] >= vector[i + 1])
        increasingDecreasingStable[0] = false;
      // increasing = false;
      if (vector[i] != vector[i + 1])
        increasingDecreasingStable[2] = false;
      // stable = false;
    }
    return increasingDecreasingStable;
  }

  public static void minMax(Wektor wektor) {
    if (wektor.getCurrentSize() == 0)
      System.out.println("wektor jest pusty");
    else {
      int[] vector = wektor.getVector();
      int min = vector[0], max = vector[0];
      for (int i : vector) {
        if (i < min)
          min = i;
        if (i > max)
          max = i;
      }
      System.out.println("min: " + min + ", max: " + max);
    }
  }

  // O(n^3)
  public void removeDuplicatesOn3(Wektor wektor) {
    int[] vector = wektor.getVector();
    for (int i = 0; i < wektor.getCurrentSize() - 1; i++) {
      for (int j = i + 1; j < wektor.getCurrentSize(); j++) {
        if (vector[i] == vector[j]) {
          wektor.setVector(vector);
          deleteElement(wektor, j);
          vector = wektor.getVector();
          j--;
        }
      }
    }
  }

  // O(n^2)
  public void removeDuplicatesOn2(Wektor wektor) {
    int[] temp = new int[wektor.getMaxSize()];
    int tempSize = 0;

    int[] vector = wektor.getVector();
    for (int i = 0; i < wektor.getCurrentSize(); i++) {
      boolean isDuplicate = false;
      for (int j = 0; j < tempSize; j++) {
        if (temp[j] == vector[i]) {
          isDuplicate = true;
          break;
        }
      }
      if (!isDuplicate) {
        temp[tempSize] = vector[i];
        ++tempSize;
      }
    }

    for (int i = 0; i < tempSize; i++) {
      vector[i] = temp[i];
    }
    wektor.setVector(vector);
    wektor.setCurrentSize(tempSize);
  }

  public static GrowingSubArrayResult growingSubArray(Wektor wektor) {
    int start = 0, end = 0, currentSum = 0, maxSum = Integer.MIN_VALUE,
        tempStart = 0;
    int[] vector = wektor.getVector();

    for (int i = 0; i < wektor.getCurrentSize(); i++) {
      int value = vector[i];
      currentSum += value;
      if (maxSum < currentSum) {
        if (checkOrder(wektor, tempStart, i)[0] == true) {
          maxSum = currentSum;
          end = i;
          start = tempStart;
        }
      }
      if (currentSum <= 0) {
        tempStart = i + 1;
      }
    }

    return new GrowingSubArrayResult(maxSum, start, end);
  }

  public static void main(String[] args) {

    Wektor testVector = new Wektor(100);
    // Tworzy jeden obiekt sc.
    Scanner sc = new Scanner(System.in);

    WektorApp.setVectorElements(sc, testVector);
    // insertVectorElement(sc, testVector);
    // deleteVectorElements(sc, testVector);
    // checkOrder(testVector);
    // minMax(testVector);
    GrowingSubArrayResult result = growingSubArray(testVector);
    System.out.println("maxSum: " + result.maxSum + ", [" + result.start +
                       ", " + result.end + "]");

    /*
   // Kalkulator do wielomianów online:
   //
   https://www.symbolab.com/solver/polynomial-equation-calculator/2x%5E%7B3%7D-6x%5E%7B2%7D%2B2x%20-1%2C%20x%3D3?or=input
   // lub po prostu: https://www.wolframalpha.com/
   // Przykład użycia: -4x^3+6x^2+2x,x=3
   Wektor forHornerVector = new Wektor(100);
   WektorApp.setVectorElements(sc, forHornerVector, new int[] {2, -6, 2, -1});
   // x==3,
   // wielomian==5; x==-1,
   //  wielomian==-11, x==2,
   //  wielomian==-5
   System.out.println(forHornerVector.hornersMethod(3));
   System.out.println(forHornerVector.hornersMethod(-1));
   System.out.println(forHornerVector.hornersMethod(2));

   WektorApp.setVectorElements(
       sc, forHornerVector, new int[] {-4, 6, 2, 0}); // x==3, wielomian==-48;
                                                      // x==-1, wielomian==8,
                                                      // x==2, wielomian==-4
   System.out.println(forHornerVector.hornersMethod(3));
   System.out.println(forHornerVector.hornersMethod(-1));
   System.out.println(forHornerVector.hornersMethod(2));

   WektorApp.setVectorElements(
       sc, forHornerVector, new int[] {1, 6, 11, 6}); // x==3, wielomian==120;
                                                      // x==-1, wielomian==0;
                                                      // x==2, wielomian==60
   System.out.println(forHornerVector.hornersMethod(3));
   System.out.println(forHornerVector.hornersMethod(-1));
   System.out.println(forHornerVector.hornersMethod(2));

   WektorApp.setVectorElements(
       sc, forHornerVector, new int[] {-4, 0, 2, -3}); // x==-1, wielomian==-1;
   System.out.println(forHornerVector.hornersMethod(-1));
   */
  }
} // End of class wektorApp