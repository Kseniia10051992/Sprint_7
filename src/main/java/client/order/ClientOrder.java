package client.order;

import client.Client;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import orderCreating.OrderCreate;

public class ClientOrder extends Client {
    private final static String ORDERS = "/orders";
    private final static String CANCEL = "/cancel";


    @Step("Создать заказ")
    public Response createOrder(OrderCreate orderCreate) {
        return spec()
                .body( orderCreate )
                .when()
                .post( ORDERS );
    }

    @Step("Завершить заказ")
    public Response cancelOrder(Integer id) {
        return spec()
                .queryParam( "track", id )
                .put( ORDERS + CANCEL );
    }

    @Step("Получить список заказов")
    public Response getOrders() {
        return spec()
                .get( ORDERS );
    }
}


