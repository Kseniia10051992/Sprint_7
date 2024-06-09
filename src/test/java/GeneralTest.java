import courierCreating.CourierCreating;
import org.junit.After;

public class GeneralTest {
    public CourierCreating courierCreating;
    private final UtilMethods util = new UtilMethods();

    @After
    public void deleteCourier() {
        if (courierCreating != null) {
            util.deleteCourier(courierCreating);
        }
    }
}