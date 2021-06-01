package assignment5;

import java.util.Map;
import java.util.concurrent.*;

public class KitchenServer extends AbstractKitchenServer{

<<<<<<< Updated upstream
    private OrderClient orderClient;
    private KitchenStatus kitchenStatus;
    ExecutorService threadPool;
    Map<String, Order> orderMap;
=======
    OrderClient orderClient;
    KitchenStatus kitchenStatus;
>>>>>>> Stashed changes

    public KitchenServer()
    {
<<<<<<< Updated upstream
        this.orderClient = orderClient;
        this.threadPool = Executors.newFixedThreadPool(10);
=======

>>>>>>> Stashed changes
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
       Runnable receiveOrderAsync = () ->
       {
           try {
               String orderID = order.getOrderID();
               orderMap.put(orderID,order);
               cook(order);
           } catch (Exception e)
           {
               System.out.println(e);
           }
       };
       threadPool.submit(receiveOrderAsync);
        return null;
    }

    /**
     * Note that the methods should sleep for a random duration before it returns a status.
     * This is to simulate an actual server-call that might operate slowly.
     */

    @Override
    public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
        Runnable checkStatusAsync = () ->
        {
            try {
                Order order = orderMap.get(orderID);
               OrderStatus status = order.getStatus();
            } catch (Exception e)
            {
                System.out.println(e);
            }
        };
        threadPool.submit(checkStatusAsync);
        return orderStatus.join();
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
        Runnable serveOrderAsync = () ->
        {
            try {
                orderMap.get(orderID);
                kitchenStatus
            } catch (Exception e)
            {
                System.out.println(e);
            }
        };
        threadPool.submit(serveOrderAsync);
        return null;
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

    public KitchenStatus getKitchenStatus() {
        return kitchenStatus;
    }

}
