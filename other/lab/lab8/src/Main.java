public class Main {

    void gwiazdki(int liczbaGwiazd) {
        if (liczbaGwiazd == 0)
            return;
        for (int i = 0; i < liczbaGwiazd; i++) {
            System.out.print('*');
        }
        System.out.print("\n");
        gwiazdki(liczbaGwiazd - 1);
        for (int i = 0; i < liczbaGwiazd; i++) {
            System.out.print('*');
        }
        System.out.print("\n");
    }

    boolean isPalindrome(String str) {
        if (str.length() == 1)
            return true;

        if (str.charAt(0) != str.charAt(str.length() - 1))
            return false;

        return isPalindrome(str.substring(1, str.length() - 1));
    }

    // какая сложность у этой функции?
    boolean isPalindromeIteration(String str) {
        for (int i = 0; i < str.length() / 2; i++) {
            if (str.charAt(i) != str.charAt(str.length() - 1 - i))
                return false;
        }
        return true;
    }

    int fibonacci(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    int fibonacciMemoization(int n, int memArray[]) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    int fibonacciIteration(int n) {
        if (n <= 0)
            return 0;
        else if (n == 1)
            return 1;

        int a = 0, b = 1, c = 0;

        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    public static void main(String[] args) {
        Main mainObj = new Main();
        // mainObj.gwiazdki(10);
        // System.out.println(mainObj.isPalindromeIteration("zaradnydyndaraz"));
        // System.out.println(mainObj.isPalindrome("zaradnydyndaraz"));

        System.out.println(mainObj.fibonacci(3));
        System.out.println(mainObj.fibonacciIteration(3));

    }

}


/*
 * 
 * n == 0 - x^n = 1
 * n == parzyste - x^n = (x^(n/2))^2
 * n == nieparzyste - x^n = x * x^(n-1) = x * (x^(n/2))^2
 * 
 * T(n):
 * 1
 * T(n/2) + 1
 * T(n - 1) + 1
 * 
 * T(n) = T(n/2) + 1
 * T(n) = T(n - 1) + 1
 * 
 */

 /*
  * T(n) = n/2 + 1
  T(n) = n/k
  T(n) = n/n
  */