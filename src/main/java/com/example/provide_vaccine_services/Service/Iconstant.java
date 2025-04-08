package com.example.provide_vaccine_services.Service;

public class Iconstant {

    public static final String GOOGLE_CLIENT_ID = "737894268831-9ab6vfskfnv7pfoh1i7nnagpmmau67oi.apps.googleusercontent.com";

    public static final String GOOGLE_CLIENT_SECRET = "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";

    public static final String GOOGLE_REDIRECT_URI = "http://localhost:8080/provide_vaccine_services_war/login?provider=google";

    public static final String GOOGLE_GRANT_TYPE = "authorization_code";

    public static final String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";

    public static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";

    public static final String FACEBOOK_CLIENT_ID = "1610596812975790";



    // zalo
    public static final String FACEBOOK_CLIENT_SECRET =
            "718e0f67c798d7d919b1bb603ace4fae";

    public static final String FACEBOOK_REDIRECT_URI =
            "http://localhost:8080/provide_vaccine_services_war/login?provider=facebook";

    public static final String FACEBOOK_LINK_GET_TOKEN =
            "https://graph.facebook.com/v19.0/oauth/access_token";

    public static final String FACEBOOK_LINK_GET_USER_INFO =
            "https://graph.facebook.com/me?fields=id,name,email,picture&access_token=";

}
