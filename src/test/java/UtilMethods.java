import client.courier.ClientCourier;
import client.order.ClientOrder;
import courierAuthorization.AuthorizationCourier;
import courierCreating.CourierCreating;
import generate.GenerateCourier;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import static org.hamcrest.CoreMatchers.equalTo;


@Slf4j
public class UtilMethods {
    private static final String MESSAGE_OK = "ok";
    public static final String MESSAGE_ID = "id";
    private static final String RESPONSE = "Получен ответ от сервера: {}";
    private final ClientCourier courierClient = new ClientCourier();
    private final GenerateCourier generator = new GenerateCourier();
    private final ClientOrder orderClient = new ClientOrder();
    public Integer getIdCourier(CourierCreating courierCreating) {
        AuthorizationCourier authorizationCourier = generator.getAuthorizationCourier(courierCreating);
        log.info("Авторизация курьера \"{}\" с паролем \"{}\" на сервере",
                authorizationCourier.getLogin(), authorizationCourier.getPassword());

        Response response = courierClient.login(authorizationCourier);
        log.info(RESPONSE, response.body().asString());
        Integer courierId = response.body().path(MESSAGE_ID);
        log.info("Курьер с id = {} авторизован", courierId);
        return courierId;
    }
    public void deleteCourier(CourierCreating courierCreating) {
        int courierId = getIdCourier(courierCreating);
        log.info("Удаление созданного ранее курьера по id = {}", courierId);
        Response response = courierClient.deleteCourier(courierId);
        log.info(RESPONSE, response.body().asString());

        response.then().assertThat().body(MESSAGE_OK, equalTo(true))
                .and().statusCode(HttpStatus.SC_OK);
        log.info("Удаление курьера с id = {} успешно\n", courierId);
    }

    public void finishOrder(Integer id) {
        Response response = orderClient.cancelOrder(id);

        response.then().assertThat().body(MESSAGE_OK, equalTo(true))
                .and().statusCode(HttpStatus.SC_OK);
        log.info("Заказ {} завершен\n", id);
    }
}