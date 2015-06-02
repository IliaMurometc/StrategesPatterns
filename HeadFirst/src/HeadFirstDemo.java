import head.first.*;

public class HeadFirstDemo {
    public static void main (String [] args){
        MallardDuck mallard = new MallardDuck();
        mallard.display();
        mallard.performQuack();
        System.out.println("--- Set new behaviors ---");
        mallard.setQuackBehavior(new MuteQuack());
        mallard.display();
        mallard.performQuack();
        System.out.println("---------------------------------");


        Duck modelDuck = new ModelDuck();
        ((ModelDuck)modelDuck).display();
        modelDuck.performFly();
        modelDuck.performQuack();
        modelDuck.swim();
        System.out.println("--- Set new behaviors ---");
        modelDuck.setFlyBehavior(new FlyRocketPowered());
        modelDuck.setQuackBehavior(new FakeQuack());
        ((ModelDuck)modelDuck).display();
        modelDuck.performFly();
        modelDuck.performQuack();
        modelDuck.swim();
    }
}
