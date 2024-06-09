package generate;

import courierAuthorization.AuthWithoutLogin;
import courierAuthorization.AuthWithoutPassword;
import courierAuthorization.AuthorizationCourier;
import courierCreating.CourierCreating;
import courierCreating.CreateWithoutLogin;
import courierCreating.CreateWithoutPassword;
import org.apache.commons.lang3.RandomStringUtils;

public class GenerateCourier {
    protected final String password = "password";
    protected final String firstName = "name";

    public CourierCreating getCourierCreating() {
        return CourierCreating.builder()
                .login( RandomStringUtils.randomAlphanumeric(10))
                .password(password)
                .firstName(firstName)
                .build();
    }
    public CreateWithoutLogin getCreateWithoutLogin() {

        return new CreateWithoutLogin(password);
    }


    public CreateWithoutPassword getCreateWithoutPassword() {
        return new CreateWithoutPassword(RandomStringUtils.randomAlphanumeric(10));
    }

    public AuthorizationCourier getAuthorizationCourier(CourierCreating courierCreating) {
        return AuthorizationCourier.builder()
                .login(courierCreating.getLogin())
                .password(courierCreating.getPassword())
                .build();
    }

    public AuthWithoutLogin getAuthWithoutLogin(CourierCreating courierCreating) {
        return new AuthWithoutLogin(courierCreating);
    }

    public AuthWithoutPassword getAuthWithoutPassword(CourierCreating courierCreating) {
        return new AuthWithoutPassword(courierCreating);
    }

}

