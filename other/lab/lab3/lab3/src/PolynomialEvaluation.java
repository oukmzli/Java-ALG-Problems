public class PolynomialEvaluation {

    public static double evaluate(double[] coefficients, double x) {
        return evaluateRecursive(coefficients, x, 0, coefficients.length - 1);
    }

    private static double evaluateRecursive(double[] coefficients, double x, int start, int end) {
        if (start == end) {
            return coefficients[start];
        }

        int mid = (start + end) / 2;
        double[] evenCoefficients = new double[(mid - start) / 2 + 1];
        double[] oddCoefficients = new double[(end - mid) / 2];

        for (int i = start, j = 0; i <= end; i += 2, j++) {
            evenCoefficients[j] = coefficients[i];
            if (i + 1 <= end) {
                oddCoefficients[j] = coefficients[i + 1];
            }
        }

        double evenValue = evaluateRecursive(evenCoefficients, x * x, 0, evenCoefficients.length - 1);
        double oddValue = evaluateRecursive(oddCoefficients, x * x, 0, oddCoefficients.length - 1);

        return evenValue + x * oddValue;
    }

    public static void main(String[] args) {
        double[] coefficients = {1, 2, 3, 4, 5};
        double x = 2;
        double result = evaluate(coefficients, x);
        System.out.println("Wartość wielomianu: " + result);
    }
}