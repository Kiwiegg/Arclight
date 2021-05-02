package android.example.passwordmanager;

import androidx.annotation.NonNull;

public class Information {

    @NonNull
    private String purpose, username, password;

    public Information() {
        this.purpose = "";
        this.username = "";
        this.password = "";
    }
    public Information(String purpose, String username, String password) {
        this.purpose = purpose;
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
