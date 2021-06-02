package assignment5;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        KitchenServer kitchenServer = new KitchenServer();
        OrderClient orderClient = new OrderClient(kitchenServer);
        GenericRestaurantForm restaurant = new GenericRestaurantForm(orderClient);
        restaurant.Start();
    }
}
