package assignment5;

import java.util.Timer;

public class OrderClient extends AbstractOrderClient{

    private Order order;
    private AbstractKitchenServer kitchenServer;
    Timer pollingTimer;
    public OrderClient(KitchenServer kitchenServer)
    {
        this.kitchenServer = kitchenServer;
    }

    /**
     * Start an asynchronous request to {@link AbstractKitchenServer#receiveOrder(Order)}
     * Also start {@link #startPollingServer(String)}
     */

    @Override
    public void submitOrder() {

    }

    /**
     * Start a new task with a periodic timer {@link #pollingTimer}
     * to ask a server periodically about the order status {@link AbstractKitchenServer#checkStatus(String)}.
     *
     * Call {@link #pickUpOrder()} when status is {@link OrderStatus#Ready} and stop the {@link #pollingTimer}.
     */

    @Override
    protected void startPollingServer(String orderId) {

    }

    /**
     * Start an asynchronous request to {@link AbstractKitchenServer#serveOrder(String)}
     */

    @Override
    protected void pickUpOrder() {

    }
}
