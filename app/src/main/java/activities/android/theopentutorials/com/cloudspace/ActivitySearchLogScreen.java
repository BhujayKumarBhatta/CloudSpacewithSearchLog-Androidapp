package activities.android.theopentutorials.com.cloudspace;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivitySearchLogScreen extends AppCompatActivity {
    String SearchLoginID;
    String SearchPassword;
    String Server_ID;
    String Server_Name;
    String Server_IP;
    String SSH_Port;
    String Server_OS;
    ListView search_server_list;
    EditText Start_Date;EditText End_Date; EditText Start_Hour;EditText End_Hour;EditText Start_Minute;EditText End_Minute;
    EditText Start_Second;EditText End_Second;EditText Start_Month;
    Button search_button;
    TextView msg_job_id;
    String response1;
    String DjangoBaseUrl;
    String SearchdataPostURL;// =  "http://192.168.52.176:8000/search/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_search_log_screen);
        search_server_list = (ListView) findViewById(R.id.search_server_list);
        Start_Date= (EditText)findViewById(R.id.vwidStartDate);
        End_Date=(EditText)findViewById(R.id.vwidEndDate);
        Start_Hour=(EditText)findViewById(R.id.vwidStartHour);
        End_Hour = (EditText)findViewById(R.id.vwidEndHour);
        Start_Minute = (EditText)findViewById(R.id.vwidStartMinute);
        End_Minute = (EditText)findViewById(R.id.vwidEndMinute);
        Start_Second = (EditText)findViewById(R.id.vwidStartSecond);
        End_Second = (EditText)findViewById(R.id.vwidEndSecond);
        Start_Month = (EditText)findViewById(R.id.vwidStartMonth);
        search_button= (Button)findViewById(R.id.findSelected);
        msg_job_id = (TextView)findViewById(R.id.vwidJob_ID_Msg) ;
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
        SearchdataPostURL =  DjangoBaseUrl+"/search/";
        Intent intent = getIntent();
        String received_intent_loginresult1 = intent.getStringExtra("srvlst");
        SearchLoginID = intent.getStringExtra("LoginID");
        SearchPassword = intent.getStringExtra("Password");

        ArrayList srvlistArray = new ArrayList();

        try {
            JSONArray ja = new JSONArray(received_intent_loginresult1);
            JSONObject jobj = new JSONObject();
            for (int i = 0; i < ja.length(); i++) {
                jobj = ja.getJSONObject(i);
                Server_ID = jobj.getString("id");
                Server_Name = jobj.getString("name");
                Server_IP = jobj.getString("ip");
                SSH_Port = jobj.getString("sshport");
                Server_OS = jobj.getString("os");
                String srv_details = Server_ID + "," + Server_Name + "," + Server_IP + "," + SSH_Port + "," + Server_OS;
                srvlistArray.add(srv_details);

            }

        } catch (Exception e) {
        }

        AdapterDjangoServerList adapter = new AdapterDjangoServerList(getApplicationContext(), srvlistArray);
        search_server_list.setAdapter(adapter);
        search_server_list.setItemsCanFocus(false);
        // we want multiple clicks
        search_server_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        // search_server_list.setOnItemClickListener(new CheckBoxClick());

        //final Map mMap = new HashMap();
        //final List<Map> chkboxlist = new ArrayList();
        //final JSONObject jo = new JSONObject();

        final JSONArray ja = new JSONArray();
        search_server_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @TargetApi(19)
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.Checkbox1);
                cb.setChecked(!cb.isChecked());
                //if (cb.isChecked()) {
                    String flag = null;
                    Object rowObj = search_server_list.getItemAtPosition(position);
                    String S[] = rowObj.toString().split(",");
                    try {
                        JSONObject jo = new JSONObject();
                        JSONObject existing_jo = new JSONObject();
                        jo.put("id", Integer.parseInt(S[0]));
                        jo.put("name", S[1]);
                        jo.put("ip", S[2]);
                        jo.put("sshport", Integer.parseInt(S[3]));
                        jo.put("os", Integer.parseInt(S[4]));
                        //if (cb.isChecked()) {
                        if (ja.length()== 0){ja.put(jo);}
                            for (int i=0;i<ja.length();i++){
                                existing_jo= ja.getJSONObject(i);
                                String ej=existing_jo.getString("id");
                                String nj=jo.getString("id");
                                if ( ej.equals(nj)){
                                     flag= "Yes";
                                    if (!cb.isChecked()){
                                        ja.remove(i);}
                                     }
                            }
                        if ((flag==null)&&(cb.isChecked())) {
                            ja.put(jo);
                            flag=null;}
                       //}
                        //Toast.makeText(getBaseContext(), rowObj.toString(), Toast.LENGTH_LONG).show();
                        //Toast.makeText(getBaseContext(), ja.toString(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {e.printStackTrace();
                    }
                //}
            }

        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    Map jod= new HashMap();
                    jod.put("start_date",Integer.parseInt(Start_Date.getText().toString()));
                    jod.put("end_date",Integer.parseInt(End_Date.getText().toString()));
                    jod.put("start_hour",Integer.parseInt(Start_Hour.getText().toString()));
                    jod.put("end_hour",Integer.parseInt(End_Hour.getText().toString()));
                    jod.put("start_minute",Integer.parseInt(Start_Minute.getText().toString()));
                    jod.put("end_minute",Integer.parseInt(End_Minute.getText().toString()));
                    jod.put("start_second",Integer.parseInt(Start_Second.getText().toString()));
                    jod.put("end_second",Integer.parseInt(End_Second.getText().toString()));
                    jod.put("start_month",(Start_Month.getText().toString()));
                    jod.put("srvlst",ja);
                    //JSONObject jod1 = new JSONObject(jod);
                    //Toast.makeText(getBaseContext(), ja.toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getBaseContext(), jod.toString(), Toast.LENGTH_LONG).show();
                    PostInputForSearch(jod);
                }catch (Exception e){e.printStackTrace();}
            }

        });
    }

    private void PostInputForSearch(Map j){
        //String SearchdataPostURL =  "http://220.225.15.126:8081/search/";
        //String SearchdataPostURL =  "http://192.168.52.176:8000/search/";
                RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest streq = new JsonObjectRequest(Request.Method.POST, SearchdataPostURL,new JSONObject(j),
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    response1=response.toString();
                    try{
                        String z= "Job Has been Sucessufully Submitted with job id : -"
                                +response.getString("jobid");
                        msg_job_id.setText(z);
                    }catch (Exception e){e.printStackTrace();}
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    msg_job_id.setText(error.getMessage());
                    //String z= error.getMessage();
                }
            }) {
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> params = new HashMap<String, String>();
            String creds = String.format("%s:%s",SearchLoginID,SearchPassword);
            String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
            params.put("Authorization", auth);
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
    public void gotoGetServerbyJobid(View view) {
        Intent job_id_intent = new Intent(getApplicationContext(), ActivityLogSearchResult.class);
        job_id_intent.putExtra("resposne", response1);
        startActivity(job_id_intent);
    }

}
