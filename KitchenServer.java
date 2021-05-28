package assignment5;

import java.util.concurrent.CompletableFuture;

public class KitchenServer extends AbstractKitchenServer{

    OrderClient orderClient;
    KitchenStatus kitchenStatus;

    public KitchenServer(OrderClient orderClient)
    {
        this.orderClient = orderClient;
    }

    @Override
    public CompletableFuture<KitchenStatus> receiveOrder(Order order) throws InterruptedException {
        CompletableFuture<KitchenStatus> kitchenStatus = getKitchenStatus();
    }

    @Override
    public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
        return null;
    }

    @Override
    public CompletableFuture<KitchenStatus> serveOrder(String orderID) throws InterruptedException {
        return null;
    }

    @Override
    protected void cook(Order order) {

    }

    public KitchenStatus getKitchenStatus() {
        return kitchenStatus;
    }
}
