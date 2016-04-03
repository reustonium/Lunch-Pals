package com.reustonium.lunchpals.data.remote;

import com.reustonium.lunchpals.BuildConfig;

/**
 * Created by Andrew on 3/18/2016.
 */
public class Util {

    /**
     * Constants related to locations in Firebase, such as the name of the node
     * where user lists are stored (ie "userLists")
     */
    public static final String FIREBASE_LOCATION_USERS = "users";

    /**
     * Constants for bundles, extras and shared preferences keys
     */
    public static final String KEY_PROVIDER = "PROVIDER";
    public static final String KEY_ENCODED_EMAIL = "ENCODED_EMAIL";
    public static final String KEY_SIGNUP_EMAIL = "SIGNUP_EMAIL";

    /**
     * Constants for Firebase object properties
     */
    public static final String FIREBASE_PROPERTY_EMAIL = "email";
    public static final String FIREBASE_PROPERTY_USER_HAS_LOGGED_IN_WITH_PASSWORD =
            "hasLoggedInWithPassword";

    /**
     * Constants for Firebase URL
     */
    public static final String FIREBASE_URL = BuildConfig.FIREBASE_ROOT_URL;
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;

    public static final String error_message_email_issue =
            "There was an error with your email; please check that you entered it correctly.";
    public static final String error_message_failed_sign_in_no_network =
            "There was a problem with the network connection." +
            "\nAre you sure you\'re connected to the internet?";

    public static final String error_message_default = "There was a problem logging in.";
    public static final String error_message_wrong_password =
            "There was a problem with your password; please check that you entered it correctly.";
}
