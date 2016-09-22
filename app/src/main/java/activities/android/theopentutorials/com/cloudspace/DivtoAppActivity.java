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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class DivtoAppActivity extends AppCompatActivity {
    private TextView divtoapptxtVW1;
    private ListView applistvw;
    private String get_base_url_app=null;
    private String urlVolley=null;
    int swipeCounter=0;int rnum=7;
    private String div_name=null;
    private String subdiv_name=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divto_app);
        //divtoapptxtVW1=(TextView)findViewById(R.id.divtoappMaintestText);
        //divtoapptxtVW1.setText("SUCCESS");
        applistvw=(ListView)findViewById(R.id.applist);
        final ArrayList applistArray= new ArrayList();
        //applistArray.add("Kiran");
        //applistArray.add("CT");

        Intent intent = getIntent();
        String message = intent.getStringExtra("row_data");
        final String S1[] = message.split(",");
        div_name=S1[0];
        subdiv_name=S1[1];
        try {
            File traceFile = new File(((Context) this).getFilesDir(), "CSSettingsFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(traceFile));
            String myarray[] = new String[20];
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                myarray[i] = line;
                i++;             //sb.append(line);
            }
            reader.close();
            get_base_url_app = myarray[4];
            rnum=Integer.parseInt(myarray[6]);
        }catch (Exception e){e.printStackTrace();
            get_base_url_app="http://220.225.15.126:8000/cgi-bin/getsubAppListNkpi.py";}

        urlVolley =get_base_url_app+"?division="+div_name+"&subdivision="+subdiv_name+"&offset="+ swipeCounter + "&rnum=" + rnum;

        //=========================Start List Creation by downloaidng json data from server====================
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jreq = new JsonArrayRequest(urlVolley,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jo = response.getJSONObject(i);
                                String subdiv_name = jo.getString("subDivname");
                                String subapp_name = jo.getString("subAppName");
                                String app_name = jo.getString("AppName");
                                String app_id = jo.getString("AppID");
                                String host_count = jo.getString("hostCount");
                                String vm_count = jo.getString("vmCount");
                                String cpu_count = jo.getString("cpu");
                                String storage_count = jo.getString("storage");
                                String subAppdetail=subapp_name+","+app_name+","+host_count+","+cpu_count+
                                        ","+storage_count+","+app_id+","+vm_count+","+subdiv_name;
                                applistArray.add(subAppdetail);
                                //txtvw1.setText(subapp_name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //List Binding starts Here=========================
                        //ArrayAdapter adapter = new ArrayAdapter(this,R.layout.app_list_group_rows_meta,R.id.txtvWSubAppName,applistArray);
                        AdapterSubappList adapter = new AdapterSubappList(getApplicationContext(),applistArray);
                        applistvw.setAdapter(adapter);
                        applistvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Object rowObj=applistvw.getItemAtPosition(position);
                                Toast.makeText(getBaseContext(),rowObj.toString(),Toast.LENGTH_LONG).show();
                                Intent serverListintent = new Intent(getApplicationContext(),ServerListAppwiseActivity.class);
                                serverListintent.putExtra("applistrowdata",rowObj.toString());
                                serverListintent.putExtra("divname_intent",div_name);
                                serverListintent.putExtra("suvdivname_intent",subdiv_name);
                                startActivity(serverListintent);
                            }
                        });

                        //List Binding ends here

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(jreq);
        //==============END List Creation by downloaidng json data from server=================



    }
}
