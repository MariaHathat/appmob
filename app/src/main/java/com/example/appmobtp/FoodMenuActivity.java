package com.example.appmobtp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodMenuActivity extends HttpActivity {

    protected RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String firstName = preferences.getString("first_name", "");
//        String familyName = preferences.getString("family_name", "");
//        TextView welcomeTextView = findViewById(R.id.welcome_textview);
//        String hello = getResources().getString(R.string.hello);
//        welcomeTextView.setText(hello +" " + firstName + " " + familyName);
        Button logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().clear().apply();
                Intent intent = new Intent(FoodMenuActivity.this, Welcome_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Map<String, String> params = new HashMap<>();
        send("foodmenu.php",params);
    }
    @Override
    protected void ResponseReceived(String response, Map<String, String> params) {
        System.out.println("response recieved started");
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray itemsArray = jsonObject.getJSONArray("menu_list");
            System.out.println("Json array fetched");
            List<Item> items = new ArrayList<>();
            for(int i=0; i<itemsArray.length(); i++) {
                JSONObject itemObject = itemsArray.getJSONObject(i);
                String itemId = itemObject.getString("id");
                String itemName = itemObject.getString("name");
                String itemDescription = itemObject.getString("description");
                String itemImage = http + itemObject.getString("image");
                String itemPrice = itemObject.getString("price");
                items.add(new Item(itemName, itemDescription, itemImage, itemPrice));
                System.out.println(items);
            }
            ItemAdapter adapter = new ItemAdapter(items);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}