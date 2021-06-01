package assignment5;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KitchenServer extends AbstractKitchenServer{

    private OrderClient orderClient;
    private KitchenStatus kitchenStatus;
    ExecutorService threadPool;
    Map<String, Order> orderMap;

    public KitchenServer(OrderClient orderClient)
    {
        this.orderClient = orderClient;
        this.threadPool = Executors.newFixedThreadPool(10);
    }

    @Override
    public CompletableFuture<KitchenStatus> receiveOrder(Order order) throws InterruptedException {
        String orderID = order.getOrderID();
        orderMap.put(orderID,order);
        threadPool.submit(cook(order));
        return null;
    }

    @Override
    public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {

        return
    }

    @Override
    public CompletableFuture<KitchenStatus> serveOrder(String orderID) throws InterruptedException {

        return null;
    }

    @Override
    protected Runnable cook(Order order) throws InterruptedException {
        try
        {
            order.setStatus(OrderStatus.BeingPrepared);
            Thread.sleep(1000);
            order.setStatus(OrderStatus.Ready);
        } catch (Exception e)
        {

        }
        return null;
    }

    public KitchenStatus getKitchenStatus() {
        return kitchenStatus;
    }
}
