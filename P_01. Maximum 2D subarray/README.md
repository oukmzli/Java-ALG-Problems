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
