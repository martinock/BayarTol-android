package com.upiki.bayartol.api;

public class ApiConstanta {
    public static final String API = "https://us-central1-upiki-77460.cloudfunctions.net";

    public static String REGISTER = API + "/register";
    public static String LOGIN = API + "/login";
    public static String GET_PROFILE = API + "/profile?uid=%1s";
    public static String GET_HISTORY = API + "/history";
    public static String TRANSACTION = API + "/transaction";
}
