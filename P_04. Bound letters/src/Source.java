// Denys Kyrychenko - grupa nr 6
/*
 Program sklada sie z czterech klas: Car jako klasa wagonu, Train jako klasa pociagu,
 realizowana za pomoca podwojnej listy, klasy TrainList, realizowana za pomoca listy pojedynczej i oczewiscie klasy glownej Source.
 Klasy zawieraja konstruktory, wymagane do tworzenia nowych obiektow i podprogramy zarowno wymagane zadaniem, jak i pomocnicze.
 Kazdy wymagany zadaniem podprogram, poza Display i TrainList, dziala w czasie O(1), nie liczas czesci z wyszukiwaniem pociagu,
 gdyz zawsze wymaga ono O(n). Podanto kazdy wymagany zadaniem podprogram nie tworzy zadnych zbednych obciazen pamieciowych, lecz
 tworzy jedynie nowy obiekt dla podanej klasy lub roznorodne 'temp'y, sluzace do wymiany niektorych referencij wagonow.
 Podprogram Reverse nie uzywa zmiany referencji next lub prev, zgodnie z uwarunkowaniem zadania.
*/
import java.util.Scanner;

class Car {
    String name;
    Car prev;
    Car next;

    public Car(String name) {
        this.name = name;
        prev = null;
        next = null;
    }
}

class Train {
    Car first;
    Car last;
    String name;
    Train next;
    boolean reversed = false;

    public Train(String trainName, String firstName) {
        first = new Car(firstName);
        last = first;
        name = trainName;
        next = null;
    }

    public void reverse() {
        reversed = !reversed;
        Car temp = first;
        first = last;
        last = temp;
    }

    public void display() {
        if (first == null) {
            return;
        }

        Car current = first;
        Car currentPrev = current;

        while (current != null) {
            System.out.print(current.name + " ");

            if (currentPrev == current.prev) {
                currentPrev = current;
                current = current.next;
            } else if (currentPrev == current.next) {
                currentPrev = current;
                current = current.prev;
            } else {
                if (this.reversed) {
                    currentPrev = current;
                    current = current.prev;
                } else {
                    currentPrev = current;
                    current = current.next;
                }
            }

        }
        System.out.println();
    }

    public void insertFirst(String carName) {
        Car newCar = new Car(carName);
        if (reversed) {
            first.next = newCar;
            newCar.prev = first;
            first = first.next;
        } else {
            first.prev = newCar;
            newCar.next = first;
            first = first.prev;
        }
    }

    public void insertLast(String carName) {
        Car newCar = new Car(carName);
        if (reversed) {
            last.prev = newCar;
            newCar.next = last;
            last = last.prev;
        } else {
            last.next = newCar;
            newCar.prev = last;
            last = last.next;
        }
    }

    public void removeFirstCar() {
        if (first == null) {
            return;
        }
        if (first == last) {
            first = last = null;
        } else {
            if (!reversed) {
                first = first.next;
                if (first != null) {
                    first.prev = null;
                }
            } else {
                first = first.prev;
                if (first != null) {
                    first.next = null;
                }
            }
        }
    }

    public void removeLastCar() {
        if (last == null) {
            return;
        }
        if (first == last) {
            first = last = null;
        } else {
            if (!reversed) {
                last = last.prev;
                if (last != null) {
                    last.next = null;
                }
            } else {
                last = last.next;
                if (last != null) {
                    last.prev = null;
                }
            }
        }
    }

    public boolean isEmpty() {
        return first == null;
    }

}

class TrainList {
    Train first;

    public TrainList() {
        this.first = null;
    }

    public void addTrain(String trainName, String carName) {
        if (findTrain(trainName) != null) {
            System.out.println("Train " + trainName + " already exists");
            return;
        }

        Train newTrain = new Train(trainName, carName);
        if (first == null) {
            first = newTrain;
        } else {
            newTrain.next = first;
            first = newTrain;
        }
    }

    Train findTrain(String name) {
        Train current = first;
        while (current != null && !current.name.equals(name)) {
            current = current.next;
        }
        return current;
    }

    public void insertFirst(String trainName, String carName) {
        Train train = findTrain(trainName);
        if (train != null)
            train.insertFirst(carName);
        else
            System.out.println("Train " + trainName + " does not exist");
    }

    public void insertLast(String trainName, String carName) {
        Train train = findTrain(trainName);
        if (train != null)
            train.insertLast(carName);
        else
            System.out.println("Train " + trainName + " does not exist");
    }

    public void display(String trainName) {
        Train train = findTrain(trainName);
        if (train != null) {
            System.out.print(trainName + ": ");
            train.display();
        } else {
            System.out.println("Train " + trainName + " does not exist");
        }
    }

    public void trains() {
        Train current = first;
        System.out.print("Trains: ");
        while (current != null) {
            System.out.print(current.name + " ");
            current = current.next;
        }
        System.out.println();
    }

    public void reverseTrain(String trainName) {
        Train train = findTrain(trainName);
        if (train != null) {
            train.reverse();
        } else {
            System.out.println("Train " + trainName + " does not exist");
        }
    }

    public void delFirst(String trainName, String newTrainName) {
        Train train = findTrain(trainName);
        if (train != null) {
            String carName = train.first.name;
            addTrain(newTrainName, carName);
            train.removeFirstCar();
            if (train.isEmpty()) {
                removeTrain(train);
            }
        } else
            System.out.println("Train " + trainName + " does not exist");
    }

