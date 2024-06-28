 Program sklada sie z czterech klas: Car jako klasa wagonu, Train jako klasa pociagu,
 realizowana za pomoca podwojnej listy, klasy TrainList, realizowana za pomoca listy pojedynczej i oczewiscie klasy glownej Source.
 Klasy zawieraja konstruktory, wymagane do tworzenia nowych obiektow i podprogramy zarowno wymagane zadaniem, jak i pomocnicze.
 Kazdy wymagany zadaniem podprogram, poza Display i TrainList, dziala w czasie O(1), nie liczas czesci z wyszukiwaniem pociagu,
 gdyz zawsze wymaga ono O(n). Podanto kazdy wymagany zadaniem podprogram nie tworzy zadnych zbednych obciazen pamieciowych, lecz
 tworzy jedynie nowy obiekt dla podanej klasy lub roznorodne 'temp'y, sluzace do wymiany niektorych referencij wagonow.
 Podprogram Reverse nie uzywa zmiany referencji next lub prev, zgodnie z uwarunkowaniem zadania.
