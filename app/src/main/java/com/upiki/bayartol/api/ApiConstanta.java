package com.upiki.bayartol.api;

public class ApiConstanta {
    public static final String API_URL = "https://us-central1-upiki-77460.cloudfunctions.net";

    // User
    public static String REGISTER = API_URL + "/register";
    public static String LOGIN = API_URL + "/login";
    public static String EDIT = API_URL + "/edit";
    public static String GET_PROFILE = API_URL + "/profile?uid=%1s&role=user";
    public static String GET_HISTORY = API_URL + "/history";
    public static String GET_HISTORY_BY_DATE = API_URL
        + "/history?uid=%1s&start_date=%1s&end_date=%1s&current=%1s&limit=%1s";
    public static String TRANSACTION = API_URL + "/transaction";

    // Organization
    public static String ADD_MEMBER_URL = API_URL + "/addMember";
    public static String GET_ORGANIZATION_URL = API_URL + "/organization?uid=%1s";
    public static String POST_REGISTER_ORGANIZATION = API_URL + "/organization";


}
