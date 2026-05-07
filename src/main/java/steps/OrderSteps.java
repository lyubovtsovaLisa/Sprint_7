package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.OrderInfo;
import static data.Constants.CANCEL_ORDER_PATH;
import static data.Constants.ORDERS_PATH;
import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Создание заказа")
    public Response createOrder(OrderInfo orderInfo){
        return given()
                .header("Content-type", "application/json")
                .body(orderInfo)
                .post(ORDERS_PATH);
    }

    @Step("Получение списка заказов")
    public Response getOrderList() {
        return given()
                .header("Content-type", "application/json")
                .get(ORDERS_PATH);
    }

    @Step("Очиска данных: удаление созданного заказа")
    public void cleanUpOrder(int trackId){
        if (trackId != 0) {
            given()
                    .header("Content-type", "application/json")
                    .queryParam("track", trackId)
                    .put(CANCEL_ORDER_PATH);
        }
    }
}
