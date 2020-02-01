package com.intellilabs.zerosnapscanner;

/**
 * Created by uvionics on 28,January,2020
 */
public class NetworkUtils {

    public static final String BASE_URL = "https://zerosnap.store/idsapi/";
    public static final int NETWORK_READ_TIME_OUT = 30;
    public static final int NETWORK_CONNECT_TIME_OUT = 30;
    public static final int NETWORK_WRITE_TIME_OUT = 30;
    public static final int RESPONSE_OK = 200;
    public static final String SUBSCRIPTIONS = "subscriptions.php";
    public static final String ADD_SUSCRIPTION = "payment.php";
    public static final String CHECK_SUSCRIPTION = "payment.php";
    public static final String APPLICATION_DETAILS = "clients.php";
    public static final String VERIFY_SUSCRIPTION = "verifypayment.php";
    public static final String GET_SUBSCRIPTION = "subscription.php";
    public static final String CANCEL_SUBSCRIPTION = "payment.php";

    public static final String NETWORK_AUTHORIZATION = "Access-Token";
    public static final String NETWORK_ZEROSNAP_AUTHORIZATION = "X-Zerosnap-Access-Token";
    public static final String NETWORK_LICENCE_KEY = "Licence-Key";
}
