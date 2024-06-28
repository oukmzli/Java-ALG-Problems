Idea algorytmu: quickSelect, ale wybieramy pivot za pomoca magicznych piatek, co zwieksza
efektywnosc.

    Magiczne piatki zostaly zaimplementowane w metodzie select() - znajduje optymalny pivot
    jako mediany median malych podtablic dlugosci 5, co jest odporne na najgorsze przypadki wyboru
    pivota i sprawia liniowa zlozonosc - nie musimy przeszukiwac calej tabblicy. 
    Sortujemy male podtablice w insertionSort, a nastepnie przenosimy mediane z kazdej piatki 
    na kolejne indeksy od poczatku tablicy. Sortujemy ten poczatkowy fragment i bierzemy jego mediane.
    Teraz mozemy uzyc ten element jako pivot dla podzialu tablicy

    Partition w partion() - dzieli podtablice wzgledem elementu-pivot, umieszczajac elementy
    mniejsze od pivot po lewej, a wieksze lub rowne po prawej stronie. Zwraca nowy indeks pivota. Jest
    identyczne do realizacji rozdzielania w zadaniu P_07, tak samo wedlug Lomuto (co prawda uzywamy int,
    a nie long)

    quickSelect w select() - rekurencyjna metoda do znajdowania k-tego najmniejszego elementu.
    Uzywa magicznych piatek do wyboru pivota i 'partition' do podzielenia tablicy. Nastepnie,
    w zaleznosci od k i liczby elementow po lewej stronie od pivota, zwraca pivot lub rekurencyjnie
    szuka w odpowiedniej czesci tablicy. Pewnosc w odpowiednsosci czesci tablicy otrzymujemy z zasady
    dzialania partion. Elementy mniejsze od pivot zawsze sa po lewej stronie, co oznacza, ze jesli k ==
    pivot - pivot jest szukanym k-tym najmnieszym elementym (gdyz wszystko co przed pivot jest
    mniejsze).
