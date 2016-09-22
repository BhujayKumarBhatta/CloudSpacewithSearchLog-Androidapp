package activities.android.theopentutorials.com.cloudspace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ActivityLogSearchResult extends AppCompatActivity {
    String job_id;
    String job_status;
    TextView messagetext1;
    TextView messagetext2;
    ListView result_srv_lst;
    String DjangoBaseUrl;
    String get_Job_URL;// = "http://192.168.52.176:8000/getsrvbysidapi/"+ sid+"?format=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_search_result);
        messagetext1 = (TextView) findViewById(R.id.vwid_jobstatus_msg1);
        messagetext2 = (TextView) findViewById(R.id.vwid_jobstatus_msg2);
        result_srv_lst = (ListView) findViewById(R.id.search_result_srv_list);
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

        try {
            String received_intent_search_job_ID = intent.getStringExtra("resposne");
            JSONObject jo1 = new JSONObject(received_intent_search_job_ID);
            job_id = jo1.getString("jobid");
            job_status = jo1.getString("jobstatus");
            getServerByJobID(job_id);

        } catch (Exception e) {
            //e.printStackTrace();
            try{
                String received_intent_search_ID = intent.getStringExtra("search_id");
                getServerBySearchID(received_intent_search_ID);
            }catch (Exception e1){e1.printStackTrace();}
        }
    }
//++++++++++++++++++++BY JOB ID++++++++++++++++++++++++++++++++++++
    private void getServerByJobID(String jid) {
        //String SearchdataPostURL =  "http://220.225.15.126:8081/search/";
        String get_Job_URL = DjangoBaseUrl+"/getjobapi/"+ jid+"?format=json";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest streq = new JsonObjectRequest(Request.Method.GET, get_Job_URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String response1=response.toString();
                        try{
                            if (response.getString("Job_status").equals("SUCCESS")){
                                JSONObject jo_result = new JSONObject(response.getString("result"));
                                JSONArray ja_result_servers = new JSONArray(jo_result.getString("resultservers"));
                                AdapterSearchResult_ServerList adapter1 = new AdapterSearchResult_ServerList(getApplicationContext(),
                                        ja_result_servers);

                                result_srv_lst.setAdapter(adapter1);
                                result_srv_lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Object o = result_srv_lst.getItemAtPosition(position);
                                        String row_data = o.toString();
                                        try {
                                            JSONObject jo = new JSONObject(row_data);
                                            //String tS[] = row_data.split(",");
                                            Toast.makeText(getApplicationContext(), "You have Selected: " + " " + jo.toString(), Toast.LENGTH_LONG).show();
                                            // TriggerServerProgram method to Check network , pass on the selected athlete name to a Async Class
                                            Intent gotologlinescreenIntent = new Intent(getApplicationContext(), ActivityServerLogLines.class);
                                            gotologlinescreenIntent.putExtra("server_id", jo.getString("id"));
                                            startActivity(gotologlinescreenIntent);
                                        }catch (Exception e){e.printStackTrace();}
                                    }
                                });
                            }
                            else {
                                messagetext1.setText(response.getString("result"));
                            }

                        }catch (Exception e){e.printStackTrace();}

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        messagetext1.setText(error.getMessage());
                        //String z= error.getMessage();
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

//============================================================by Search ID =========================
    private void getServerBySearchID(String sid) {
        //String SearchdataPostURL =  "http://220.225.15.126:8081/search/";
        String get_Job_URL =DjangoBaseUrl+"/getsrvbysidapi/"+ sid+"?format=json";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest streq = new JsonObjectRequest(Request.Method.GET, get_Job_URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String response1=response.toString();
                        try{
                            //JSONObject jo_result = new JSONObject(response.getString("result"));
                            JSONArray ja_result_servers = new JSONArray(response.getString("resultservers"));
                            AdapterSearchResult_ServerList adapter1 = new AdapterSearchResult_ServerList(getApplicationContext(),
                                        ja_result_servers);

                            result_srv_lst.setAdapter(adapter1);
                            result_srv_lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Object o = result_srv_lst.getItemAtPosition(position);
                                String row_data = o.toString();
                                try {
                                      JSONObject jo = new JSONObject(row_data);
                                      //String tS[] = row_data.split(",");
                                      Toast.makeText(getApplicationContext(), "You have Selected: " + " " + jo.toString(), Toast.LENGTH_LONG).show();
                                      // TriggerServerProgram method to Check network , pass on the selected athlete name to a Async Class
                                      Intent gotologlinescreenIntent = new Intent(getApplicationContext(), ActivityServerLogLines.class);
                                      gotologlinescreenIntent.putExtra("server_id", jo.getString("id"));
                                      startActivity(gotologlinescreenIntent);
                                        }catch (Exception e){e.printStackTrace();}
                                    }
                                });

                        }catch (Exception e){e.printStackTrace();}

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        messagetext1.setText(error.getMessage());
                        //String z= error.getMessage();
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