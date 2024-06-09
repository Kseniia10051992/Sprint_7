package courierAuthorization;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AuthorizationCourier {
    protected String login;
    protected String password;
}
