package com.example.appmobtp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public abstract class HttpActivity extends AppCompatActivity implements ConnectivityChangeListener{//code smell
    public String http = "http://192.168.9.88:80/";

    protected String LOGIN = "login.php";

    protected String SIGNUP = "singup.php";

    NetworkService networkService;
    boolean mBound = false;
    private NetworkChangeReceiver networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        networkReceiver = NetworkChangeReceiver.getInstance(this);

        registerReceiver(networkReceiver, filter);
    }

    @Override
    public void onConnectivityChange(boolean isConnected) {
        if (isConnected) {//code smell
            // do something when the conenctivity is restored
        } else {
            // do something when the connectivity is lost
        }
    }
    protected void send(String type, Map<String, String> params) {
         System.out.println("send started");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, http + type,
                    response -> ResponseReceived(response, params),
                    error -> Toast.makeText(HttpActivity.this, "Error: " + error.toString(), Toast
                            .LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(stringRequest);
//        }
    }

    protected abstract void ResponseReceived(String Response, Map<String, String> params);

    protected ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            NetworkService.LocalBinder binder
                    = (NetworkService.LocalBinder) service;
            networkService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, NetworkService.class);
        startService(intent);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    protected void onStop() {
        super.onStop();
        unbindService(connection);
        Intent intent = new Intent(this, NetworkService.class);
        stopService(intent);
    }


    @Override
    public void onDestroy() {
        if (networkReceiver != null) {
            unregisterReceiver(networkReceiver);
            networkReceiver = null;
        }
        super.onDestroy();
    }

}