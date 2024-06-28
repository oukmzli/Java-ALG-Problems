public class App {

    
    public static void main(String[] args) throws Exception {
        foo(2);
    }

    private static void foo(int x) {
        assert x > 5;
        System.out.println(x);
    }
}