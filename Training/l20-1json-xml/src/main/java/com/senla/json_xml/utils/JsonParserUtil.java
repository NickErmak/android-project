package com.senla.json_xml.utils;

import com.senla.json_xml.App;
import com.senla.json_xml.R;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParserUtil {

    static App self = App.self;

    public enum Param {
        login, profile
    }

    public static enum Key {
        status, message, token, firstName, lastName, birthDate, notes
    }

    public static enum Status {
        ok, error
    }

    public static String getJsonValueByKey(String src, Key key) {
        String res = null;
        try {
            JSONObject jsonObject = new JSONObject(src);
            res = jsonObject.getString(key.name());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String getJsonUrl(Param param) {
        String format = self.getString(R.string.format_url);
        return String.format(format, param);
    }

    public static String getJsonLogin(String login, String password) {
        String format = self.getString(R.string.format_login);
        return String.format(format, login, password);
    }

    public static String getJsonProfile(String token) {
        String format = self.getString(R.string.format_profile);
        return String.format(format, token);
    }
}
