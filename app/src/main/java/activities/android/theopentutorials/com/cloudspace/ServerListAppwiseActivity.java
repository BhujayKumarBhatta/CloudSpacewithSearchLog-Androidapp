package activities.android.theopentutorials.com.cloudspace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

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

public class ServerListAppwiseActivity extends AppCompatActivity {
    private TextView txtvw;
    private ListView serverListvw;
    private ArrayList<String> serverEnvGroupArraylist;
    private String get_base_url_srv=null;
    private String urlsrvlist=null;
    int swipeCounter_srv=0;int rnum_srv=7; // swipe counter = 1 not working when  table has only one row
    private String subdiv_name=null;
    private String app_id=null;
    //private HashMap<String,List<String>> serverChildArraylist ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_list_appwise);
        txtvw = (TextView)findViewById(R.id.SrvListtestingVW); // this view is only for testing
        serverListvw = (ListView)findViewById(R.id.serverListExpanded);

        //Prepare the URL===================================================================
        //Read subdiv name and application id from the selected item in previous page
        Intent intent = getIntent();
        String message = intent.getStringExtra("applistrowdata");
        String divname=intent.getStringExtra("divname_intent");
        String subdivname=intent.getStringExtra("suvdivname_intent");
        final String S1[] = message.split(",");
        app_id=S1[5];
        txtvw.setText(subdivname+S1[5]);// this line of for testing only

        //Read base url and number of row per screen settings  from setting file saved inside mobile
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
            get_base_url_srv = myarray[7];
            rnum_srv=Integer.parseInt(myarray[8]);
        }catch (Exception e){e.printStackTrace();
            get_base_url_srv="http://220.225.15.126:8000/cgi-bin/getServerList.py";}

        urlsrvlist =get_base_url_srv+"?division="+divname+"&subdivision="+subdivname+"&appid="+app_id+"&offset="+ swipeCounter_srv + "&rnum=" + rnum_srv;
        //End Prepare URL=======================================================================

        // Get data in the list , populate adapter and show in the list____________________________
        prepareListdata();
        // END Get data in the list , populate adapter and show in the list______________________
    }

    private  void prepareListdata(){
        serverEnvGroupArraylist=new ArrayList<String>();
        //serverChildArraylist =new HashMap<String, List<String>>();
        //serverEnvGroupArraylist.add("Prod"); serverEnvGroupArraylist.add("DR");
        //serverEnvGroupArraylist.add("QA");serverEnvGroupArraylist.add("DEV");
        //=========================Start List Creation by downloaidng json data from server====================
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jreq = new JsonArrayRequest( urlsrvlist,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jo = response.getJSONObject(i);
                                String host_name = jo.getString("hostName");
                                String host_ip = jo.getString("IP");
                                String host_cpu = jo.getString("cpu");
                                String host_ram = jo.getString("RAM");
                                String host_storage = jo.getString("storage");
                                String env_name = jo.getString("envname");
                                //String cpu_count = jo.getString("cpu");
                                //String storage_count = jo.getString("storage");
                                String serverdetail=host_name+","+host_ip+","+host_cpu+","+host_ram+
                                        ","+host_storage+","+env_name;
                                serverEnvGroupArraylist.add(serverdetail);
                                //txtvw1.setText(subapp_name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //List Binding starts Here=========================
                       /* ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                                R.layout.serverlist_child_row_meta,R.id.txtvWSrvName,serverEnvGroupArraylist);
                                */
                        AdapterServerList adapter =new AdapterServerList(getApplicationContext(),serverEnvGroupArraylist);
                        serverListvw.setAdapter(adapter);
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

/*
{"hostid":row[0],"assetID":row[1],"hostName":row[2],"IP":row[7],"NAT":row[8],"netZone":row[9],"cpu":str(row[13]),
                      "RAM":str(row[14]),"storage":str(row[19]),"storageType":str(row[20]),"contact":str(row[21]),"srno":str(row[37]),
                      "envname":str(row[39]),"sType":str(row[44]),"dchall":str(row[46])}
 */