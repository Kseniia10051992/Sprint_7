package courierAuthorization;

import courierCreating.CourierCreating;

public class AuthWithoutPassword {
    protected String login;

    public AuthWithoutPassword (CourierCreating courierCreating) {

        this.login = courierCreating.getLogin();
    }
}
