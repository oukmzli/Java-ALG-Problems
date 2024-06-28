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
