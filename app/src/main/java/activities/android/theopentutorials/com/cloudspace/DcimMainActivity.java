package activities.android.theopentutorials.com.cloudspace;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DcimMainActivity extends AppCompatActivity {
    ListView listviewDChall;
    TextView textViewDCHALL;
    String myURL1 = "http://14.142.104.138/api/v1/cabinet";
    ArrayList dcHallList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcim_main);

        textViewDCHALL = (TextView)findViewById(R.id.testviewdcim);
        try {
            textViewDCHALL.setText("Success");
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                textViewDCHALL.setText("Network OK");
                new AsyncJSONtoDBTask().execute(myURL1);
            } else {
                textViewDCHALL.setText("Network Problem");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class AsyncJSONtoDBTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                return dcimDataDownloadToDb(params[0]);  //
                            } catch (Exception e) {
                return "";
            }
        }
        @Override
        protected void onPostExecute(String result) {

            try {
                ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.dcim_main_item_meta, dcHallList);
                listviewDChall= (ListView) findViewById(R.id.listviewHall);
                listviewDChall.setAdapter(adapter);
            }catch (Exception e){
                e.printStackTrace();}
        }
    }
    private String dcimDataDownloadToDb(String urlString) throws  IOException {
        DBSecondClass obj=new DBSecondClass(this);
        HTTPwithBasicAuth httpServerWNR = new HTTPwithBasicAuth();
        String returnFromDCIMServer = httpServerWNR.HTTPtoServer(urlString);
        try {
            JSONObject jsonObj = new JSONObject(returnFromDCIMServer);
            JSONArray cabinetArray = jsonObj.getJSONArray("cabinet");
            obj.addCabinet(cabinetArray);
        }catch (Exception e){e.printStackTrace();}
        dcHallList=obj.getDcHalls();

        return null;
    }
}
