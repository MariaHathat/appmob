package com.example.appmobtp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends HttpActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!networkService.getNetworkReceiver().isConnected()) {
//                    Toast.makeText(Login_Activity.this, "Verify your Internet connection", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                send(LOGIN, params);
            }
        });
    }
    protected void ResponseReceived(String response, Map<String, String> params) {//code smell too long
        try {
            JSONObject jsonObject = new JSONObject(response);
            status = jsonObject.getString("status");
            if (status.equals("success")) {
                JSONObject userObject = jsonObject.getJSONObject("user_info");
                String family_name = userObject.getString("family_name");
                String first_name = userObject.getString("first_name");
                String email = userObject.getString("email");
                int age = userObject.getInt("age");
                String address = userObject.getString("address");
                // Save user information to shared preferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Login_Activity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("family_name", family_name);
                editor.putString("first_name", first_name);
                editor.putString("email", email);
                editor.putInt("age", age);
                editor.putString("address", address);
                editor.apply();
                // Start HomeActivity
                Intent intent = new Intent(Login_Activity.this, FoodMenuActivity.class);
                startActivity(intent);
                finish();
            } else {
                String errorMessage = jsonObject.getString("message");
                Toast.makeText(Login_Activity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getLastStatus() {
        return this.status;
    }
}