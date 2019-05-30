package com.example.rightprice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Bird extends Service{
    private boolean login = false;
    private String email = "";
    private double ratePerMin = 0.3;
    private double rateUnlock = 1;

    public void loginToggle(){
        login = !login;
    }

    public void loginToggle(boolean b){
        login = b;
    }

    public void setEmail(String str){
        email = str;
    }

    public void setrpm(double rate){
        ratePerMin = rate;
    }

    public JsonObjectRequest getInitReq() {
        return initReq;
    }

    //@androidx.annotation.Nullable
    private JsonObjectRequest initReq;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public Bird(String email) throws JSONException {
        //first init with bird
        String url = "https://api.bird.co/user/login";
        JSONObject obj = new JSONObject();
        obj.put("email",email);

        Response.ErrorListener onErr = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Request Error");
                //bad request or something

            }
        };
        Response.Listener<JSONObject> onRes = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Request success");
                System.out.println(response.toString());
                System.out.println("$$$$$$$$$$$$$$$$$");

                //This is what we do when we recieve info

            }
        };
        initReq = new JsonObjectRequest(Request.Method.POST,url,obj,onRes,onErr) {
            /**
             * Passing some request headers*
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Device-id:", "000bca56-fb54-4704-9abe-60efc4d9993c");
                headers.put("Platform","android");
                return headers;
            }
        };
    }
}
