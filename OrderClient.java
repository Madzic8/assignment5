package assignment5;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class OrderClient extends AbstractOrderClient{

    private Order order;
    private KitchenServer kitchenServer;
    Timer pollingTimer;
    ExecutorService threadpool;
    public OrderClient(KitchenServer kitchenServer)
    {
        this.kitchenServer = kitchenServer;
        this.order = new Order();
        this.threadpool = Executors.newFixedThreadPool(10);
    }

    /**
     * Start an asynchronous request to {@link AbstractKitchenServer#receiveOrder(Order)}
     * Also start {@link #startPollingServer(String)}
     */

    @Override
    public void submitOrder() throws ExecutionException, InterruptedException {
        System.out.println("test");
        Future<?> kitchenStatus = threadpool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    kitchenServer.receiveOrder(order);
                }  catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        try
        {
            if (kitchenStatus.get() == KitchenStatus.Received)
            {
                System.out.println("submitOrder Method");
                startPollingServer(order.getOrderID());
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }

    }

    /**
     * Start a new task with a periodic timer {@link #pollingTimer}
     * to ask a server periodically about the order status {@link AbstractKitchenServer#checkStatus(String)}.
     *
     * Call {@link #pickUpOrder()} when status is {@link OrderStatus#Ready} and stop the {@link #pollingTimer}.
     */

    @Override
    protected void startPollingServer(String orderId) throws InterruptedException {
        pollingTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    CompletableFuture<OrderStatus> orderStatus = kitchenServer.checkStatus(orderId);
                    orderStatus.get();
                    if (orderStatus.equals(OrderStatus.Ready))
                    {
                        System.out.println("Start Polling Server Method");
                        pickUpOrder();
                        cancel();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        pollingTimer.scheduleAtFixedRate(timerTask,1000, 10000);

    }
    /**
     * Start an asynchronous request to {@link AbstractKitchenServer#serveOrder(String)}
     */

    @Override
    protected void pickUpOrder() {
        threadpool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("pickUpOrder Method");
                    kitchenServer.serveOrder(order.getOrderID());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Order getOrder() {
        return order;
    }
}
