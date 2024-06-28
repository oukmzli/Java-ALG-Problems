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
