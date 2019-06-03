package com.example.rightprice;
import android.location.Location;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * THERE MAY BE AN ISSUE WITH SPIN SCOOTERS.... CHECK THIS TO SEE HOW BATTERY STORED
 */

public class Spin {
    public List<Vehicle> getVehicles() {
        return spins;
    }

    private List<Vehicle> spins;
    private JsonObjectRequest vehicleReq;
    private JsonObjectRequest initReq;
    private String token;

    public void generateInitReq(Response.Listener<JSONObject> onRes) throws JSONException {
        String url ="https://web.spin.pm/api/v1/auth_tokens";
        JSONObject obj = new JSONObject();
        JSONObject innerObj = new JSONObject();
        innerObj.put("mobileType","andorid");
        innerObj.put("uid","3fbdb6d9-199f-4038-9c10-b9f85228ac9a");
        obj.put("device",innerObj);
        obj.put("grantType","device");
        //{"device":{"mobileType":"ios","uid":"3fbdb6d9-199f-4038-9c10-b9f85228ac9a"},"grantType":"device"}

        Response.ErrorListener onErr = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error Request when INIT  SPIN");
                //bad request or something

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
                return headers;
            }
        };
    }


    public void generateVehicles(JSONObject response) throws JSONException {
        System.out.println("I got the spins ...");
        String id;
        String type;
        String rate;
        double lat;
        double lng;
        int bat;
        spins = new ArrayList<Vehicle>();
        JSONArray items = response.getJSONArray("vehicles");
        for(int i=0;i<items.length();++i){
            JSONObject current = (JSONObject) items.get(i);
            lat = current.getDouble("lat");
            lng = current.getDouble("lng");
            type = current.getString("vehicle_type");
            id = current.getString("last4");
            if(type.equals("scooter")) {
                bat = current.getInt("batt_percentage");
                rate = "$1 to start, $0.15 / 1 min";
            }
            else{
                bat = -1;
                rate = "$1 for 30 mins";
            }
            Vehicle veh = new Vehicle("spin",id,bat,lat,lng,rate);
            veh.setType(type);
            spins.add(veh);
        }

        for(int i=0;i<spins.size();++i){
            System.out.println(spins.get(i));
        }
    }

    //https://web.spin.pm/api/v3/vehicles?lng=-117.237552&lat=32.880277&distance=&mode=
    public void generateVehicleReq(final Location loc,Response.Listener<JSONObject> onRes) {
        String url = "https://web.spin.pm/api/v3/vehicles?";
        url += "lng=" + loc.getLongitude();
        url += "&lat=" +loc.getLatitude();
        url += "&distance=&mode=";

        JSONObject obj = new JSONObject();
        Response.ErrorListener onErr = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error " +
                        "on GET request from SPIN");
                System.out.println(error.toString());
                //bad request or something

            }
        };


        System.out.println("Setting token...."+ token);
        vehicleReq = new JsonObjectRequest(Request.Method.GET, url, obj, onRes, onErr){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", token);
                return headers;
            }
        };
    }


    public Spin(){
        //token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyVW5pcXVlS2V5IjoiMmU5ZWZhNTY4MTNiOTllZmQ2ODRlM2EwNzZjMjQyZjkiLCJyZWZlcnJhbENvZGUiOm51bGwsImlzQXBwbGVQYXlEZWZhdWx0IjpmYWxzZSwiaXNBZG1pbiI6ZmFsc2UsImlzQ2hhcmdlciI6ZmFsc2UsImlzQ29ycG9yYXRlIjpmYWxzZSwiYXV0b1JlbG9hZCI6ZmFsc2UsImNyZWRpdEJhbGFuY2UiOjAsInRvdGFsVHJpcENvdW50IjowLCJzcGluVW5saW1pdGVkIjpmYWxzZSwic3BpblVubGltaXRlZE5leHRCaWxsaW5nQ3ljbGUiOm51bGwsInNwaW5VbmxpbWl0ZWRNZW1iZXJzaGlwIjpudWxsLCJpc1F1YWxpZmllZEZvclJpZGUiOmZhbHNlLCJyYXRlRGlzY291bnRQZXJjZW50YWdlIjowLCJ0eXBlIjoiZGV2aWNlIiwiZXhwIjoxNTU5NDYwNzUzfQ.jSHcF0BjPuk80JArBPMm1vSrVHAPkDkWkGOP-0lRG7k";
        // generateVehicleReq(loc);
        //generateInitReq();


    }

    public JsonObjectRequest getVehicleReq() {
        return vehicleReq;
    }
    public void setToken(String token){
        this.token = token;
    }

    public JsonObjectRequest getInitReq() {
        return initReq;
    }
}
