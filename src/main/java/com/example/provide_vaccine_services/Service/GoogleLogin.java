package com.example.provide_vaccine_services.Service;

import com.example.provide_vaccine_services.dao.model.Users;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import java.io.IOException;

public class GoogleLogin {
    public static String getToken(String code) throws ClientProtocolException, IOException {
        String response = Request.Post(Iconstant.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(
                        Form.form()
                                .add("client_id", Iconstant.GOOGLE_CLIENT_ID)
                                .add("client_secret", Iconstant.GOOGLE_CLIENT_SECRET)
                                .add("redirect_uri", Iconstant.GOOGLE_REDIRECT_URI)
                                .add("code", code)
                                .add("grant_type", Iconstant.GOOGLE_GRANT_TYPE)
                                .build()
                )
                .execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;

    }

    public static Users getUserInfo(final String accessToken) throws ClientProtocolException, IOException {

        String link = Iconstant.GOOGLE_LINK_GET_USER_INFO + accessToken;

        String response = Request.Get(link).execute().returnContent().asString();

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

        Users user = new Users();
        user.setFullname(jsonObject.has("name") ? jsonObject.get("name").getAsString() : null);
        user.setEmail(jsonObject.has("email") ? jsonObject.get("email").getAsString() : null);
        user.setGender(jsonObject.has("gender") ? jsonObject.get("gender").getAsString() : "unknown");
        user.setIdentification(jsonObject.has("sub") ? jsonObject.get("sub").getAsString() : null);
        user.setDateOfBirth(null); // Google không trả về ngày sinh, cần cập nhật thủ công
        user.setAddress(null); // Google không cung cấp địa chỉ trực tiếp
        user.setPhone(jsonObject.has("phone") ? jsonObject.get("phone").getAsString() : null);
        user.setPassword(null); // Không lấy password từ Google OAuth
        user.setRole(0); // Mặc định 0, có thể cập nhật theo logic ứng dụng

        return user;

    }
}
