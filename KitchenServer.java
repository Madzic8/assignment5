package assignment5;

import java.util.concurrent.CompletableFuture;

public class KitchenServer extends AbstractKitchenServer{

    OrderClient orderClient;
    KitchenStatus kitchenStatus;
    Order orderList;

    public KitchenServer(OrderClient orderClient)
    {
        this.orderClient = orderClient;
    }

    @Override
    public CompletableFuture<KitchenStatus> receiveOrder(Order order) throws InterruptedException {
        cook(order);
        return null;
    }

    @Override
    public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
        CompletableFuture<OrderStatus> OrderStatus = new CompletableFuture<>();

        return null;
    }

    @Override
    public CompletableFuture<KitchenStatus> serveOrder(String orderID) throws InterruptedException {

        return null;
    }

    @Override
    protected void cook(Order order) throws InterruptedException {
        order.setStatus(OrderStatus.BeingPrepared);
        Thread.sleep(1000);
        order.setStatus(OrderStatus.Ready);
    }

    public KitchenStatus getKitchenStatus() {
        return kitchenStatus;
    }
}
