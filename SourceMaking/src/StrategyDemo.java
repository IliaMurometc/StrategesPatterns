interface Strategy { public void solve(); }

abstract class TemplateMethod1 implements Strategy {
    protected abstract void    start();
    protected abstract boolean nextTry();
    protected abstract boolean isSolution();
    protected abstract void    stop();

    public void solve() {
        start();
        while (nextTry() && ! isSolution())
            ;
        stop();
    }
}

class Impl1 extends TemplateMethod1 {
    private int state = 1;
    private int FIND_STATE = 5;

    protected void start() {
        System.out.println("start  ");
    }

    protected void stop() {
        System.out.println( "stop" );
    }

    protected boolean nextTry() {
        System.out.print( "nextTry-" + state++ + "  " );
        return true;
    }

    protected boolean isSolution() {
        System.out.println("isSolution-" + (state == FIND_STATE) + "  ");
        return (state == FIND_STATE);
    }
}

abstract class TemplateMethod2 implements Strategy {
    protected abstract void preProcess();
    protected abstract boolean search();
    protected abstract void postProcess();

    public void solve() {
        while (true) {
            preProcess();
            if (search()) break;
            postProcess();
        }
    }
}

class Impl2 extends TemplateMethod2 {
    private int state = 1;
    private int FIND_STATE = 5;
    protected void    preProcess()  { System.out.println("preProcess  "); }
    protected void    postProcess() { System.out.println("postProcess  "); }

    protected boolean search() {
        System.out.println("search-" + state++ + "  " );
        return state == FIND_STATE ? true : false;
    }
}

public class StrategyDemo {
    public static void clientCode( Strategy strat ) {
        strat.solve();
    }
    public static void main( String[] args ) {
        Strategy[] algorithms = { new Impl1(), new Impl2() };
        for (int i = 0; i < algorithms.length; i++) {
            clientCode( algorithms[i] );
            System.out.println("");
        }
    }
}
