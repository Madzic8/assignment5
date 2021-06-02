package assignment5;

import java.util.Map;
import java.util.concurrent.*;

public class KitchenServer extends AbstractKitchenServer{
    ExecutorService threadPool;
    Map<String, Order> orderMap;

    public KitchenServer()
    {
        this.threadPool = Executors.newFixedThreadPool(10);
    }

    /**
     * This method should save the order to the map
     * and return a confirmation that the order is received {@link KitchenStatus#Received}
     * or a rejection {@link KitchenStatus#Rejected}
     *
     * When an order is received, a {@link #cook(Order)} task should be launced in th {@link #threadPool}
     *
     * Note that the methods should sleep for a random duration before it returns a status.
     * This is to simulate an actual server-call that might operate slowly.
     */

    @Override
    public CompletableFuture<KitchenStatus> receiveOrder(Order order) throws InterruptedException {
        orderMap.put(order.getOrderID(), order);
        threadPool.submit(cook(order));
        if (order == null)
        {
            return CompletableFuture.supplyAsync(()-> KitchenStatus.Rejected);
        }
        else return CompletableFuture.supplyAsync(()-> KitchenStatus.Received);
    }

    /**
     * Note that the methods should sleep for a random duration before it returns a status.
     * This is to simulate an actual server-call that might operate slowly.
     */

    @Override
    public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
        Order order = orderMap.get(orderID);
        OrderStatus status = order.getStatus();
        return CompletableFuture.supplyAsync(()-> status);
    }

    /**
     * Allows a client to picks up the order if it is ready {@link OrderStatus#Ready}.
     * Should remove the order from the {@link #orderMap}
     *
     * Note that the methods should sleep for a random duration before it returns a status.
     * This is to simulate an actual server-call that might operate slowly.
     */

    @Override
    public CompletableFuture<KitchenStatus> serveOrder(String orderID) throws InterruptedException {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Order order = orderMap.get(orderID);
                    order.setStatus(OrderStatus.BeingPrepared);
                    Thread.sleep(1000);
                    order.setStatus(OrderStatus.Served);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPool.submit(task);
        return CompletableFuture.supplyAsync(()->KitchenStatus.Served);
    }

    /**
     * Simulate cooking in this method.
     * Execute random delay and update the order status
     * {@link OrderStatus#Received} -> {@link OrderStatus#BeingPrepared} -> {@link OrderStatus#Ready}
     * @return
     */

    @Override
    protected Runnable cook(Order order) throws InterruptedException {
        try
        {
            order.setStatus(OrderStatus.BeingPrepared);
            Thread.sleep(1000);
            order.setStatus(OrderStatus.Ready);
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
}
