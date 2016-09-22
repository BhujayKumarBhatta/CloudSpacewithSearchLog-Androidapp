
package activities.android.theopentutorials.com.cloudspace;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by acer on 19-05-2016.
 */
public class HostedApplicationActivity extends AppCompatActivity {
    private EditText Showserver1;
    private static final String DEBUG_TAG = "HttpExample";
    ArrayList<String> Hosted_Application_List_Array = new ArrayList<String>();
    String[] DemoArray = new String[] { "Android Example ListActivity", "Adapter implementation", "Simple List View With ListActivity",};
    //private EditText server2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosted_application);
        //Showserver1 = (EditText) findViewById(R.id.postmethodUrl);
        //server2 = (EditText) findViewById(R.id.EditTxtserver2);
        try
        {
            File traceFile = new File(((Context)this).getFilesDir(), "CSSettingsFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(traceFile));
            //StringBuilder sb = new StringBuilder();
            String myarray[]= new String[10];
            String line;
            int i=0;
            while ((line = reader.readLine()) != null) {
                myarray[i]= line;
                i++;             //sb.append(line);
            }
            Toast.makeText(getApplicationContext(), myarray[0].toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), myarray[1].toString(), Toast.LENGTH_LONG).show();
            Showserver1.setText(myarray[1].toString());
            reader.close();
            //MediaScannerConnection.scanFile((Context)(this), new String[] { traceFile.toString() }, null, null);
            //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.hosted_application_item_meta,DemoArray);
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new AsyncHTTPDataExchange().execute(myarray[0]);
            } else {
                //textView.setText("No network connection available.");
            }
        } catch (IOException e){e.printStackTrace();}
    }
    //+++++ AsyncHTTPDataExchange  class +++++++++++Use this HTTPConnection with a Aync Task Class
    private class AsyncHTTPDataExchange extends AsyncTask<String,Void,String> {
        protected String doInBackground(String...url){
            //Here use actua HTTPConnections method that is HTTPConnectToServer
            try{return HTTPConnectToServer(url[0]);}catch (IOException e){return "URL may be invalid"; }
        }
        protected void onPostExecute(String result){
            try{
                JSONArray jarray = new JSONArray(result);
                for (int i=0;i<jarray.length();i++){
                    String jvalue = ""+jarray.get(i);
                    Hosted_Application_List_Array.add(jvalue);
                    //ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.hosted_application_item_meta,Hosted_Application_List_Array);
                    ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.hosted_application_item_meta,Hosted_Application_List_Array);
                    final ListView listView = (ListView)findViewById(R.id.listview1);
                    listView.setAdapter(adapter);
                }
            }catch (Exception e){Toast.makeText(getApplicationContext(),"Server data is not in JSON Format" ,Toast.LENGTH_LONG).show();}
        }
    }
    //HTTPConnectToServer Method++++++++++++++++Actual HTTP Server Connection Method++++++++++++++++++++++++++++++++++++
    private String HTTPConnectToServer(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();
            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}

