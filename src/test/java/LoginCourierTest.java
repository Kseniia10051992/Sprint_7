import client.courier.ClientCourier;
import courierAuthorization.AuthWithoutLogin;
import courierAuthorization.AuthWithoutPassword;
import courierAuthorization.AuthorizationCourier;
import courierCreating.CourierCreating;
import generate.GenerateCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

@Slf4j
public class LoginCourierTest extends GeneralTest {
    public static final String AUTHORIZATION_COURIER = "Авторизация курьера: {}";
    private static final String RESPONSE = "Получен ответ от сервера: {}";
    public static final String INSUFFICIENT_DATA = "Недостаточно данных для входа";
    private static final String MESSAGE_FIELD = "message";
    public static final String MESSAGE_ID = "id";
    public static final String ACCOUNT_NOT_FOUND = "Учетная запись не найдена";
    private final GenerateCourier generator = new GenerateCourier();
    private final ClientCourier clientCourier = new ClientCourier();
    private AuthorizationCourier authorizationCourier;

    @Before
    public void setUp() {
        courierCreating = generator.getCourierCreating();
        clientCourier.create(courierCreating);
        authorizationCourier = generator.getAuthorizationCourier(courierCreating);
    }

    @Test
    @DisplayName("Авторизация с существующими данными")
    public void authorizationCourierCorrect() {
        log.info(AUTHORIZATION_COURIER, authorizationCourier);
        Response response = clientCourier.login(authorizationCourier);
        log.info(RESPONSE + "\n", response.body().asString());

        response.then().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body(MESSAGE_ID, allOf(notNullValue(), greaterThan(0)));
    }

    @Test
    @DisplayName("Авторизация без поля login")
    public void authorizationCourierWithoutLogin() {
        AuthWithoutLogin authWithoutLogin = generator.getAuthWithoutLogin(courierCreating);
        log.info(AUTHORIZATION_COURIER, authWithoutLogin);

        Response response = clientCourier.authorizationWithoutLogin(authWithoutLogin);
        log.info(RESPONSE + "\n", response.body().asString());

        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and().body(MESSAGE_FIELD, equalTo(INSUFFICIENT_DATA));
    }

    @Test
    @DisplayName("Авторизация без поля password")
    public void authorizationCourierWithoutPassword() {
        AuthWithoutPassword authWithoutPassword = generator.getAuthWithoutPassword(courierCreating);
        log.info(AUTHORIZATION_COURIER, authWithoutPassword);

        Response response = clientCourier.authorizationWithoutPassword(authWithoutPassword);
        log.info(RESPONSE + "\n", response.body().asString());

        response.then().statusCode( HttpStatus.SC_BAD_REQUEST )
                .and().body( MESSAGE_FIELD,equalTo( INSUFFICIENT_DATA ));


}

    @Test
    @DisplayName("Авторизация под несуществующим пользователем")
    public void authorizationCourierNonExistent() {
        CourierCreating courierNotCreate = generator.getCourierCreating();
        AuthorizationCourier courierForAuthNotCreate = generator.getAuthorizationCourier(courierNotCreate);
        log.info(AUTHORIZATION_COURIER, courierNotCreate);

        Response response = clientCourier.login(courierForAuthNotCreate);
        log.info(RESPONSE + "\n", response.body().asString());

        response.then().statusCode( HttpStatus.SC_NOT_FOUND)
                .and().body(MESSAGE_FIELD, equalTo(ACCOUNT_NOT_FOUND));
    }
}