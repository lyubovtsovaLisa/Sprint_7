package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.CourierId;
import pojo.CourierInfo;
import pojo.CourierLogin;
import static data.Constants.*;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class CourierSteps {
    @Step("Создание курьера")
    public Response createCourier(CourierInfo courierInfo){
        return given()
                .header("Content-type", "application/json")
                .body(courierInfo)
                .post(CREATE_COURIER_PATH);
    }
    @Step("Логин курьера в системе")
    public Response loginCourier(CourierLogin loginCourierInfo){
        return given()
                .header("Content-type", "application/json")
                .body(loginCourierInfo)
                .post(LOGIN_COURIER_PATH);
    }
    @Step("Удалить курьера")
    public Response deleteCourier(CourierId courierId){
        return given().header("Content-type", "application/json")
                .body(courierId)
                .delete(DELETE_COURIER_PATH, courierId.getId());
    }


    @Step("Очистка данных: удаление курьера из базы")
    public void cleanUpCourier(String login, String password){
        CourierLogin loginForCleanUp = new CourierLogin(login,password);
        Response response = loginCourier(loginForCleanUp);
        if (response.statusCode() == HTTP_OK) {
            CourierId courierId = response.body().as(CourierId.class);
            deleteCourier(courierId);
        }
    }

}
