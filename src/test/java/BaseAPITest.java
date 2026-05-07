import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.Before;
import steps.CourierSteps;
import steps.OrderSteps;

import static data.Constants.STAND_URL;

public class BaseAPITest {
    CourierSteps courierSteps;
    OrderSteps orderSteps;
    @Before
    public void setUp() {
        RestAssured.baseURI = STAND_URL;
        RestAssured.replaceFiltersWith(new AllureRestAssured());
        courierSteps = new CourierSteps();
        orderSteps = new OrderSteps();
    }
}
