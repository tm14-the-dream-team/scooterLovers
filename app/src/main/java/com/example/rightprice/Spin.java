package com.example.rightprice;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Spin {
    private List<Vehicle> spins;
    private JsonObjectRequest vehicleReq;
    private JsonObjectRequest initReq;
    private String token;

    private void generateVehicles(JSONObject response){
        System.out.println("IM SPINNING...");

    }


    private void generateVehicleReq(Location loc) {
        String url = "https://web.spin.pm/api/v3/vehicles?";
        url += "lng=" + loc.getLongitude();
        url += "&lat=" +loc.getLatitude();
        url += "&distance=&mode=";

        JSONObject obj = new JSONObject();
        Response.ErrorListener onErr = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error " +
                        "on GET request from bird");
                System.out.println(error.toString());
                //bad request or something

            }
        };
        Response.Listener<JSONObject> onRes = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("GET RECIEVED FROM SPIN");
                System.out.println(response.toString());
                    generateVehicles(response);
            }
        };

        vehicleReq = new JsonObjectRequest(Request.Method.GET, url, obj, onRes, onErr){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", token);
                return headers;
            }
        };
    }


    public Spin(Location loc) {
            token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyVW5pcXVlS2V5IjoiMjY3MDhhNWE0ODhjM2IzOTdmM2JiMjJmODNhOGNhZTgiLCJyZWZlcnJhbENvZGUiOm51bGwsImlzQXBwbGVQYXlEZWZhdWx0IjpmYWxzZSwiaXNBZG1pbiI6ZmFsc2UsImlzQ2hhcmdlciI6ZmFsc2UsImlzQ29ycG9yYXRlIjpmYWxzZSwiYXV0b1JlbG9hZCI6ZmFsc2UsImNyZWRpdEJhbGFuY2UiOjAsInRvdGFsVHJpcENvdW50IjowLCJzcGluVW5saW1pdGVkIjpmYWxzZSwic3BpblVubGltaXRlZE5leHRCaWxsaW5nQ3ljbGUiOm51bGwsInNwaW5VbmxpbWl0ZWRNZW1iZXJzaGlwIjpudWxsLCJpc1F1YWxpZmllZEZvclJpZGUiOmZhbHNlLCJyYXRlRGlzY291bnRQZXJjZW50YWdlIjowLCJ0eXBlIjoiZGV2aWNlIiwiZXhwIjoxNTU5NDU2MzI2fQ.TXVtaCCuBaYaqLNp3xii5YzP-rTMWE7KU9CnDA_Pf_8";
            generateVehicleReq(loc);


        }

    public JsonObjectRequest getVehicleReq() {
        return vehicleReq;
    }
}
