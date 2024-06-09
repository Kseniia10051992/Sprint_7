package courierAuthorization;

import courierCreating.CourierCreating;

public class AuthWithoutLogin {

    protected String password;

    public AuthWithoutLogin (CourierCreating courierCreating) {

        this.password = courierCreating.getPassword();
    }
}
