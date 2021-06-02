package assignment5;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

public class KitchenServer extends AbstractKitchenServer{
    ExecutorService threadPool;
    Map<String, Order> orderMap;

    public KitchenServer()
    {
        this.threadPool = Executors.newFixedThreadPool(10);
        this.orderMap = new HashMap<String,Order>();
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
    public CompletableFuture<KitchenStatus> receiveOrder(Order order) throws InterruptedException, ExecutionException {
       if (order.getOrderList().size()==0)
       {
           System.out.println(KitchenStatus.Rejected.text);
       }
       else {
           System.out.println(KitchenStatus.Received.text);
           orderMap.put(KitchenStatus.Received.text, order);
           return CompletableFuture.supplyAsync(() ->
           {
               try {
                   cook(order);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               return KitchenStatus.Cooking;
           }, threadPool);
       }
            return null;
    }

    /**
     * Note that the methods should sleep for a random duration before it returns a status.
     * This is to simulate an actual server-call that might operate slowly.
     */

    @Override
    public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
        Order order = orderMap.get(orderID);
        OrderStatus status = order.getStatus();
        System.out.println("checkStatus Method");
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
        System.out.println("serveOrder Method");
        return CompletableFuture.supplyAsync(()->KitchenStatus.Served);
    }

    /**
     * Simulate cooking in this method.
     * Execute random delay and update the order status
     * {@link OrderStatus#Received} -> {@link OrderStatus#BeingPrepared} -> {@link OrderStatus#Ready}
     * @return
     */

    @Override
    public void cook(Order order) throws InterruptedException {
        try
        {
            Random rng = new Random();
            int rnd1 = rng.nextInt(2000);
            int rnd2 = rng.nextInt(2000);
            System.out.println("cook Method");

            Thread.sleep(rnd1);

            order.setStatus(OrderStatus.BeingPrepared);
            orderMap.replace(OrderStatus.BeingPrepared.text, order);

            Thread.sleep(rnd2);

            order.setStatus(OrderStatus.Ready);
            orderMap.replace(OrderStatus.Ready.text, order);

            System.out.println(OrderStatus.Ready.text);

        } catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
