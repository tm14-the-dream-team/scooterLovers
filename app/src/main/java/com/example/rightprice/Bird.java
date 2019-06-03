package com.example.rightprice;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;


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

public class Bird extends com.example.rightprice.Map {


    private List<Vehicle> birds;
    private JsonObjectRequest initReq;
    private String token;
    private String id;
    private String expiration;
    private String email;
    private Location loc = new Location("GaryIsMyDad");

    public interface BirdListener{
        public void onDataLoaded(JsonObjectRequest result);
    }

    private BirdListener listener;

    public Bird(){
        this.listener = null;
    }

    public void setBirdListener(BirdListener listener){
        this.listener = listener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        email = "johnathan@ucsd.com";
        id = "eee4913d-078e-4f13-8bd6-87d3245a3fb0";
        //token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBVVRIIiwidXNlcl9pZCI6ImRiN2IwMGIzLWE2NWUtNDQyMy1iZDIzLWZlOGVkZTk3NWNmMyIsImRldmljZV9pZCI6IjQ3OTIwMzZkLWVkNGEtNDQ5OC05ZGJjLTViMjlmZjNmMWVmNSIsImV4cCI6MTU5MDgwMTkxMH0.hmhXizqW64omSvjdbhabdMcJBPECdzq2MVtObov2drs";
        token = "Bird eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBVVRIIiwidXNlcl9pZCI6ImRiN2IwMGIzLWE2NWUtNDQyMy1iZDIzLWZlOGVkZTk3NWNmMyIsImRldmljZV9pZCI6IjQ3OTIwMzZkLWVkNGEtNDQ5OC05ZGJjLTViMjlmZjNmMWVmNSIsImV4cCI6MTU5MDgwMTkxMH0.hmhXizqW64omSvjdbhabdMcJBPECdzq2MVtObov2drs";
        loc.setLatitude(32.880277);
        loc.setLongitude(-117.237552);


        String url = "https://api.bird.co/bird/nearby?";

        url += "latitude=" + loc.getLatitude();
        url += "&longitude=" + loc.getLongitude();
        url += "&radius=" + 1000;

        new JSONAsyncTask().execute(url, email);


    }

    public class JSONAsyncTask extends AsyncTask<String, Void, JsonObjectRequest> {




        @Override
        protected JsonObjectRequest doInBackground(String... strings) {
            JSONObject obj = new JSONObject();
            String url = strings[0];
            String email = strings[1];

            try {
                obj.put("email", email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Response.ErrorListener onErr = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error Request when constructing HARDCODE Bird()");
                    //bad request or something

                }
            };

            Response.Listener<JSONObject> onRes = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("GET RECIEVED FROM BIRD");
                    System.out.println(response.toString());
                    try {
                        generateVehicles(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            System.out.println("Here is the request for GET BIRD:");
            System.out.println(obj);
            vehicleReq = new JsonObjectRequest(Request.Method.GET, url, obj, onRes, onErr) {
                /**
                 * Passing some request headers*
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("Authorization", token);
                    headers.put("Device-id:", "801087ee-88a2-4ff7-9067-2e090007ffb6");
                    headers.put("App-Version", "3.0.5");

                    String locH = "{";
                /*locH+= "\"latitude\":"+String.format("%.6f",point.getLatitude())+",";
                locH+= "\"longitude\":"+String.format("%.6f",point.getLongitude())+",";
                locH+= "\"latitude\":"+point.getLatitude()+",";
                locH+= "\"longitude\":"+point.getLongitude()+",";
                locH+= "\"altitude\":"+ 500 + ",";
                locH+= "\"accuracy\":"+ 100 + ",";
                locH+= "\"speed\":"+ -1 + ",";
                locH+= "\"heading\":"+ -1;
*/
                    //{"latitude":32.888532,
                    locH += "\"latitude\":" + loc.getLatitude() + ",";
                    // "longitude":-117.240903,
                    locH += "\"longitude\":" + loc.getLongitude() + ",";
                    // "altitude":0,
                    locH += "\"altitude\":" + 0 + ",";
                    // "accuracy":100,
                    locH += "\"accuracy\":" + 100 + ",";
                    // "speed":-1,
                    locH += "\"speed\":" + -1 + ",";
                    // "heading":-1}
                    locH += "\"heading\":" + -1;
                    locH += "}";

                    //{"latitude":32.880277,"longitude":-117.237552,"altitude":0,"accuracy":100,"speed":-1,"heading":-1}
                    //{"latitude":32.888532,"longitude":-117.240903,"altitude":0,"accuracy":100,"speed":-1,"heading":-1}

                    System.out.println("Here is location: " + locH);

                    headers.put("Location", locH);
                    System.out.println("Headers for getBirds: ");
                    System.out.println(headers);
                    return headers;
                }
            };

            return vehicleReq;
        }

        @Override
        protected void onPostExecute(JsonObjectRequest vehicleReq) {

            if(listener != null)
                listener.onDataLoaded(vehicleReq);

        }

    }



    public List<Vehicle> getBirds() {
        return birds;
    }

    public JsonObjectRequest getVehicleReq() {
        return vehicleReq;
    }

    private JsonObjectRequest vehicleReq;

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }

    public String getExpiration() {
        return expiration;
    }


    public void setLocation(Location location){
        loc = location;
    }








    public JsonObjectRequest getInitReq() {
        return initReq;
    }
    //idk what this is but i commented it out


    private void generateVehicles(JSONObject resp) throws JSONException {
        birds = new ArrayList<Vehicle>();
        JSONArray items = resp.getJSONArray("birds");
        double lat = 0;
        double lng = 0;
        String id = "";
        int bat = 0;
        System.out.println(items.toString());
        System.out.println("parsing.....");
        for(int i=0;i<items.length();++i){
            JSONObject current = (JSONObject) items.get(i);




            //Vehicle(String vendor,String id, int battery, double lat, double lng, double startPrice, double minutePrice)
            //birds.add(new Vehicle("bird",items.get(i).getString("id");
            if(current.getString("captive").equals("false")) {


                JSONObject loc = current.getJSONObject("location");




                lat = loc.getDouble("latitude");
                lng = loc.getDouble("longitude");
                bat = Integer.parseInt(current.getString("battery_level"));
                id = current.getString("id");

                Vehicle veh = new Vehicle("bird",id,bat,lat,lng,"$1 to unlock $0.27 / 1 min");
                veh.setType("scooter");
                System.out.print(veh);

                birds.add(veh);
            }
        }
        for(int i=0;i<birds.size();++i){
            System.out.println(birds.get(i));
        }

    }






}