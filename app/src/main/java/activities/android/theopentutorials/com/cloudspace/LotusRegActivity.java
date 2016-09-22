package activities.android.theopentutorials.com.cloudspace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LotusRegActivity extends Activity {
    private EditText vwfirstname;private EditText vwmidname; private EditText vwlastname;
    private Spinner vwLNconID;
    private TextView vwLNConDetails;
    private EditText vwmobile; private EditText vwempid; private EditText vwaddl1;

    String lnRegFirstname=""; String lnRegMidname=""; String lnRegLastname="";
    String lnRegLNconID="";String LNConDetails="";
    String lnRegMobile=""; String lnRegEmpid="";String FieldTextStatus="";
    String LNRESTapiadddress1; String URLArguments; String LNregUrl;
    String lnRegAdminemailID="";
    String LN_reg_policy_details ="";


    private ArrayList<String> LNConfig_ID_List = new ArrayList<String>();
    Map<String, String> ConfigIDtoDivMap = new HashMap<String, String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotus_reg);
        vwfirstname = (EditText)findViewById(R.id.vwLNfirstname);
        vwmidname = (EditText)findViewById(R.id.vwLNmiddlename);
        vwlastname =(EditText)findViewById(R.id.vwLNlastname);
        vwLNconID = (Spinner) findViewById(R.id.vwidLNconID);
        vwLNConDetails = (TextView)findViewById(R.id.vwidLNCondetails);
        vwmobile= (EditText)findViewById(R.id.vwLNmobile);
        vwempid= (EditText)findViewById(R.id.vwLNempid);


        Intent intent = getIntent();
        String received_intent_loginresult = intent.getStringExtra("LoginResult");
        lnRegAdminemailID = intent.getStringExtra("AdminLoginID");

        try{
            JSONArray ja = new JSONArray(received_intent_loginresult);
            JSONObject jobj = new JSONObject();
            for (int i=0;i<ja.length();i++){
                jobj = ja.getJSONObject(i);
                String LNConfig_ID = jobj.getString("LNCONFIG_ID");
                if (!LNConfig_ID_List.contains(LNConfig_ID)){LNConfig_ID_List.add(LNConfig_ID);}
                LN_reg_policy_details= "PolicyID:-"+LNConfig_ID+
                        ", Division:-"+jobj.getString("DIV_NAME")+ ","+jobj.getString("DIV_NAME")+
                ", Location:- "+jobj.getString("Location")+
                ", Department:-"+jobj.getString("Department")+
                ", Function:-"+jobj.getString("Function");
                ConfigIDtoDivMap.put(LNConfig_ID,LN_reg_policy_details);

            }

        }catch (Exception e){}

        ArrayAdapter<String> adapterLNConfigID =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item, LNConfig_ID_List);
        adapterLNConfigID.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        vwLNconID.setAdapter(adapterLNConfigID);
        vwLNconID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lnRegLNconID = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),ConfigIDtoDivMap.get(lnRegLNconID),Toast.LENGTH_LONG).show();
                vwLNConDetails.setText(ConfigIDtoDivMap.get(lnRegLNconID));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        try {
            File traceFile = new File(((Context) this).getFilesDir(), "CSSettingsFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(traceFile));
            String S_array[] = new String[15];
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                S_array[i] = line;
                i++;             //sb.append(line);
            }
            reader.close();
            LNRESTapiadddress1 = S_array[9];

        }catch (Exception e){e.printStackTrace();
            LNRESTapiadddress1 = "http://220.225.15.126:8000/cgi-bin/domino.py";
        }

    }

    public void LNregistrationCall(View view) {

        lnRegFirstname = vwfirstname.getText().toString();
        lnRegMidname = vwmidname.getText().toString();
        lnRegLastname = vwlastname.getText().toString();
        lnRegMobile = vwmobile.getText().toString();
        lnRegEmpid = vwempid.getText().toString();

       //Checking Last name
        if (lnRegLastname.equals("")){Toast.makeText(getApplicationContext(),"last name can not be Blank",Toast.LENGTH_LONG).show();
            FieldTextStatus="Failed";
        }else{
            FieldTextStatus="Success";
        }

        //Checking Emp id
        if (lnRegEmpid.equals("")){Toast.makeText(getApplicationContext(),"Employee ID can not be Blank",Toast.LENGTH_LONG).show();
            FieldTextStatus="Failed";
        }else{
            FieldTextStatus="Success";
        }

        //Checking Division
        if (lnRegLNconID.equals("")){Toast.makeText(getApplicationContext(),"Division ID can not be Blank",Toast.LENGTH_LONG).show();
            FieldTextStatus="Failed";
        }else{
            FieldTextStatus="Success";
        }



        if (lnRegLastname.equals("")||lnRegLNconID.equals("")||lnRegEmpid.equals("") ) {
            Toast.makeText(getApplicationContext(),"Please fill up must have fields",Toast.LENGTH_LONG).show();
        } else {

            //LNRESTapiadddress1 = "http://220.225.15.126:8000/cgi-bin/domino.py";
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LNRESTapiadddress1,

                    new Response.Listener<String>() {
                        long startTimeMs;
                        int retryCount;

                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //vwaddl1=(EditText)findViewById(R.id.vwLNaddl1);
                            //vwaddl1.setText("Response is: "+ response.substring(0,500));
                            Intent Regresultintent = new Intent(getApplicationContext(), LotusRegResultActivity.class);
                            Regresultintent.putExtra("LotusReg_result", response.toString());
                            startActivity(Regresultintent);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    vwaddl1.setText(error.getMessage());
                    Log.e("MyError", "Registration Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    NetworkResponse errorRes = error.networkResponse;
                    String stringData = "";
                    if (errorRes != null && errorRes.data != null) {
                        try {
                            stringData = new String(errorRes.data, "UTF-8");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e("Error", stringData);

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("lnm", lnRegLastname);
                    params.put("fnm",lnRegFirstname );
                    params.put("mnm", lnRegMidname);
                    params.put("mbl", lnRegMobile);
                    params.put("emid", lnRegEmpid);
                    params.put("pwd", "12345");
                    params.put("lnregid", lnRegLNconID);
                    params.put("adminemid", lnRegAdminemailID);
                    return params;
                }

                ;
            };

// Add the request to the RequestQueue.
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    25000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        }
    }
}


/*
URLArguments = "?lnm="+lnRegFirstname+"&fnm="+lnRegLastname+"&mnm="+lnRegMidname+
                "&pwd="+12345+"&loc="+lnRegLocation+"&div="+lnRegDivision+"&dpt="+lnRegDepartment+
                "&mbl="+lnRegMobile+"&emid="+lnRegEmpid;
 LNregUrl=LNRESTapiadddress+URLArguments;
 */