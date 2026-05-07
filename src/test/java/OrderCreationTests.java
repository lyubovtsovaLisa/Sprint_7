import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.OrderInfo;
import pojo.OrderTrack;
import java.util.List;

import static data.Constants.*;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;
@Epic("Управление заказами")
@Feature("Создание заказа")
@RunWith(Parameterized.class)
public class OrderCreationTests extends BaseAPITest {
    private OrderInfo orderInfo;
    private final List<String> color;
    private int trackId;

    public OrderCreationTests(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GRAY")},
                {List.of("BLACK","GRAY") },
                {List.of()}
        };
    }
    @Test
    @DisplayName("Заказ с различной конфигурацией цвета")
    @Description("Заказ оформлен - статус 201 и track в теле ответа")
    public void createOrderWithDifferentColors(){
        orderInfo = new OrderInfo(ORDER_FIRST_NAME,
                ORDER_LAST_NAME,
                ORDER_ADDRESS,
                ORDER_METRO_STATION,
                ORDER_PHONE,
                ORDER_RENT_TIME,
                ORDER_DELIVERY_DATE,
                ORDER_COMMENT,
                color);
        Response response = orderSteps.createOrder(orderInfo);
        response.then().statusCode(HTTP_CREATED)
                .body("track", notNullValue());
        trackId = response.body().as(OrderTrack.class).getTrack();
    }

    @After
    public void cleanUp(){
        orderSteps.cleanUpOrder(trackId);
    }

}
