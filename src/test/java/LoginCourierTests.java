import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.CourierInfo;
import pojo.CourierLogin;

import static data.Constants.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
@Epic("Управление курьерами")
@Feature("Логин курьера в сисеме")
public class LoginCourierTests extends BaseAPITest {
    private CourierInfo courierInfo;
    @Before
    public void localSetUp() {
        courierInfo = new CourierInfo(LOGIN+ System.currentTimeMillis(), PASS, COURIER_NAME);
        courierSteps.createCourier(courierInfo);
    }


    @Test
    @DisplayName("Успешный вход курьера в систему")
    @Description("Успешный вход - статус 200 и id курьера в теле ответа")
    public void successfulLogin(){
        CourierLogin loginCourierInfo = new CourierLogin(courierInfo.getLogin(), courierInfo.getPassword());
        courierSteps.loginCourier(loginCourierInfo).then().statusCode(HTTP_OK).body("id", notNullValue());
    }
    @Test
    @DisplayName("Вход с неверным паролем")
    @Description("Войти не удалось - статус 404 и сообщение в теле ответа")
    public void invalidPasswordLogin(){
        CourierLogin loginCourierInfo = new CourierLogin(courierInfo.getLogin(), INVALID_PASS);
        courierSteps.loginCourier(loginCourierInfo).then().statusCode(HTTP_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Вход с несуществующей учетной записью")
    @Description("Войти не удалось - статус 404 и сообщение в теле ответа")
    public void nonExistingCourierLogin(){
        CourierLogin loginCourierInfo = new CourierLogin(INVALID_LOGIN, INVALID_PASS);
        courierSteps.loginCourier(loginCourierInfo).then().statusCode(HTTP_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Вход без логина")
    @Description("Войти не удалось - статус 400 и сообщение в теле ответа")
    public void loginWithoutLogin(){
        CourierLogin loginCourierInfo = new CourierLogin(null, PASS);
        courierSteps.loginCourier(loginCourierInfo).then().statusCode(HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Вход без пароля")
    @Description("Войти не удалось - статус 400 и сообщение в теле ответа")
    public void loginWithoutPass(){
        CourierLogin loginCourierInfo = new CourierLogin(courierInfo.getLogin(), null);
        courierSteps.loginCourier(loginCourierInfo).then().statusCode(HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }
    @After
    public void cleanUp() {
        if (courierInfo != null) {
            courierSteps.cleanUpCourier(courierInfo.getLogin(), courierInfo.getPassword());
        }
    }
}
