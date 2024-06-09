package client.courier;

import client.Client;
import courierAuthorization.AuthWithoutLogin;
import courierAuthorization.AuthWithoutPassword;
import courierAuthorization.AuthorizationCourier;
import courierCreating.CourierCreating;
import courierCreating.CreateWithoutLogin;
import courierCreating.CreateWithoutPassword;
import io.qameta.allure.Step;
import io.restassured.response.Response;


public class ClientCourier extends Client {
    private final static String COURIER = "/courier";

    private static final String LOGIN = "/login";

    @Step("Создать курьера")
    public Response create(CourierCreating courierCreating) {
        return spec()
                .body(courierCreating)
                .when()
                .post(COURIER);
    }
    @Step("Создать курьера без login")
    public Response createWithoutLogin(CreateWithoutLogin createWithoutLogin) {
        return spec()
                .body(createWithoutLogin)
                .when()
                .post(COURIER);
    }

    @Step("Создать курьера без password")
    public Response createWithoutPassword(CreateWithoutPassword createWithoutPassword) {
        return spec()
                .body(createWithoutPassword)
                .when()
                .post(COURIER);
    }

    @Step("Авторизация курьера")
    public Response login(AuthorizationCourier authorizationCourier) {
        return spec()
                .body(authorizationCourier)
                .when()
                .post(COURIER + LOGIN);
    }

    @Step("Авторизация курьера без login")
    public Response authorizationWithoutLogin(AuthWithoutLogin authWithoutLogin) {
        return spec()
                .body(authWithoutLogin)
                .when()
                .post(COURIER + LOGIN);
    }

    @Step("Авторизация курьера без password")
    public Response authorizationWithoutPassword(AuthWithoutPassword authWithoutPassword) {
        return spec()
                .body(authWithoutPassword)
                .when()
                .post(COURIER + LOGIN);
    }

    @Step("Удалить курьера")
    public Response deleteCourier(Integer courierId) {
        return spec()
                .delete(COURIER + String.format("/%d", courierId));
    }
}
