package activities.android.theopentutorials.com.cloudspace;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class LoginSearchLogDjangoActivity extends AppCompatActivity {

    private EditText loginDjangoIDView;
    private EditText loginDjangoPwdView;
    private TextView loginDjangoStatView;
    private ListView prev_search_list_view;
    private Button loginDjangoButton;
    private String loginDjangoid_text;
    private String loginDjangopwd_text;
    private String loginDjangoapiadddress1;
    private String DjangoBaseUrl;
    private String prevsearchlistURL;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_search_log_django);
        loginDjangoIDView = (EditText) findViewById(R.id.VwidDjangologinID);
        loginDjangoPwdView = (EditText) findViewById(R.id.VwidDjangologinPwd);
        loginDjangoStatView = (TextView) findViewById(R.id.VwidDjangologinMsg);
        loginDjangoButton = (Button) findViewById(R.id.VwidDjangologinButton);
        prev_search_list_view = (ListView) findViewById(R.id.vwid_prev_searh_id_list);
        //loginDjangoapiadddress1 = "http://220.225.15.126:8081/search/?format=json";
        //DjangoBaseUrl = "http://192.168.52.176:8000";
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


        loginDjangoapiadddress1 = DjangoBaseUrl + "/search/?format=json";
        prevsearchlistURL = DjangoBaseUrl + "/getprevsearchesapi/?format=json";
        getPrevSearchHistory();

        loginDjangoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginDjangoid_text = loginDjangoIDView.getText().toString().trim();
                loginDjangopwd_text = loginDjangoPwdView.getText().toString().trim();

                if (loginDjangoid_text.equals("")) {
                    loginDjangoIDView.setError("please enter a valid email address");
                }
                if (loginDjangopwd_text.equals("")) {
                    loginDjangoPwdView.setError("please enter password");
                }

                if ((loginDjangoid_text.equals("")) || (loginDjangopwd_text.equals(""))) {
                    loginDjangoStatView.setError("Invalid Credentials");
                } else {
                    attemptDjangoLogin(loginDjangoid_text, loginDjangopwd_text);
                }

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void attemptDjangoLogin(final String userid, final String password) {
        //loginapiadddress1 = "http://14.142.104.139:8000/api/getLNregDB.py";
        //loginapiadddress1 = "http://220.225.15.126:8000/cgi-bin/getLNregDB.py";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest streq = new StringRequest(Request.Method.GET, loginDjangoapiadddress1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loginDjangoStatView.setText(response.toString());
                        Intent SearchLogintent = new Intent(getApplicationContext(), ActivitySearchLogScreen.class);
                        SearchLogintent.putExtra("srvlst", response.toString());
                        SearchLogintent.putExtra("LoginID", loginDjangoid_text.toString());
                        SearchLogintent.putExtra("Password", loginDjangopwd_text.toString());
                        startActivity(SearchLogintent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loginDjangoStatView.setText(error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s", userid, password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
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


    private void getPrevSearchHistory() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest streq = new JsonObjectRequest(Request.Method.GET, prevsearchlistURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //String response1 = response.toString();
                        try {
                            //JSONObject jo_result = new JSONObject(response.getString("srvlog"));
                            JSONArray ja_prev_searches = new JSONArray(response.getString("prev_searches"));
                            Adapter_Prev_Searches adapter1 = new Adapter_Prev_Searches(getApplicationContext(),
                                    ja_prev_searches);
                            prev_search_list_view.setAdapter(adapter1);
                            prev_search_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Object o_sid = prev_search_list_view.getItemAtPosition(position);
                                    String row_data_sid = o_sid.toString();
                                    try {
                                        JSONObject jo_sid = new JSONObject(row_data_sid);
                                        //String tS[] = row_data.split(",");
                                        Toast.makeText(getApplicationContext(), "You have Selected: " + " " + jo_sid.toString(), Toast.LENGTH_LONG).show();
                                        // TriggerServerProgram method to Check network , pass on the selected athlete name to a Async Class
                                        Intent gotologlinescreenIntent = new Intent(getApplicationContext(), ActivityLogSearchResult.class);
                                        gotologlinescreenIntent.putExtra("search_id", jo_sid.getString("id"));
                                        startActivity(gotologlinescreenIntent);
                                    }catch (Exception e){e.printStackTrace();}
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loginDjangoStatView.setText(error.getMessage());
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "LoginSearchLogDjango Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://activities.android.theopentutorials.com.cloudspace/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "LoginSearchLogDjango Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://activities.android.theopentutorials.com.cloudspace/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

