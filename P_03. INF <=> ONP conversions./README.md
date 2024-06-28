/*
Program uzywa: 
- algorytm, przedstawiony na wykladzie, konwertacji INF->ONP;
- algorytm konwertacji ONP->INF zapewniajacy minimalna ilosc nawiasow.

1) logika pierwszego zostala opisana na wykladzie.

2) drugi algorytm wykorzystuje dwa stosy dla wyrazen i priorytetow. Pozwala to uzyc minimalnej ilosci nawiasow.
(operandy maja zawsze maksymalny priorytet, pozostale priorytety sa przepisywane zgodnie z trescia)

Zrealizowana jest tazke uniwersalna (w sensie typow danych) klasa stosu, ktora moze przyjmowac zarowno argumenty string jak i int,
co pozwala uzyc jedndej i tej samej struktury dla wyrazen i priorytetow.

Klasa source zawiera 
- metody pomocnicze sluzace m.in: przepisaniu symboli do grup (o1, o2, z etc.),
zwracaniu odpowiednich priorytetow dla symboli operatorow, ich prawo/lewostronnego wiazania.
- metody z glownymi algorytmami convertINFtoONP oraz convertONPtoINF - sluza tworzeniu lancuchow wyjsciowych. Do tego 
analiza poprawnosci wyrazzenia jest wykonana w glownej petli algorytmow konwersji.

- metoda removeTrash przygotowywuje napisy do dzialania wyzej wymienionych metod, usuwajac nieprzewidywane symbole. 
- metoda addSpaces dodaje do juz przekonwertowanego napisu spacje w celu zgodnosci z przewidywanym wyjsciem
 */
