package com.example.appmobtp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp_Activity extends HttpActivity {

    private EditText firstnameEditText;
    private EditText familynameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText ageEditText;
    private EditText addressEditText;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstnameEditText = findViewById(R.id.firstname);
        familynameEditText = findViewById(R.id.familyname);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        ageEditText = findViewById(R.id.age);
        addressEditText = findViewById(R.id.address);
        signUpButton = findViewById(R.id.signup_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {//code smell too long
            @Override
            public void onClick(View view) {
                String firstname = firstnameEditText.getText().toString();
                String familyname = familynameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String age = ageEditText.getText().toString();
                String address = addressEditText.getText().toString();
//                    signUpButton.setText(R.string.Confirm);
                Map<String, String> params = new HashMap<>();
                params.put("first_name", firstname);
                params.put("family_name", familyname);
                params.put("email", email);
                params.put("password", password);
                params.put("age", age);
                params.put("address", address);
                send(SIGNUP, params);
            }
        });
    }

    protected void ResponseReceived(String response, Map<String, String> params) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if (status.equals("success")) {
                // Get user information
                String sessionToken = jsonObject.getString("session_token");
                String sessionId = jsonObject.getString("session_id");

                // Save user information to shared preferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SignUp_Activity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("family_name", params.get("familyname"));
                editor.putString("first_name", params.get("firstname"));
                editor.putString("email", params.get("email"));
                editor.putInt("age", Integer.parseInt(params.get("age")));
                editor.putString("address", params.get("address"));
                editor.putString("session_token", sessionToken);
                editor.putString("session_id", sessionId);
                editor.apply();

                // Start HomeActivity
                Intent intent = new Intent(SignUp_Activity.this, FoodMenuActivity.class);
                startActivity(intent);
                finish();
            } else {
                String errorMessage = jsonObject.getString("message");
                Toast.makeText(SignUp_Activity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}