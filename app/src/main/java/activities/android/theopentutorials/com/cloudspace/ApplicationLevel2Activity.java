package activities.android.theopentutorials.com.cloudspace;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ApplicationLevel2Activity extends AppCompatActivity {
    private TextView txtvw1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_level2);
        Intent intent = getIntent();
        String message = intent.getStringExtra("ath_name");
        String url = intent.getStringExtra("postNgetUrl");
        txtvw1=(TextView)findViewById(R.id.textView1);
        txtvw1.setText(message);

        ConnectivityManager conmgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nwinfo = conmgr.getActiveNetworkInfo();
        if (nwinfo !=null && nwinfo.isConnected()){
            new AsyncDataPostNGet().execute(url,message);
        } else {
            Toast.makeText(getApplicationContext(),"Network Check failed",Toast.LENGTH_LONG).show();
        }

    }
    //Inner Class for Async task  PostTOServer this will have method to Write&ReadToServer
    private class AsyncDataPostNGet extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String...urlnParams){
            // urlnParams comes from the PostToServer.execute() call: urlnParams[0] is the url.
            try {
                return HTTPWriteNRead(urlnParams);
            } catch (IOException e) {return "Unable to POST to server , check url or server connection";}
        }
        //on post execute displays the result
        //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected  void onPostExecute(String result){
            txtvw1.setText(result);
            //Toast.makeText(getApplicationContext(), "Result:"+result, Toast.LENGTH_LONG).show();
        }
    }
    //This is the actual HTTP connection , Write andd read , this wrapped in a Async class ....
    // and the class object is actually called during the onclick event
    private String HTTPWriteNRead(String... urlnParams1)throws IOException {
        String url = urlnParams1[0];
        String athname = urlnParams1[1];
        String charset = "UTF-8";
        StringBuilder resultObj = new StringBuilder();
        StringBuilder sbParams;
        String paramsString;
        sbParams = new StringBuilder();
        try {//key value that is defined in the cgi program at the server is which_athlete
            sbParams.append("which_athlete").append("=").append(URLEncoder.encode(urlnParams1[1], charset));
            //To append more parameters repeat the above line after appaneding one "&" the code will look like:
            ///*sbParams.append("&");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {//Block for url connection
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            //conn.setRequestProperty(String filed , String new vale ); could actually pass the parameters then why we are
            //using the write ??????
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.connect();
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            //Write the parameters to the server starts here.The paramsString="which_athelete=Sarah+Sweeney" however
            //what gets addeed after the url/filename.py is ?which_athlete=Sarah Sweeney
            paramsString = sbParams.toString();
            wr.writeBytes(paramsString);
            wr.flush();
            wr.close();
            //The http://220.225.15.126:8000/cgi-bin/generate_timing_data_json_cgifield.py?which_athlete=Sarah
            //Sweeney gets executed on the server and it returns the swiming time results . We need to read that result
            //connection provides input stream , that can be read by inputstarem reader and stored in a buffer object
            InputStream inputstreamObj = new BufferedInputStream(conn.getInputStream());
            BufferedReader bufferreaderObj = new BufferedReader(new InputStreamReader(inputstreamObj));
            //Read each line of buffer and  to a string object resultObj
            String line;
            while ((line = bufferreaderObj.readLine()) != null) {
                resultObj.append(line);
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Toast.makeText(getApplicationContext(), "Result:"+resultObj.toString(), Toast.LENGTH_LONG).show();
        return resultObj.toString();
    }
}
