package com.example.rightprice;

import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class JSONAsyncTask extends AsyncTask<String, Void, String> {


    public interface JSONAsyncTaskListener{
        void onPreExecuteConcluded() throws JSONException;
        void onPostExecuteConcluded(String result);
    }

    private JSONAsyncTaskListener mListener;

    public JSONAsyncTask(){
        this.mListener = null;
    }


    final public void setJSONAsyncListener(JSONAsyncTaskListener listener){
        this.mListener = listener;
    }


    @Override
    final protected String doInBackground(String... url) {

        JSONObject obj = new JSONObject();

        try {
            obj.put("email","johnathan@ucsd.com");
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
            public void onResponse(JSONObject response){
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
        vehicleReq = new JsonObjectRequest(Request.Method.GET,url,obj,onRes,onErr) {
            /**
             * Passing some request headers*
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", token);
                headers.put("Device-id:", "801087ee-88a2-4ff7-9067-2e090007ffb6");
                headers.put("App-Version","3.0.5");

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
                locH+= "\"latitude\":"+point.getLatitude()+",";
                // "longitude":-117.240903,
                locH+= "\"longitude\":"+point.getLongitude()+",";
                // "altitude":0,
                locH+= "\"altitude\":"+ 0 + ",";
                // "accuracy":100,
                locH+= "\"accuracy\":"+ 100 + ",";
                // "speed":-1,
                locH+= "\"speed\":"+ -1 + ",";
                // "heading":-1}
                locH+= "\"heading\":"+ -1;
                locH+= "}";

                //{"latitude":32.880277,"longitude":-117.237552,"altitude":0,"accuracy":100,"speed":-1,"heading":-1}
                //{"latitude":32.888532,"longitude":-117.240903,"altitude":0,"accuracy":100,"speed":-1,"heading":-1}

                System.out.println("Here is location: " + locH);

                headers.put("Location",locH);
                System.out.println("Headers for getBirds: ");
                System.out.println(headers);
                return headers;
            }
        };
    }

    public JsonObjectRequest getInitReq() {
        return initReq;
    }

        return null;
    }

    @Override
    final protected void onPreExecute(){
        //set up vehicle requests


        if(mListener != null) {
            try {
                mListener.onPreExecuteConcluded();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPostExecute(String result){

        //output bird vehicles

        if(mListener != null)
            mListener.onPostExecuteConcluded(result);

    }
}
