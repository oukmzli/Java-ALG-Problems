enum ProcessNames { REALTIME, DYNAMIC_HIGH, DYNAMIC_NORMAL, DYNAMIC_IDLE, IDLE }

class Process implements Comparable<Process> {
    private String name;
    private ProcessNames priotiry;
    private Process next;

    Process(String name, ProcessNames priotiry) {
        this.name = name;
        this.priotiry = priotiry;
        next = null;
    }

    public void setNextProcess(Process nextProcess) {
        next = nextProcess;
    }

    public Process getNextProcess() {
        return next;
    }

    public String getName() {
        return name;
    }

    public void setPriority(ProcessNames priotiry) {
        this.priotiry = priotiry;
    }

    public int getPriotiry() {
        switch (priotiry) {
            case REALTIME:
                return 5;
            case DYNAMIC_HIGH:
                return 4;
            case DYNAMIC_NORMAL:
                return 3;
            case DYNAMIC_IDLE:
                return 2;
            case IDLE:
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return name + " (" + priotiry + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        else if (other == null)
            return false;

        Process otherProcess = (Process) other;
        if (otherProcess.getPriotiry() != this.getPriotiry())
            return false;
        else if (otherProcess.name != this.name)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (priotiry != null ? priotiry.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Process o) {
        return Integer.compare(this.getPriotiry(), o.getPriotiry());
    }
}

class ProcessQueue {
    private Process head;
    private int size;

    ProcessQueue() {
        size = 0;
        head = null;
    }

    public void setFirstProcess(Process process) {
        head = process;
        size++;
    }

    public void setQueueLength(int length) {
        size = length;
    }

    public Process getFirstProcess() {
        return head;
    }

    public int getQueueLength() {
        return size;
    }

    public void offer(Process newProcess) {
        Process current = head;
        boolean prev = false;

        while (current != null) {
            if (current.getPriotiry() == newProcess.getPriotiry()) {
                if (current.getNextProcess() == null) {
                    prev = true;
                    break;
                } else if (current.getNextProcess().getPriotiry() != newProcess.getPriotiry()) {
                    prev = true;
                    break;
                }
            }
            current = current.getNextProcess();
        }

        if (prev) {
            newProcess.setNextProcess(current.getNextProcess());
            current.setNextProcess(newProcess);
        } else {
            newProcess.setNextProcess(head);
            head = newProcess;
        }

        ++size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "empty";

        String result = "";
        Process current = head;
        while (current != null) {
            result += current.toString();
            current = current.getNextProcess();
            if (current != null) result += " ->\n";
        }

        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        else if (other == null)
            return false;

        ProcessQueue otherPq = (ProcessQueue) other;

        if (this.size != otherPq.size)
            return false;
        else {
            Process current = head;
            Process otherCurrent = otherPq.head;
            while (current != null) {
                if (current.getName() != otherCurrent.getName()) {
                    return false;
                } else if (current.getPriotiry() != otherCurrent.getPriotiry()) {
                    return false;
                }
                current = current.getNextProcess();
                otherCurrent = otherCurrent.getNextProcess();
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        Process current = head;
        while (current != null) {
            result = 31 * result + (current.hashCode());
            current = current.getNextProcess();
        }
        return result;
    }
}

public class App {
    public static void shellSort(Process[] arr) {
        int n = arr.length;
        int gap = n / 2;

        while (gap > 0) {
            for (int i = gap; i < n; i++) {
                Process temp = arr[i];
                int j = i;
                while (j >= gap && (arr[j - gap].compareTo(temp) == 1)) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = temp;
            }
            gap /= 2;
        }
    }

    public static Process[] generateArray() {
        Process[] processesArray = {new Process("Firefox", ProcessNames.DYNAMIC_HIGH),
                new Process("Web content", ProcessNames.DYNAMIC_HIGH),
                new Process("Search", ProcessNames.DYNAMIC_NORMAL),
                new Process("Terminal", ProcessNames.DYNAMIC_NORMAL),
                new Process("None", ProcessNames.DYNAMIC_IDLE),
                new Process("Idle", ProcessNames.IDLE)};
        return processesArray;
    }

    /**
     * Metoda generuje poprawna kolejke priorytetowa tylko dlatego, ze tablica
     * ulozona jest we wlasciwej kolejnosci
     * Nie zastepuje to w zadnym wypadku funkcjonalnosci, ktore nalezy
     * zaimplementowac w ramach kolokwium.
     */
    public static ProcessQueue generateProcesses() {
        Process[] processesArray = generateArray();
        ProcessQueue pq = new ProcessQueue();
        // setFirstProcess() - metoda interfejsu klasy ProcessQueue. Trzeba ja
        // zaimplementowac.
        pq.setFirstProcess(new Process("Sys", ProcessNames.REALTIME));
        // setQueueLength() - metoda interfejsu klasy ProcessQueue. Trzeba ja
        // zaimplementowac.
        pq.setQueueLength(1);
        // getFirstProcess() - metoda interfejsu klasy ProcessQueue. Trzeba ja
        // zaimplementowac.
        Process currentPointedProcess = pq.getFirstProcess();
        for (Process process : processesArray) {
            // setNextProcess() - metoda interfejsu klasy Process. Trzeba ja
            // zaimplementowac.
            currentPointedProcess.setNextProcess(process);
            // setNextProcess() - metoda interfejsu klasy Process. Trzeba ja
            // zaimplementowac.
            currentPointedProcess = currentPointedProcess.getNextProcess();
            // setQueueLength() i getQueueLength() - metody interfejsu klasy ProcessQueue.
            // Trzeba ja zaimplementowac.
            pq.setQueueLength(pq.getQueueLength() + 1);
        }
        return pq;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("\nZadanie 1 ==========================");
        System.out.println("Processes: ");
        Process process1 = new Process("abc", ProcessNames.REALTIME);
        Process process2 = new Process("abc", ProcessNames.REALTIME);
        System.out.println("toString test: ");
        System.out.println("\tp1: " + process1);
        System.out.println("\tp2: " + process2);
        System.out.println("Equality test: " + process1.equals(process2));
        process2.setPriority(ProcessNames.IDLE);
        System.out.println("\tp2: " + process2);
        System.out.println("CompareTo test: " + process1.compareTo(process2));
        System.out.println("hashCode test: " + process2.hashCode());
        System.out.println();

        System.out.println("ProcessQueues: ");
        ProcessQueue test = new ProcessQueue();
        System.out.println("Emptiness test: " + test.isEmpty());
        test = generateProcesses();
        System.out.println("Emptiness test: " + test.isEmpty());
        System.out.println();

        ProcessQueue pq = generateProcesses();
        System.out.println(pq);
        System.out.println("pq lenght: " + pq.getQueueLength());
        System.out.println();
        System.out.println("Equality test: " + pq.equals(test));
        System.out.println();
        pq.offer(new Process("test", ProcessNames.DYNAMIC_NORMAL));
        System.out.println(pq);
        System.out.println("pq lenght: " + pq.size());
        System.out.println();
        System.out.println("Equality test: " + pq.equals(test));
        System.out.println("hashCode test: " + pq.hashCode());
        System.out.println();

        System.out.println("\nZadanie 2 ==========================");
        System.out.println("not Sorted: ");
        Process[] processesArray = generateArray();
        for (int i = 0; i < processesArray.length; i++) {
            System.out.print("[" + i + "] " + processesArray[i]);
            System.out.println();
        }
        shellSort(processesArray);
        System.out.println("\nSorted: ");
        for (int i = 0; i < processesArray.length; i++) {
            System.out.print("[" + i + "] " + processesArray[i]);
            System.out.println();
        }
    }
}