package courierCreating;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CourierCreating {
    protected String login;
    protected String password;
    protected String firstName;
}
