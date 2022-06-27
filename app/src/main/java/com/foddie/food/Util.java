package com.foddie.food;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.regex.Pattern;

class Util {
    public static final String EmailAddress_PATTERN = "^(([^<>()\\[\\]\\\\.,;:\\s@\"`~!#$%^&*=|?{}\\']+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"`~!#$%^&*=|?{}\\']+)*))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";


    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String USER_USER_NAME = "user_user_name";
    public static final String USER_EMAIL_NAME = "user_email_id";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_TYPE = "user_type";

    public static final String RESTRO_ID = "restro_id";
    public static final String RESTRO_NAME = "restro_name";
    public static final String RESTRO_SMALL_DESC = "restro_small_desc";
    public static final String RESTRO_DESC = "restro_desc";
    public static final String RESTRO_CREATE_BY = "restro_create_by";
    public static final String RESTRO_CREATE_DATE = "restro_create_date";

    public static final String REVIEW_ID = "review_id";
    public static final String REVIEW_COMMENT = "review_comment";
    public static final String REVIEW_RATING = "review_rating";
    public static final String REVIEW_GIVEN_BY = "review_given_by";

    public static final String REVIEW_GIVEN_BY_EMAIL = "review_given_by_email";
    public static final String REVIEW_DATE = "review_date";
    public static final String USER_NAME_ID = "USER_NAME_ID";


    public static boolean isValidEmail(String emailAddress) {
        if (TextUtils.isEmpty(emailAddress))
            return false;
        else if (!Pattern.matches(EmailAddress_PATTERN, emailAddress)) {
            return false;
        } else
            return true;
    }

    public static void showErrorMessage(Context context, String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
