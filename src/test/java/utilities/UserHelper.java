package utilities;

import org.json.JSONObject;

public class UserHelper {
    public static String getUserBody(
            int id, String username, String firstName, String lastName,
            String email, String password, String phone, int status) {

        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("username", username);
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("email", email);
        json.put("password", password);
        json.put("phone", phone);
        json.put("userStatus", status);

        return json.toString(); // Converts JSONObject to JSON string
    }
}
