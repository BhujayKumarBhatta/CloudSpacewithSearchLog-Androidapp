package activities.android.theopentutorials.com.cloudspace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity2 extends AppCompatActivity {

    private EditText loginIDView;
    private EditText loginPwdView;
    private TextView loginStatView;
    private Button loginButton;
    private String loginid_text;
    private String loginpwd_text;
    private String loginapiadddress1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        loginIDView = (EditText)findViewById(R.id.VwidloginID);
        loginPwdView = (EditText)findViewById(R.id.VwidloginPwd);
        loginStatView =(TextView)findViewById(R.id.VwidloginMsg);
        loginButton= (Button)findViewById(R.id.VwidloginButton);

        try {
            File traceFile = new File(((Context) this).getFilesDir(), "CSSettingsFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(traceFile));
            String S_array[] = new String[20];
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                S_array[i] = line;
                i++;             //sb.append(line);
            }
            reader.close();
            loginapiadddress1 = S_array[12];

        }catch (Exception e){e.printStackTrace();
            loginapiadddress1 = "http://14.142.104.139:8000/api/getLNregDB.py";
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginid_text = loginIDView.getText().toString().trim();
                loginpwd_text = loginPwdView.getText().toString().trim();

                if (loginid_text.equals("")){loginIDView.setError("please enter a valid email address");}
                if (loginpwd_text.equals("")){loginPwdView.setError("please enter password");}

                if ((loginid_text.equals(""))||(loginpwd_text.equals(""))){loginStatView.setError("Invalid Credentials");
                }else {
                    attemptLogin(loginid_text ,loginpwd_text);
                }

            }
        });
    }

    private void attemptLogin(final String userid,final String password) {
        //loginapiadddress1 = "http://14.142.104.139:8000/api/getLNregDB.py";
        //loginapiadddress1 = "http://220.225.15.126:8000/cgi-bin/getLNregDB.py";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest streq = new StringRequest(Request.Method.POST, loginapiadddress1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loginStatView.setText(response.toString());
                        Intent RegFormtintent = new Intent(getApplicationContext(), LotusRegActivity.class);
                        RegFormtintent.putExtra("LoginResult", response.toString());
                        RegFormtintent.putExtra("AdminLoginID", loginid_text.toString());
                        startActivity(RegFormtintent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loginStatView.setText(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams()throws com.android.volley.AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("uid",userid);
                params.put("pwd",password);
                return params;
            }

        };
        RetryPolicy retryPolicy = new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 25000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 5;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        };
        streq.setRetryPolicy(retryPolicy);
        queue.add(streq);
    }
}
