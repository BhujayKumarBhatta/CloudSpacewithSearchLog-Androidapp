package activities.android.theopentutorials.com.cloudspace;

import android.app.Activity;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by acer on 19-05-2016.
 */

public class SettingPgViewActivity extends Activity {

//
    private EditText appListSrvAddrTxt;
    private EditText subappListSrvaddrTxt;
    private EditText dcimSrvAddrTxt;
    private EditText divlistNumRowView;
    private EditText subappListNumRowView;
    private EditText serversListurlView;
    private EditText serverRowNumView;
    private EditText lotusUrlView;
    private EditText useridView;
    private EditText passwordView;
    private EditText vwloginApi;
    private EditText vwLogSearchApi;



    private String appListSrvurl;
    private String divListNumRow;
    private String subappListSrvurl;
    private String subappListNumRow;
    private String dcimSrvurl;
    private EditText server1;
    private EditText server2;
    private String server1url;
    private String server2url;
    private String serverListurl;
    private String serverListRowNum;
    private String lotusApiUrl;
    private String useridText;
    private String passwordText;
    private String txtLoginApi;
    private String txtLogSearchApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingpg);
        server1 = (EditText) findViewById(R.id.EditTxtserver1);
        server2 = (EditText) findViewById(R.id.EditTxtserver2);
        appListSrvAddrTxt=(EditText) findViewById(R.id.EditTxtApps1);
        subappListSrvaddrTxt=(EditText)findViewById(R.id.EditTxtSubApps1);
        dcimSrvAddrTxt=(EditText) findViewById(R.id.EditTxtDcim);
        divlistNumRowView=(EditText)findViewById(R.id.EditTxtNumRowDiv);
        subappListNumRowView = (EditText)findViewById(R.id.EditTxtNumRowApp);
        serversListurlView=(EditText)findViewById(R.id.EditViewServers);
        serverRowNumView=(EditText)findViewById(R.id.EditViewNumRowservers);
        lotusUrlView = (EditText)findViewById(R.id.EditTxtLotus);
        useridView = (EditText)findViewById(R.id.settingVwloginid);
        passwordView = (EditText)findViewById(R.id.settingVwpassword);
        vwloginApi = (EditText)findViewById(R.id.vwidLogin);
        vwLogSearchApi = (EditText)findViewById(R.id.vwid_logsearch_api_url);

        String[] alllsetting=ReadSettings(server1);
        server1url=alllsetting[0];
        server2url=alllsetting[1];
        appListSrvurl=alllsetting[2];
        dcimSrvurl=alllsetting[3];
        subappListSrvurl=alllsetting[4];
        divListNumRow=alllsetting[5];
        subappListNumRow=alllsetting[6];
        serverListurl=alllsetting[7];
        serverListRowNum=alllsetting[8];
        lotusApiUrl=alllsetting[9];
        useridText = alllsetting[10];
        passwordText= alllsetting[11];
        txtLoginApi = alllsetting[12];
        txtLogSearchApi = alllsetting[13];


        server1.setText(server1url);
        server2.setText(server2url);
        appListSrvAddrTxt.setText(appListSrvurl);
        dcimSrvAddrTxt.setText(dcimSrvurl);
        divlistNumRowView.setText(divListNumRow);
        subappListSrvaddrTxt.setText(subappListSrvurl);
        subappListNumRowView.setText(subappListNumRow);
        serversListurlView.setText(serverListurl);
        serverRowNumView.setText(serverListRowNum);
        lotusUrlView.setText(lotusApiUrl);
        useridView.setText(useridText);
        passwordView.setText(passwordText);
        vwloginApi.setText(txtLoginApi);
        vwLogSearchApi.setText(txtLogSearchApi);
    }
    public void SaveSettings(View view) {
        // Do something in response to button
        String server1Url = server1.getText().toString();
        String server2Url = server2.getText().toString();
        String appListSrvurl=appListSrvAddrTxt.getText().toString();
        String dcimSrvurl=dcimSrvAddrTxt.getText().toString();
        String subAppListurl=subappListSrvaddrTxt.getText().toString();
        String divlistnumrow=divlistNumRowView.getText().toString();
        String subapplistnumrow=subappListNumRowView.getText().toString();
        String server_list_url=serversListurlView.getText().toString();
        String server_list_rownum=serverRowNumView.getText().toString();
        String lotus_Api_Url =  lotusUrlView.getText().toString();
        String user_id = useridView.getText().toString();
        String user_password= passwordView.getText().toString();
        String login_api = vwloginApi.getText().toString();
        String log_Search_api=vwLogSearchApi.getText().toString();
        try {
            // Creates a trace file in the primary external storage space of the
            // current application.
            // If the file does not exists, it is created.
            File traceFile = new File(((Context) this).getFilesDir(), "CSSettingsFile.txt");
            if (!traceFile.exists())
            traceFile.createNewFile();
            // Adds a line to the trace file
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile));//, false /*append*/));
            writer.write(server1Url+"\n"); //0
            //writer.write(System.getProperty( "line.separator" ));
            //writer.newLine();
            //writer.append("\n");
            writer.append(server2Url+"\n"); //1
            writer.append(appListSrvurl+"\n"); //2
            writer.append(dcimSrvurl+"\n"); //3
            writer.append(subAppListurl+"\n"); //4
            writer.append(divlistnumrow+"\n"); //5
            writer.append(subapplistnumrow+"\n"); //6
            writer.append(server_list_url+"\n"); //7
            writer.append(server_list_rownum+"\n"); //8
            writer.append(lotus_Api_Url+"\n"); //9
            writer.append(user_id+"\n"); //10
            writer.append(user_password+"\n"); //11
            writer.append(login_api+"\n"); //12
            writer.append(log_Search_api+"\n"); //13

            //writer.newLine();
            writer.flush();
            Toast.makeText(getApplicationContext(), "File Saved Successfully", Toast.LENGTH_LONG).show();
            // Refresh the data so it can seen when the device is plugged in a
            // computer. You may have to unplug and replug the device to see the
            // latest changes. This is not necessary if the user should not modify
            // the files.
            MediaScannerConnection.scanFile((Context) (this),
                    new String[]{traceFile.toString()},
                    null,
                    null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String[] ReadSettings(View view) {
        // Do something in response to button

        try
        {
            // Creates a trace file in the primary external storage space of the
            // current application.
            // If the file does not exists, it is created.
            File traceFile = new File(((Context)this).getFilesDir(), "CSSettingsFile.txt");
            if (!traceFile.exists())
                traceFile.createNewFile();
            BufferedReader reader = new BufferedReader(new FileReader(traceFile));
            StringBuilder sb = new StringBuilder();
            String myarray[]= new String[25];
            String line;
            int i=0;
            while ((line = reader.readLine()) != null) {
                myarray[i]=line;
                i++;
                //Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), line.toString(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), myarray[i].toString(), Toast.LENGTH_LONG).show();
                //sb.append(line);
            }
            reader.close();
            // Refresh the data so it can seen when the device is plugged in a
            // computer. You may have to unplug and replug the device to see the
            // latest changes. This is not necessary if the user should not modify
            // the files.
            MediaScannerConnection.scanFile((Context)(this),
                    new String[] { traceFile.toString() },
                    null,
                    null);
            return myarray;

        }
        catch (IOException e)
        {
            e.printStackTrace();
            String[] S={"Exception in reading settings data",};
            return S;
        }

    }

}



