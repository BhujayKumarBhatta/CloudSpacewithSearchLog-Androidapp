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
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RackListingActivity extends AppCompatActivity {
    TextView textView;
    ListView listview;
    String myURL1 = "http://14.142.104.138/api/v1/cabinet";
    ArrayList<HashMap<String, String>> cabinetList = new ArrayList<HashMap<String, String>>(500);
    List<String> DCNameList = new ArrayList<>();
    String UniquieDCName=null;
    //Context context = PullJSONActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rack_listing);
        textView = (TextView) findViewById(R.id.testview1);
        listview = (ListView) findViewById(R.id.listview1);
        try {
            textView.setText("Success");
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                textView.setText("Network OK");
                new AsyncJSONDownloadTask().execute(myURL1);
            } else {
                textView.setText("Network Problem");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private class AsyncJSONDownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                //DBSecondClass obj=new DBSecondClass(getApplicationContext());
                //obj.addPost();
               // CSDatabase.FeedReaderDbHelper obj = new CSDatabase.FeedReaderDbHelper(getApplicationContext());

                return loadJSONFromNetwork(params[0]);

                //
            } catch (Exception e) {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                //WebView myWebView = (WebView) findViewById(R.id.webview);
                //myWebView.loadData(result, "text/html", null);
                //String[] testArray = new String[20];
                // ArrayList<String> Hosted_Application_List_Array = new ArrayList<String>();
                ArrayList testArray = new ArrayList();
                String item = null;
                String item1 = null;
                for (int i = 0; i < cabinetList.size(); i++) {
                    item = cabinetList.get(i).toString() + "\n" + item;
                    item1 = cabinetList.get(i).toString();
                    testArray.add(item1);
                    //textView.setText(item + "\n");
                }

                ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.rack_data_listing_meta, testArray);
                //textView.setText(result);
                //textView.setText(cabinetList.toString());
                //ArrayAdapter adapter = new ArrayAdapter<String>(context, R.layout.list_item_for_listview1_meta,cabinetList);
                //ListAdapter adapter = new SimpleAdapter(PullJSONActivity.this, cabinetList,
                // R.layout.list_item_for_listview1_meta, new String[] { "Cabinet ID","Cabinet RowID","DC name" });                    );
                final ListView listViewR = (ListView) findViewById(R.id.listviewRack);
                listViewR.setAdapter(adapter);
                Set udcset = new HashSet(DCNameList);
                UniquieDCName=udcset.toString();
                textView.setText(UniquieDCName);

                //                +++++++++++++++++++++++
/*
                // Create the list with duplicates.
                List<String> listAll = Arrays.asList("CO2", "CH4", "SO2", "CO2", "CH4", "SO2", "CO2", "CH4", "SO2");

                // Create a list with the distinct elements using stream.
                List<String> listDistinct = listAll.stream().distinct().collect(Collectors.toList());

                // Display them to terminal using stream::collect with a build in Collector.
                String collectAll = listAll.stream().collect(Collectors.joining(", "));
                System.out.println(collectAll);
                String collectDistinct = listDistinct.stream().collect(Collectors.joining(", "));
                System.out.println(collectDistinct);*/



            }catch (Exception e){
                e.printStackTrace();}
            }
        }


    private String loadJSONFromNetwork(String urlString) throws XmlPullParserException, IOException {
        String returnFromDCIMServer = null;
        //ArrayList<HashMap<String, String>> cabinetList=null;
        String cabinetlistString = null;
        //Set set1 = new HashSet();
        //DBHelperClass1 obj=new DBHelperClass1(this);
        DBSecondClass obj=new DBSecondClass(this);
        //DatabaseHelper obj= new DatabaseHelper(this);

        HTTPwithBasicAuth httpServerWNR = new HTTPwithBasicAuth();
        returnFromDCIMServer = httpServerWNR.HTTPtoServer(urlString);

        try{
            JSONObject jsonObj = new JSONObject(returnFromDCIMServer);
            JSONArray cabinetArray = jsonObj.getJSONArray("cabinet");

            //cabinetArray = jsonObj.getJSONArray("cabinet");
            for (int i=0;i<cabinetArray.length();i++){
                JSONObject cabinetObj = cabinetArray.getJSONObject(i);
                String CabinetID = cabinetObj.getString("CabinetID");
                String DataCenterID =cabinetObj.getString("DataCenterID");
                String Location =cabinetObj.getString("Location");
                String LocationSortable =cabinetObj.getString("LocationSortable");
                String AssignedTo  =cabinetObj.getString("AssignedTo");
                String  ZoneID =cabinetObj.getString("ZoneID");
                String CabRowID  =cabinetObj.getString("CabRowID");
                String  CabinetHeight =cabinetObj.getString("CabinetHeight");
                String Model  =cabinetObj.getString("Model");
                String Keylock  =cabinetObj.getString("Keylock");
                String MaxKW  =cabinetObj.getString("MaxKW");
                String MaxWeight  =cabinetObj.getString("MaxWeight");
                String InstallationDate  =cabinetObj.getString("InstallationDate");
                String Notes  =cabinetObj.getString("Notes");
                String  U1Position =cabinetObj.getString("U1Position");
                String  DataCenterName =cabinetObj.getString("DataCenterName");
                // tmp hashmap for single contact
                HashMap<String, String> cabinet = new HashMap<String, String>();
                // adding each child node to HashMap key => value
                cabinet.put("Cabinet ID",CabinetID);
                cabinet.put("DataCenterID",DataCenterID);
                cabinet.put("Location",Location);
                cabinet.put("LocationSortable",LocationSortable);
                cabinet.put("CabinetHeight",CabinetHeight);
                cabinet.put("Model",Model);
                cabinet.put("Keylock",Keylock);
                cabinet.put("MaxKW",MaxKW);
                cabinet.put("MaxWeight",MaxWeight);
                cabinet.put("InstallationDate",InstallationDate);
                cabinet.put("DataCenterName",DataCenterName);
                cabinetList.add(cabinet);
                cabinetlistString=cabinetList.toString();
                DCNameList.add(cabinet.get("DataCenterName"));

            }
            //obj.addRack();
            obj.addCabinet(cabinetArray);
            //String str=  obj.getAllPosts();
            obj.getDcHalls();

           }catch (Exception e){e.printStackTrace();}
        //return returnFromDCIMServer;
        return cabinetlistString;
        //int numcount = Arrays.

    }

}
