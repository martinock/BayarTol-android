package com.upiki.bayartol.api.ApiClass;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by taufic on 7/30/2017.
 */

public class Organization implements Serializable {
    public String date;
    public ArrayList<User> member;
    public String name;
    public String error; // for no organization necessary
}
