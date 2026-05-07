import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import pojo.CourierInfo;

import static data.Constants.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.is;

@Epic("Управление курьерами")
@Feature("Создание курьера")
public class CreateCourierTests extends BaseAPITest {
    private CourierInfo courierInfo;
    @Test
    @DisplayName("Успешное создание курьера со всеми полями")
    @Description("Курьера можно создать, передав все поля - статус 201 и ok: true в теле ответа")
    public void courierCreatedWithAllFields() {
        courierInfo = new CourierInfo(LOGIN+ System.currentTimeMillis(), PASS, COURIER_NAME);
        courierSteps.createCourier(courierInfo)
                .then().assertThat().statusCode(HTTP_CREATED).body("ok", is(true));
    }
    @Test
    @DisplayName("Успешное создание курьера с обязательными полями")
    @Description("Курьера можно создать, передав обязательные поля - статус 201 и ok: true в теле ответа")
    public void courierCreatedWithRequiredFields() {
        courierInfo = new CourierInfo(LOGIN+ System.currentTimeMillis(), PASS, null);
        courierSteps.createCourier(courierInfo)
                .then().assertThat().statusCode(HTTP_CREATED).body("ok", is(true));
    }

    @Test
    @DisplayName("Создание дубликата курьера")
    @Description("Нельзя создать двух одинаковых курьеров - статус 409 и появление сообщения в теле ответа")
    public void cannotCreateDuplicate(){
        courierInfo = new CourierInfo(LOGIN+ System.currentTimeMillis(), PASS, COURIER_NAME);
        courierSteps.createCourier(courierInfo);
        courierSteps.createCourier(courierInfo).then().statusCode(HTTP_CONFLICT)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Нельзя создать курьера без логина - статус 400 и появление сообщения в теле ответа")
    public void cannotCreateWithoutLogin(){
        courierInfo = new CourierInfo(null,PASS, COURIER_NAME);
        courierSteps.createCourier(courierInfo).then().statusCode(HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Нельзя создать курьера без пароля - статус 400 и появление сообщения в теле ответа")
    public void cannotCreateWithoutPassword(){
        courierInfo = new CourierInfo(LOGIN+ System.currentTimeMillis(),null, COURIER_NAME);
        courierSteps.createCourier(courierInfo).then().statusCode(HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void cleanUp() {
        if (courierInfo != null) {
            courierSteps.cleanUpCourier(courierInfo.getLogin(), courierInfo.getPassword());
        }
    }
}
