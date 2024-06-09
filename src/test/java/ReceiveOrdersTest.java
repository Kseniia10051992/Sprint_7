import client.order.ClientOrder;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

@Slf4j
public class ReceiveOrdersTest {
    private static final String RESPONSE = "Получен ответ от сервера: {}";
    public static final String ORDERS_FIELD = "orders";
    ClientOrder clientOrder = new ClientOrder();

    @Test
    @DisplayName("Получить список заказов")
    public void getListOfOrders() {
        Response response = clientOrder.getOrders();
        log.info(RESPONSE, response.body().asString());

        response.then().statusCode( HttpStatus.SC_OK)
                .and().assertThat().body(ORDERS_FIELD, notNullValue());
    }
}
