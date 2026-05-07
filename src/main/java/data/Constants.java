package data;

public class Constants {
    public static final String STAND_URL = "https://qa-scooter.praktikum-services.ru/";

    public static final String LOGIN = "testLogin";
    public static final String PASS = "testPass";
    public static final String COURIER_NAME = "testName";
    public static final String INVALID_PASS = "wrongPass";
    public static final String INVALID_LOGIN = "wrongLogin";

    public static final String ORDER_FIRST_NAME = "clientName";
    public static final String  ORDER_LAST_NAME="clientLastName";
    public static final String ORDER_ADDRESS="clientAddress";
    public static final int ORDER_METRO_STATION=4;
    public static final String ORDER_PHONE="+70001110000";
    public static final int ORDER_RENT_TIME =5;
    public static final String ORDER_DELIVERY_DATE="2026-05-03";
    public static final String ORDER_COMMENT="defaultComment";

    public static final String CREATE_COURIER_PATH="/api/v1/courier";
    public static final String LOGIN_COURIER_PATH="/api/v1/courier/login";
    public static final String DELETE_COURIER_PATH="/api/v1/courier/{id}";
    public static final String ORDERS_PATH="/api/v1/orders";
    public static final String CANCEL_ORDER_PATH="/api/v1/orders/cancel";

}
