package activities.android.theopentutorials.com.cloudspace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class AppListNsrvCountActivity extends AppCompatActivity {
    private TextView kirantxtvw;
  // private ListView listViewapp;
    private String get_base_url_app=null;
    private String urlVolley=null;
    //private ProgressBar progressBar;
   // private SwipeRefreshLayout mSwipeRefreshLayout;
    final ArrayList subappList = new ArrayList();

    int swipeCounter=1;int rnum=7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list_nsrv_count);
       //listViewapp =(ListView)findViewById(R.id.ListvwSubappMain) ;
        kirantxtvw=(TextView)findViewById(R.id.kirantxtvw) ;
        kirantxtvw.setText("SUCCESS");
        Intent intent = getIntent();
        String message = intent.getStringExtra("row_data");
        final String S1[] = message.split(",");
        try {
            File traceFile = new File(((Context) this).getFilesDir(), "CSSettingsFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(traceFile));
            String myarray[] = new String[10];
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

        //String    url = "http://192.168.1.3:8000/cgi-bin/getsubAppListNkpi.py?subdivision=ITD&offset=1&rnum=2";
        //String urlVolley ="http://192.168.1.3:8000/cgi-bin/getsubAppListNkpi.py?subdivision=ITD&offset=1&rnum=12";
        //String urlVolley ="http://192.168.1.3:8000/cgi-bin/getsubAppListNkpi.py?subdivision="+S1[1]+"&offset=1&rnum=12";
        //urlVolley =get_base_url_app+"?subdivision="+S1[1]+"&offset=1&rnum=12";
        urlVolley =get_base_url_app+"?subdivision="+S1[1]+"&offset="+ swipeCounter + "&rnum=" + rnum;
        //+++++++++++++++URL Formation end s here ++++++++++++++++++++++
        /*//Swipe Refresh Starts Heres!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);//layout name to be changed
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Toast.makeText(getApplicationContext(), swipeCounter+" times Swipe detected ", Toast.LENGTH_LONG).show();
                swipeCounter = swipeCounter + 5;
                *//*Myurl = "http://192.168.1.10:8000/cgi-bin/MSSQLCheck.py?division=ALL&offset=" +
                        swipeCounter+"&rnum="+rnum;*//*
                urlVolley = get_base_url_app + "?subdivision="+S1[1]+"&offset=" + swipeCounter + "&rnum=" + rnum;
                //Toast.makeText(getApplicationContext(), Myurl, Toast.LENGTH_LONG).show();
                try {
                    //call the volley fucntion
                    mSwipeRefreshLayout.setRefreshing(false);
                }catch (Exception e){e.printStackTrace();}
            }
        });
*/
        //subappList.add("Kiran1");
        //Swipe Refresh Ends here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //AdapterSubappList adapter =new AdapterSubappList(getApplicationContext(), subappList);
        //ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.app_list_group_rows_meta,R.id.txtvWSubAppName,subappList);
        //listViewapp.setAdapter(adapter);
        // On Item Click Starts[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[

        /*listViewapp.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        // TODO Auto-generated method stub
                        Object o = listViewapp.getItemAtPosition(position);
                        String rowdata = o.toString();
                        Toast.makeText(getApplicationContext(), "You have Selected: " + " " + rowdata, Toast.LENGTH_LONG).show();
                        // TriggerServerProgram method to Check network , pass on the selected athlete name to a Async Class
                        Intent intentApptoSrv1 = new Intent(getApplicationContext(),ServerListAppwiseActivity.class);
                        intentApptoSrv1.putExtra("row_data",rowdata);
                        //intentAppLevel2.putExtra("postNgetUrl",putNgeturl);
                        startActivity(intentApptoSrv1);
                    }
                }
        );
*/
        //On Item Click End ehere [[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[





        //AdapterSubappList adapter =new AdapterSubappList(getApplicationContext(), subappList);

        //ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.app_list_group_rows_meta,R.id.txtvWSubAppName,subappList);


    }
}

/*
//=========================Start List Creation by downloaidng json data from server====================
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jreq = new JsonArrayRequest(urlVolley,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jo = response.getJSONObject(i);
                                String subapp_name = jo.getString("subAppName");
                                String app_name = jo.getString("AppName");
                                String app_id = jo.getString("AppID");
                                String host_count = jo.getString("hostCount");
                                String vm_count = jo.getString("vmCount");
                                String cpu_count = jo.getString("cpu");
                                String storage_count = jo.getString("storage");
                                String subAppdetail=subapp_name+","+app_name+","+host_count+","+cpu_count+
                                        ","+storage_count+","+app_id+","+vm_count;
                                subappList.add(subAppdetail);
                                //txtvw1.setText(subapp_name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(jreq);
        //==============END List Creation by downloaidng json data from server=================
 */