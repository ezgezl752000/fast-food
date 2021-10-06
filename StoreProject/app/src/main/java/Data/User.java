package Data;

import android.net.Uri;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User{
    String name;
    String email;
    String phone;
    String uri;
    public Map<String, Boolean> user = new HashMap<>();

    public User(){
    }

    public User(String name, String email, String phone,  String uri) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUrl(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("email", email);
        result.put("phone", phone);
        result.put("uri", uri);
        return result;
    }
}
