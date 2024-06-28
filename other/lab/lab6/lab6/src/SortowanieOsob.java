import java.util.Arrays;
import java.util.Comparator;

class Osoba implements Comparable<Osoba> {
    private String imie;
    private String nazwisko;

    // konstruktor
    public Osoba(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    @Override
    public int compareTo(Osoba inna) {
        if (this.nazwisko.compareTo(inna.nazwisko) > 0)
            return 1;
        else if (this.nazwisko.compareTo(inna.nazwisko) < 0)
            return -1;
        else {
            if (this.imie.compareTo(inna.imie) > 0)
                return 1;
            else if (this.imie.compareTo(inna.imie) < 0)
                return -1;
            else
                return 0;
        }
    }

    // toString
    @Override
    public String toString() {
        return nazwisko + " " + imie;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }
}

class NazwiskoComparator implements Comparator<Osoba> {
    @Override
    public int compare(Osoba a, Osoba b) {
        if (a.getNazwisko().compareTo(b.getNazwisko()) > 0)
            return 1;
        else if (a.getNazwisko().compareTo(b.getNazwisko()) < 0)
            return -1;
        else
            return 0;
    }
}

public class SortowanieOsob {
    public static void main(String[] args) {
        Osoba[] osoby = {
                new Osoba("Job", "Jackes"),
                new Osoba("Nate", "Aaaa"),
                new Osoba("Jack", "Jobs"),
                new Osoba("Elly", "Aaaa")
        };

        System.out.println("przed sortowaniem");
        for (Osoba i : osoby) {
            System.out.println(i);
        }

        
        Arrays.sort(osoby, new NazwiskoComparator());

        System.out.println("\npo sortowaniu nazwisk (klasa implementujaca comparator)");
        for (Osoba i : osoby) {
            System.out.println(i);
        }

        Arrays.sort(osoby, new Comparator<Osoba>() {
            @Override
            public int compare(Osoba a, Osoba b) {
                if (a.getImie().compareTo(b.getImie()) > 0)
                    return 1;
                else if (a.getImie().compareTo(b.getImie()) < 0)
                    return -1;
                else
                    return 0;
            }
        });

        System.out.println("\npo sortowaniu imion (klasa anonimowa, comparator)");
        for (Osoba i : osoby) {
            System.out.println(i);
        }


        Arrays.sort(osoby, (a, b) -> a.getNazwisko().compareTo(b.getNazwisko()));

        System.out.println("\npo sortowaniu nazwisk (klasa anonimowa, wyrazenie lambda)");
        for (Osoba i : osoby) {
            System.out.println(i);
        }
    }
}
