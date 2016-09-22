package activities.android.theopentutorials.com.cloudspace;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class DivListNappCountActivity extends AppCompatActivity {
    ArrayList divListNappCount = new ArrayList();
    private ListView listViewDiv;
    private TextView textView;
    private  String Myurl=null;
    private String get_url_app=null;
    private ProgressBar progressBar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int swipeCounter=1;int rnum=7;
    //String Myurl = "http://220.225.15.126:8000/cgi-bin/MSSQLCheck.py?division=ALL&offset=1&rnum=7";
    //String Myurl = "http://220.225.15.126:8000/cgi-bin/MSSQLCheck.py?division=ALL&offset=" +
    //swipeCounter+"&rnum="+rnum;
    //String Myurl = "http://192.168.1.10:8000/cgi-bin/MSSQLCheck.py?division=ALL&offset=" +
            //swipeCounter+"&rnum="+rnum;
    SettingPgViewActivity settingPgViewActivity = new SettingPgViewActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_div_list_napp_count);


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
            get_url_app = myarray[2];
            rnum=Integer.parseInt(myarray[5]);
        }catch (Exception e){e.printStackTrace();
            get_url_app="http://220.225.15.126:8000/cgi-bin/MSSQLCheck.py";}
        Myurl = get_url_app+"?division=ALL&offset=" +swipeCounter+"&rnum="+rnum;
        enableHttpResponseCache();
        listViewDiv = (ListView)findViewById(R.id.dinnapplistview1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(3);
        //progressBar.setVisibility( View.VISIBLE);
        //progressBar.setProgress(0);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Toast.makeText(getApplicationContext(), swipeCounter+" times Swipe detected ", Toast.LENGTH_LONG).show();
                swipeCounter=swipeCounter+5;
                /*Myurl = "http://192.168.1.10:8000/cgi-bin/MSSQLCheck.py?division=ALL&offset=" +
                        swipeCounter+"&rnum="+rnum;*/
                Myurl = get_url_app+"?division=ALL&offset=" +swipeCounter+"&rnum="+rnum;
                //Toast.makeText(getApplicationContext(), Myurl, Toast.LENGTH_LONG).show();
                try{
                    new asynCbuildListfmHTTP().execute(Myurl);}catch (Exception e){e.printStackTrace();}
                    mSwipeRefreshLayout.setRefreshing(false);

            }
        });
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try{
            new asynCbuildListfmHTTP().execute(Myurl);}catch (Exception e){e.printStackTrace();}
        } else {
            //textView.setText("No network connection available.");
        }

    }

    private class   asynCbuildListfmHTTP extends AsyncTask<String,Integer,ArrayList> {//Async<param,progress,result>

        //int progress_status;
        @Override
        protected ArrayList doInBackground(String... params) {
            try {

                for (int c = 0;c<=3;c++ ){
                Thread.sleep(300);
                    publishProgress(c);
                }
                divListNappCount=buildListfmHTTP(params[0]);

                return divListNappCount;
            } catch (Exception e) {
                e.printStackTrace();
                return divListNappCount;
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility( View.VISIBLE);
            progressBar.bringToFront();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate( progress[0]);
            progressBar.setProgress( progress[0]);
                    }

        @Override
        protected void onPostExecute (ArrayList result){
            try{
            DivnAppCountListAdapter adapter =new DivnAppCountListAdapter(getApplicationContext(),
                        divListNappCount);
                /*ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),
                        R.layout.div_list_napp_row_meta,R.id.divappcountDiv,divListNappCount); */
            listViewDiv.setAdapter(adapter);
                listViewDiv.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View view,
                                                    int position, long id) {
                                // TODO Auto-generated method stub
                                Object o = listViewDiv.getItemAtPosition(position);
                                String rowdata = o.toString();
                                Toast.makeText(getApplicationContext(), "You have Selected: " + " " + rowdata, Toast.LENGTH_LONG).show();
                                // TriggerServerProgram method to Check network , pass on the selected athlete name to a Async Class
                                String tS[]= rowdata.split(",");
                                Intent divtoappIntent = new Intent(getApplicationContext(),DivtoAppActivity.class);
                                divtoappIntent.putExtra("row_data",rowdata);
                                //intentAppLevel2.putExtra("postNgetUrl",putNgeturl);
                                startActivity(divtoappIntent);
                            }
                        }
                );
                progressBar.setVisibility( View.GONE);
                if (divListNappCount.size()==0){
                     swipeCounter=1;
                }
            }catch (Exception e){e.printStackTrace();
            mSwipeRefreshLayout.setRefreshing(false);}
        }

    }
        private ArrayList buildListfmHTTP(String url) {
            ArrayList divListNappCountPrivate = new ArrayList();
            HTTPDownload httpDownload = new HTTPDownload();
            try {
                String httpReturn = httpDownload.HTTPtoServer(url);
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray(httpReturn);
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String div = jsonObject.getString("divName"); String subdiv=jsonObject.getString("subDivName");
                    String divNsub=div +","+ subdiv;
                    String appcnt=jsonObject.getString("appCount"); String subapcnt=jsonObject.getString("subAppCount");
                    String hstcnt = jsonObject.getString("hostCount");String cpu=jsonObject.getString("cpu");
                    String storagae =jsonObject.getString("storage"); String vmcnt=jsonObject.getString("vmCount");
                    String countForaDivision = divNsub +","+appcnt+" apps"+","+subapcnt+" apps"+","+hstcnt+" servers"
                            +","+cpu+" cpu"+","+storagae+" GB storage";
                    divListNappCountPrivate.add(countForaDivision.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return divListNappCountPrivate;
        }

    private void enableHttpResponseCache() {
        try {
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            File httpCacheDir = new File(getCacheDir(), "http");
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (Exception ex) {
            //Log.d(TAG, "HTTP response cache is unavailable.");
        }
    }

}

