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
import java.util.Scanner;

class Stack<T> {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    public Stack() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public void push(T element) {
        ensureCapacity();
        elements[size++] = element;
    }

    public T pop() {
        if (size == 0) {
            return null;
        }
        T element = (T) elements[--size];
        elements[size] = null;
        return element;
    }

    public T peek() {
        if (size == 0) {
            return null;
        }
        return (T) elements[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            Object[] newElements = new Object[2 * size];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }
}

public class Source {
    public static Scanner scanner = new Scanner(System.in);

    static boolean isOperand(char x) {
        return (x >= 97 && x <= 122);
    }

    static boolean isUnary(char x) {
        String unaryOperators = "~!";
        return unaryOperators.indexOf(x) >= 0;
    }

    static boolean isBinary(char x) {
        String binaryOperators = "^*/%+-<>=&|";
        return binaryOperators.indexOf(x) >= 0;
    }

    static int getPriority(char operator) {
        switch (operator) {
            case '=':
                return 1;
            case '|':
                return 2;
            case '&':
                return 3;
            case '<':
            case '>':
                return 4;
            case '+':
            case '-':
                return 5;
            case '*':
            case '/':
            case '%':
                return 6;
            case '^':
                return 7;
            case '!':
            case '~':
                return 8;
        }
        return -1;
    }

    static boolean isLeftAssociative(char operator) {
        String leftOperators = "*/%+-<>&|?";
        return leftOperators.indexOf(operator) >= 0;
    }

    static boolean isRightAssociative(char operator) {
        return operator == '^' || operator == '!' || operator == '~';
    }

    static String convertINFtoONP(String infExpression) {
        StringBuilder onpExpression = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        // zmienne do walidacji wyrazenia
        int q = 0;
        int brCounter = 0;

        for (int i = 0; i < infExpression.length(); i++) {
            char ch = infExpression.charAt(i);
            // walidacja wyrazenia
            switch (q) {
                case 0:
                    if (ch == '(') {
                        brCounter++;
                        q = 0;
                    } else if (isUnary(ch)) {
                        q = 2;
                    } else if (isOperand(ch)) {
                        q = 1;
                    } else {
                        return null;
                    }
                    break;
                case 1:
                    if (ch == ')') {
                        brCounter--;
                        if (brCounter < 0)
                            return null;
                    } else if (isBinary(ch)) {
                        q = 0;
                    } else {
                        return null;
                    }
                    break;
                case 2:
                    if (ch == '(') {
                        brCounter++;
                        q = 0;
                    } else if (isUnary(ch)) {
                        q = 2;
                    } else if (isOperand(ch)) {
                        q = 1;
                    } else {
                        return null;
                    }
                    break;
                default:
                    return null;
            }

            if (isOperand(ch)) {
                onpExpression.append(ch);
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    onpExpression.append(stack.pop());
                }
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (isUnary(ch) || isBinary(ch)) {
                while (!stack.isEmpty() && stack.peek() != '(' &&
                        (getPriority(ch) < getPriority(stack.peek()) ||
                                (getPriority(ch) == getPriority(stack.peek()) && isLeftAssociative(ch)))) {
                    onpExpression.append(stack.pop());
                }
                stack.push(ch);
            }
        }
        while (!stack.isEmpty()) {
            onpExpression.append(stack.pop());
        }
        return (q == 1 && brCounter == 0) ? onpExpression.toString() : null;
    }

    static String convertONPtoINF(String expression) {
        Stack<String> stackExpression = new Stack<>();
        Stack<Integer> stackPriority = new Stack<>();
        // zmienne do walidacji wyrazenia
        int operandCounter = 0;
        int operatorCounter = 0;

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            // walidacja wyrazenia
            if (isBinary(ch) || (i == 0 && isUnary(ch))) {
                operatorCounter++;
            } else if (isOperand(ch)) {
                operandCounter++;
            }
            if (operatorCounter >= operandCounter) {
                return null;
            }

            if (isOperand(ch)) {
                stackExpression.push(String.valueOf(ch));
                stackPriority.push(10);
            } else {
                int priority = getPriority(ch);
                if (isUnary(ch)) {
                    String operand = stackExpression.pop();
                    int opPriority = stackPriority.pop();

                    if (opPriority < priority) {
                        operand = ch + "(" + operand + ")";
                    } else {
                        operand = ch + operand;
                    }

                    stackExpression.push(operand);
                    stackPriority.push(priority);
                }

                else if (isBinary(ch)) {
                    String right = stackExpression.pop();
                    int rightPriority = stackPriority.pop();
                    String left = stackExpression.pop();
                    int leftPriority = stackPriority.pop();

                    if (leftPriority < priority) {
                        left = "(" + left + ")";
                    }
                    if (rightPriority < priority) {
                        right = "(" + right + ")";
                    }
                    if (rightPriority == priority && isLeftAssociative(ch)) {
                        right = "(" + right + ")";
                    }

                    stackExpression.push(left + ch + right);
                    stackPriority.push(priority);
                }

            }
        }
        return ((operandCounter - operatorCounter) == 1) ? stackExpression.pop() : null;
    }

    static String removeTrashINF(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (isUnary(input.charAt(i)) || isBinary(input.charAt(i)) || isOperand(input.charAt(i))
                    || input.charAt(i) == '(' || input.charAt(i) == ')')
                sb.append(input.charAt(i));
        }
        return sb.toString();
    }

    static String removeTrashONP(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (isUnary(input.charAt(i)) || isBinary(input.charAt(i)) || isOperand(input.charAt(i)))
                sb.append(input.charAt(i));
        }
        return sb.toString();
    }

    static String addSpaces(String input) {
        StringBuilder finalOutput = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            finalOutput.append(c);
            if (i < input.length() - 1) {
                finalOutput.append(" ");
            }
        }
        return finalOutput.toString();
    }

    public static void main(String[] args) {
        int numberOfExpressions = scanner.nextInt();
        scanner.nextLine();
        while (numberOfExpressions-- > 0) {
            String expression = scanner.nextLine().trim().replaceAll("\\s+", "");
            if (expression.startsWith("ONP:")) {
                expression = removeTrashONP(expression);
                expression = convertONPtoINF(expression);
                if (expression != null) {
                    System.out.println("INF: " + addSpaces(expression));
                } else {
                    System.out.println("INF: error");
                }
            } else if (expression.startsWith("INF:")) {
                expression = removeTrashINF(expression);
                expression = convertINFtoONP(expression);
                if (expression != null) {
                    System.out.println("ONP: " + addSpaces(expression));
                } else {
                    System.out.println("ONP: error");
                }
            }
        }
    }
}

// input.txt
/*
 * 16
 * INF: a)+(b
 * INF: x=~(~(~(~a)))
 * INF: ~a-~~b<c+d&!p|!!q
 * INF: x=a=b=c
 * INF: x=(a=(b=c^(d^e)))
 * INF: (a+b)*c+(d-a)*(f-b)
 * INF: a < x < b
 * INF: (x>a) + (x<b)
 * ONP: ab+a~a-+
 * ONP: xa~~~~~~=
 * ONP: xabcdefg++++++=
 * ONP: xabc===
 * ONP: a,b,. ^ .c;-,*
 * ONP: a*aa+
 * ONP: ab*cd%/
 * ONP: ab*c*d*e*
 */