    public void delLast(String trainName, String newTrainName) {
        Train train = findTrain(trainName);
        if (train != null) {
            String carName = train.last.name;
            addTrain(newTrainName, carName);
            train.removeLastCar();
            if (train.isEmpty()) {
                removeTrain(train);
            }
        } else
            System.out.println("Train " + trainName + " does not exist");
    }

    private void removeTrain(Train train) {
        if (train == first) {
            first = first.next;
        } else {
            Train prev = first;
            while (prev.next != train) {
                prev = prev.next;
            }
            if (prev != null) {
                prev.next = train.next;
            }
        }
    }

    public void union(String trainName1, String trainName2) {
        Train train1 = null;
        Train train2 = null;
        Train current = first;

        while (current != null) {
            if (train1 == null && current.name.equals(trainName1))
                train1 = current;
            if (train2 == null && current.name.equals(trainName2))
                train2 = current;
            if (train1 != null && train2 != null)
                break;
            current = current.next;
        }

        if (train1 == null) {
            System.out.println("Train " + trainName1 + " does not exist");
            return;
        }
        if (train2 == null) {
            System.out.println("Train " + trainName2 + " does not exist");
            return;
        }

        if (train1.reversed && train2.reversed) {
            train1.last.prev = train2.first;
            train2.first.next = train1.last;
            train1.last = train2.last;
        } else if (!train1.reversed && !train2.reversed) {
            train1.last.next = train2.first;
            train2.first.prev = train1.last;
            train1.last = train2.last;
        } else if (!train1.reversed && train2.reversed) {
            train1.last.next = train2.first;
            train2.first.next = train1.last;
            train1.last = train2.last;
            Car temp = train1.last.next;
            train1.last.next = train1.last.prev;
            train1.last.prev = temp;
        } else if (train1.reversed && !train2.reversed) {
            train1.last.prev = train2.first;
            train2.first.prev = train1.last;
            train1.last = train2.last;
            Car temp = train1.last.next;
            train1.last.next = train1.last.prev;
            train1.last.prev = temp;
        }

        removeTrain(train2);
    }

}

public class Source {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int numberOfSets = scanner.nextInt();
        scanner.nextLine();

        while (numberOfSets-- > 0) {
            int numberOfExpressions = scanner.nextInt();
            scanner.nextLine();
            TrainList list = new TrainList();

            while (numberOfExpressions-- > 0) {
                String input, arg1, arg2;
                input = scanner.next();
                switch (input) {
                    case "New":
                        arg1 = scanner.next();
                        arg2 = scanner.next();
                        list.addTrain(arg1, arg2);
                        break;
                    case "InsertFirst":
                        arg1 = scanner.next();
                        arg2 = scanner.next();
                        list.insertFirst(arg1, arg2);
                        break;
                    case "InsertLast":
                        arg1 = scanner.next();
                        arg2 = scanner.next();
                        list.insertLast(arg1, arg2);
                        break;
                    case "Display":
                        arg1 = scanner.next();
                        list.display(arg1);
                        break;
                    case "Trains":
                        list.trains();
                        break;
                    case "Reverse":
                        arg1 = scanner.next();
                        list.reverseTrain(arg1);
                        break;
                    case "Union":
                        arg1 = scanner.next();
                        arg2 = scanner.next();
                        list.union(arg1, arg2);
                        break;
                    case "DelFirst":
                        arg1 = scanner.next();
                        arg2 = scanner.next();
                        list.delFirst(arg1, arg2);
                        break;
                    case "DelLast":
                        arg1 = scanner.next();
                        arg2 = scanner.next();
                        list.delLast(arg1, arg2);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}

/*
 * test.in
 * 1
 * 35
 * New T1 W2
 * InsertFirst T1 W1
 * InsertFirst T1 W0
 * InsertLast T1 W3
 * InsertLast T1 W5
 * Reverse T1
 * Display T1
 * New T2 W7
 * InsertFirst T2 W6
 * InsertLast T2 W8
 * Reverse T2
 * Display T2
 * Trains
 * Union T1 T2
 * Display T1
 * Reverse T1
 * Display T1
 * InsertLast T1 LS1
 * InsertFirst T1 FS1
 * Display T1
 * Reverse T1
 * Display T1
 * InsertLast T1 R1
 * DelLast T1 T3
 * InsertLast T3 R2
 * InsertLast T3 R3
 * Reverse T3
 * InsertFirst T3 R4
 * InsertFirst T3 R5
 * Reverse T3
 * Display T3
 * Union T3 T1
 * Display T3
 * Reverse T3
 * Display T3
 * 
 * OUT:
 * T1: W5 W3 W2 W1 W0
 * T2: W8 W7 W6
 * Trains: T2 T1
 * T1: W5 W3 W2 W1 W0 W8 W7 W6
 * T1: W6 W7 W8 W0 W1 W2 W3 W5
 * T1: FS1 W6 W7 W8 W0 W1 W2 W3 W5 LS1
 * T1: LS1 W5 W3 W2 W1 W0 W8 W7 W6 FS1
 * T3: R1 R2 R3 R4 R5
 * T3: R1 R2 R3 R4 R5 LS1 W5 W3 W2 W1 W0 W8 W7 W6 FS1
 * T3: FS1 W6 W7 W8 W0 W1 W2 W3 W5 LS1 R5 R4 R3 R2 R1
 * 
 */