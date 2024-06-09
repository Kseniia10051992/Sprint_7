import client.order.ClientOrder;
import generate.GenerateOrder;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import orderCreating.OrderCreate;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static constants.ColorScooter.*;
import static org.hamcrest.CoreMatchers.notNullValue;

@Slf4j
@RunWith(Parameterized.class)
public class CreateOrderTest {
    private static final String RESPONSE = "Получен ответ от сервера: {}";
    public static final String MESSAGE_TRACK = "track";
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;
    private final ClientOrder clientOrder = new ClientOrder();
    private final GenerateOrder generateOrder = new GenerateOrder();
    private final UtilMethods util = new UtilMethods();
    private Integer trackId;

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone,
                           Integer rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.color = color;
        this.comment = comment;

    }

    @Parameterized.Parameters(name = "{index} : color {7}")
    public static Object[][] getParameters() {
        return new Object[][]{
                {"Иван", "Иванов", "улица Ленина дом 5", "Черкизовская", "79521134567", 3, "2024-06-12", "black", new String[]{BLACK_COLOR}},
                {"Петр", "Петров", "улица Московская дом 7", "Лубянка", "79217865434", 4, "2024-06-12", "grey", new String[]{GREY_COLOR}},
                {"Семен", "Семенов", "улица Мая дом 18", "Румянцево", "79602498756", 2, "2024-06-12", "black and grey", new String[]{BLACK_COLOR, GREY_COLOR}},
                {"Карл", "Карлов", "улица Лета дом 20", "Театральная", "79119876543", 1, "2024-06-12", "не указан", new String[]{}},

        };
    }

    @After
    public void delete() {
        if (trackId != null && trackId > 0) {
            util.finishOrder(trackId);
        }
    }

    @Test
    @DisplayName("Создать заказ")
    public void createOrder() {
        OrderCreate order = generateOrder.getOrder(firstName, lastName, address, metroStation, phone,
                rentTime, deliveryDate, comment, color);
        log.info("Создание заказа: {}", order);

        Response response = clientOrder.createOrder(order);
        log.info(RESPONSE, response.body().asString());

        trackId = response.body().path(MESSAGE_TRACK);
        log.info("Создан заказ №: {}\n", trackId);

        response.then().statusCode( HttpStatus.SC_CREATED)
                .and().assertThat().body(MESSAGE_TRACK, notNullValue());
    }
}
