import client.courier.ClientCourier;
import courierCreating.CreateWithoutLogin;
import courierCreating.CreateWithoutPassword;
import generate.GenerateCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

@Slf4j
public class CreateCourierTest extends GeneralTest {
    private static final String MESSAGE_FIELD = "message";
    private static final String MESSAGE_OK = "ok";
    private static final String RE_CREATING = "Этот логин уже используется. Попробуйте другой.";
    private static final String COURIER_CREATE = "Создание курьера: {}";
    private static final String NOT_ALL_FIELDS = "Недостаточно данных для создания учетной записи";
    private static final String RESPONSE = "Получен ответ от сервера: {}";
    private final GenerateCourier generator = new GenerateCourier();
    private final ClientCourier clientCourier = new ClientCourier();
    ;
    @Test
    @DisplayName("Создание курьера со всеми обязательными полями")
    public void courierCreate() {
        courierCreating = generator.getCourierCreating();
        log.info( COURIER_CREATE, courierCreating.getLogin() );

        Response response = clientCourier.create( courierCreating );
        log.info( RESPONSE + "\n", response.body().asString() );

        response.then().assertThat().statusCode( HttpStatus.SC_CREATED )
                .and().body( MESSAGE_OK, equalTo( true ) );
    }

    @Test
    @DisplayName("Повторное создание курьера")
    public void courierCreateAgain() {
        courierCreating = generator.getCourierCreating();
        log.info( COURIER_CREATE, courierCreating.getLogin() );

        Response response = clientCourier.create( courierCreating );
        log.info( RESPONSE, response.body().asString() );
        log.info( "Повторное создание курьера с логином {}", courierCreating.getLogin() );
        Response conflictResponse = clientCourier.create( courierCreating );
        log.info( RESPONSE + "\n", conflictResponse.body().asString() );

        conflictResponse.then().statusCode( HttpStatus.SC_CONFLICT )
                .and().body( MESSAGE_FIELD, equalTo( RE_CREATING ) );
    }

    @Test
    @DisplayName("Создание курьера без поля firstName")
    public void courierCreateWithoutFirstname() {
        courierCreating = generator.getCourierCreating();
        log.info( COURIER_CREATE, courierCreating );

        Response response = clientCourier.create( courierCreating );
        log.info( RESPONSE + "\n", response.body().asString() );

        response.then().statusCode( HttpStatus.SC_CREATED )
                .and().body( MESSAGE_OK, equalTo( true ) );
    }

    @Test
    @DisplayName("Создание курьера без login")
    public void courierCreateWithoutLogin() {
        CreateWithoutLogin createWithoutLogin = generator.getCreateWithoutLogin();
        log.info( COURIER_CREATE, createWithoutLogin );

        Response response = clientCourier.createWithoutLogin( createWithoutLogin );
        log.info( RESPONSE, response.body().asString() );

        response.then().statusCode( HttpStatus.SC_BAD_REQUEST )
                .and().assertThat().body( MESSAGE_FIELD, equalTo( NOT_ALL_FIELDS ) );
    }


    @Test
    @DisplayName("Создание курьера без password")
    public void courierCreateWithoutPassword() {
        CreateWithoutPassword createWithoutPassword = generator.getCreateWithoutPassword();
        log.info( COURIER_CREATE, createWithoutPassword );

        Response response = clientCourier.createWithoutPassword( createWithoutPassword );
        log.info( RESPONSE, response.body().asString() );

        response.then().statusCode( HttpStatus.SC_BAD_REQUEST )
                .and().body( MESSAGE_FIELD, equalTo( NOT_ALL_FIELDS ) );
    }
}

