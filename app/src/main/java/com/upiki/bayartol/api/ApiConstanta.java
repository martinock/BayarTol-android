package com.upiki.bayartol.api;

public class ApiConstanta {
    public static final String API = "https://us-central1-upiki-77460.cloudfunctions.net";

    // User
    public static String REGISTER = API + "/register";
    public static String LOGIN = API + "/login";
    public static String EDIT = API + "/edit";
    public static String GET_PROFILE = API + "/profile?uid=%1s";
    public static String GET_HISTORY = API + "/history";
    public static String GET_HISTORY_BY_DATE = API
        + "/history?uid=%1s&start_date=%2s&end_date=%3s&current=%4s&limit=%5s";
    public static String TRANSACTION = API + "/transaction";


}
