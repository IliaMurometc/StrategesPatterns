import com.journaldev.design.strategy.CreditCardStrategy;
import com.journaldev.design.strategy.Item;
import com.journaldev.design.strategy.PaypalStrategy;
import com.journaldev.design.strategy.ShoppingCart;

public class ShoppingCartDemo {

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        Item item1 = new Item("1234",10);
        Item item2 = new Item("5678",40);

        cart.addItem(item1);
        cart.addItem(item2);

        //pay by paypal
        cart.pay(new PaypalStrategy("myemail@example.com", "mypwd"));

        Item item3 = new Item("1111",20);
        cart.addItem(item3);

        //pay by credit card
        cart.pay(new CreditCardStrategy("Pankaj Kumar", "1234567890123456", "786", "12/15"));
    }
}
