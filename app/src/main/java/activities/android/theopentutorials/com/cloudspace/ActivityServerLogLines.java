package activities.android.theopentutorials.com.cloudspace;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ActivityServerLogLines extends AppCompatActivity {
    ListView loglines_List = null;
    String received_intent_server_id=null;
    String url_getlogapi=null;
    String DjangoBaseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_log_lines);
        loglines_List = (ListView)findViewById(R.id.vwid_search_result_srv_loglines);
        try {
            File traceFile = new File(((Context) this).getFilesDir(), "CSSettingsFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(traceFile));
            String S_array[] = new String[15];
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                S_array[i] = line;
                i++;             //sb.append(line);
            }
            reader.close();
            DjangoBaseUrl = S_array[13];

        }catch (Exception e){e.printStackTrace();
            DjangoBaseUrl = "http://220.225.15.126:8081";
        }
        Intent intent = getIntent();
        received_intent_server_id = (intent.getStringExtra("server_id"));
        url_getlogapi=DjangoBaseUrl+"/getlogapi/"+received_intent_server_id+"/?format=json";
        getLogbyServerID(url_getlogapi);
    }

    private void getLogbyServerID(String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest streq = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String response1 = response.toString();
                        try {
                            //JSONObject jo_result = new JSONObject(response.getString("srvlog"));
                            JSONArray ja_server_logs_lines = new JSONArray(response.getString("srvlog"));
                            AdapterSearchResult_LogLines adapter1 = new AdapterSearchResult_LogLines(getApplicationContext(),
                                    ja_server_logs_lines);
                            loglines_List.setAdapter(adapter1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //messagetext1.setText(error.getMessage());
                        String z= error.getMessage();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }


        };
        RetryPolicy retryPolicy = new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 25000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 5;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        };
        streq.setRetryPolicy(retryPolicy);
        queue.add(streq);
    }
}
