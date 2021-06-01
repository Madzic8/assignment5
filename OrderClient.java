package assignment5;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderClient extends AbstractOrderClient{

    private Order order;
    private AbstractKitchenServer kitchenServer;
    Timer pollingTimer;
    ExecutorService threadpool;
    public OrderClient(KitchenServer kitchenServer)
    {
        this.kitchenServer = kitchenServer;
        threadpool = Executors.newFixedThreadPool(10);
    }

    /**
     * Start an asynchronous request to {@link AbstractKitchenServer#receiveOrder(Order)}
     * Also start {@link #startPollingServer(String)}
     */

    @Override
    public void submitOrder() {
        threadpool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    kitchenServer.receiveOrder(order);
                    startPollingServer(order.getOrderID());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
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
                    if (orderStatus.equals(OrderStatus.Ready))
                    {
                        pickUpOrder();
                        cancel();

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        pollingTimer.schedule(timerTask,1000);

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
                    kitchenServer.serveOrder(order.getOrderID());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
