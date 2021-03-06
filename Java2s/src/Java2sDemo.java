interface MathAlgorithm {
    public int calculate(int num1, int num2);
}

class MathAdd implements MathAlgorithm{
    @Override
    public int calculate(int num1, int num2) {
        return num1 + num2;
    }
}

class MathSubstract implements MathAlgorithm{
    @Override
    public int calculate(int num1, int num2) {
        return num1 - num2;
    }
}

class MathMultiply implements MathAlgorithm{
    @Override
    public int calculate(int num1, int num2) {
        return num1 * num2;
    }
}

class MathContext {
    private MathAlgorithm algorithm;

    public MathContext(MathAlgorithm strategy){
        this.algorithm = strategy;
    }

    public int execute(int num1, int num2){
        return algorithm.calculate(num1, num2);
    }
}

public class Java2sDemo {
    private static MathContext context;

    public static void main(String[] args) {
        context = new MathContext(new MathAdd());
        System.out.println("10 + 5 = " + context.execute(10, 5));

        context = new MathContext(new MathSubstract());
        System.out.println("10 - 5 = " + context.execute(10, 5));

        context = new MathContext(new MathMultiply());
        System.out.println("10 * 5 = " + context.execute(10, 5));
    }
}
