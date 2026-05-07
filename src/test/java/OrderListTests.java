import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
@Epic("Управление заказами")
@Feature("Получение списка заказов")
public class OrderListTests extends BaseAPITest {

    @Test
    @DisplayName("Получение списка  без параметров фильтрации")
    @Description("Список получен - статус 200 и список заказов в теле ответа в теле ответа")
    public void getOrderListTest() {
        orderSteps.getOrderList()
                .then()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue()) // Проверяем, что поле orders есть в ответе
                .body("orders.size()", not(0)); // Опционально: проверяем, что список не пустой
    }
}
