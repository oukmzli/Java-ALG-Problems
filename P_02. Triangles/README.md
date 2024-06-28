Algorytm zostal zaimplementowany przez skrzyzowanie dwoch prostszych algorytmow: 
SearchBinFirst (wymaganego przez zadanie) i MergeSort (do sortowania tablicy). 
Oczywiscie MergeSort mozna zastapic jakimkolwiek innym algorytmem, ktory posortuje tablice.

SearchBinFirst znajduje pierwszy element o danej wartosci (x) w tablicy. 
Funkcja jest dostosowana do wyszukiwania elementow w okreslonym segmencie tablicy []T.

Warunki istnienia trojkata:
Niech i, j, k beda bokami trojkata. Wtedy
1. i + j > k,
2. i + k > j,
3. j + k > i.

findTriangles wybiera dwa elementy z naszej tablicy (i & j. Tablica juz powinna byc posortowana, wiec i < j), 
a nastepnie uruchamia SearchBinFirst, ktory znajduje element mniejszy niz ich suma (i + j < k). 
Jesli taki element nie zostanie znaleziony (j >= k) - nic nie robimy. 
Pozostale dwa warunki istnienia trojkata sa spelnione automatycznie, 
poniewaz tablica jest posortowana (i + k > j i j + k > i, gdyz i < j < k). 
Otrzymujemy trzy spelnione warunki na istnienie trojkata!

Ogolna zlozonosc algorytmu to O(n^2 * logn), O(n) dla pamieci. 
SearchBinFirst ma O(logn) i pamieciowa O(1), czyli zgodnie z poleceniem
