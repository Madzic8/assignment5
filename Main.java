package assignment5;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        GenericRestaurantForm restaurant = new GenericRestaurantForm();
        restaurant.Start();
        OrderClient orderClient = new OrderClient();
        KitchenServer kitchenServer = new KitchenServer(orderClient );
        Order order = new Order();
        kitchenServer.receiveOrder(order);
        OrderStatus status = order.getStatus();
        System.out.println(status.toString());
    }
}
