package activities.android.theopentutorials.com.cloudspace;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

//public class DcimExpandActivity extends ExpandableListActivity  {
public class DcimExpandActivity extends AppCompatActivity  {
    // Create ArrayList to hold parent Items and Child Items
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    String myURL1 = "http://14.142.104.138/api/v1/cabinet";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcim_expand);
        //+++++++++Call Async task -Network Check, get data from server and pt it to SqlLite DB+++++++++++
        try {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new AsyncJSONtoDBTask().execute(myURL1);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //++++ Async task calling stops here++++++++++
        //++++++Expanded List formation Starts here+++++++++++++++++++++++++
        // Set the Items of Parent  from DB actual method ///////setGroupParents(); -dummay method
        DBSecondClass obj = new DBSecondClass(this);
        //parentItems = obj.getDcHalls();
        parentItems=obj.getDcHallsAttribs();
        // Set The Child Data  getiing it from DB by parents value/////setChildData(); ---Dummy method
        for (int v = 0; v < parentItems.size(); v++) {
            //childItems.add(obj.getRackListInHall(parentItems.get(v).toString())); //ned to add four arrays or thos
            childItems.add(obj.getRackInHallDetails(parentItems.get(v).toString()));
            //Remeber child item becomes a list of sublists
        }
        // Create Expandable List View  and set it's properties
        ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.dcimexpandlist);
        //ExpandableListView expandableList = getExpandableListView();
        expandableList.setDividerHeight(2);
       // expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);
        // Create the Adapter
        DcimExpandAdapter adapter = new DcimExpandAdapter(parentItems, childItems);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        // Set the Adapter to expandableList
        expandableList.setAdapter(adapter);
        //expandableList.setOnChildClickListener(this);
        //++++++++++++Expanded List Stops here ++++++++++++++++++++
    }
    private class AsyncJSONtoDBTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                return dcimDataDownloadToDb(params[0]);  //
            } catch (Exception e) {
                return ""; }
        }
        @Override
        protected void onPostExecute(String result) {
        }
    }
    private String dcimDataDownloadToDb(String urlString) throws IOException {
        DBSecondClass obj = new DBSecondClass(this);
        HTTPwithBasicAuth httpServerWNR = new HTTPwithBasicAuth();
        String returnFromDCIMServer = httpServerWNR.HTTPtoServer(urlString);
        try {
            JSONObject jsonObj = new JSONObject(returnFromDCIMServer);
            JSONArray cabinetArray = jsonObj.getJSONArray("cabinet");
            obj.addCabinet(cabinetArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        parentItems = obj.getDcHalls();
        //rackListInHall=obj.getRackListInHall("gotoDcimExpan");
        return null;
    }
}

