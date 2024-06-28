Algorytm polega na znajdowaniu kombinacji elementów, których łączna waga jest równa wadze docelowej. 
Zaczynamy od pierwszego elementu i zmniejszamy wagę docelową o jego wagę. 
Następnie próbujemy dodawać kolejne elementy, sprawdzając wszystkie możliwe kombinacje, aż do osiągnięcia docelowej wagi. 
Jeśli suma przekroczy wagę docelową, wracamy i próbujemy z innym elementem, powtarzając ten proces, 
aż znajdziemy odpowiednią kombinację lub wyczerpiemy wszystkie możliwości.

Funkcja rekurencyjna tego algorytmu uzywa "stos rekurencyjny", czyli "pamieta" poprzednie znaczenia np. wagi docelowej.
Funkcja iteracyjna nie ma mozliwosci uzycia tej specyfiki rekurencji, dlatego musimy imitowac taki stos
za pomoca struktury danych Stack.
