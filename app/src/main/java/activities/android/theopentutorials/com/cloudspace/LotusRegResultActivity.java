package activities.android.theopentutorials.com.cloudspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import org.json.JSONObject;

public class LotusRegResultActivity extends AppCompatActivity {
    private TextView vw_LNregresult;
    private TextView vw_LNRegStat;
    private TextView vw_LNdisplyaName;
    private TextView vw_LNemailAddr;
    private TextView vw_LNactionReqd;
    private TextView vw_LNemailWeblnk;

    private  String Registration_Status;
    private String Display_Name_Tried;
    private String Email_Address;
    private String Action_Required;
    private String Email_WebLink;
    private String link_value;
    private String rstat;
    private String Reg_Stat_Sanitized;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotus_reg_result);
        Intent intent = getIntent();
        String message = intent.getStringExtra("LotusReg_result");
        vw_LNregresult = (TextView) findViewById(R.id.vwLotusRegResult);
        vw_LNRegStat = (TextView) findViewById(R.id.vwidLNGreetings);
        vw_LNdisplyaName = (TextView) findViewById(R.id.vwidLNnameValue);
        vw_LNemailAddr  = (TextView) findViewById(R.id.vwidEmailaddrsssValue);
        vw_LNactionReqd = (TextView) findViewById(R.id.vwidLNnextAction);
        vw_LNemailWeblnk = (TextView) findViewById(R.id.vwidLNwebLink);

        vw_LNregresult.setText(message);

        try {
            JSONObject jo = new JSONObject(message);
            Registration_Status = jo.getString("RegistrationStatus").trim();
            Reg_Stat_Sanitized = Registration_Status.replace("\"", "");
            Display_Name_Tried=jo.getString("DisplayNameTried");
            Email_Address=jo.getString("EmailAddress");
            Action_Required=jo.getString("ActionRequired");
            Email_WebLink=jo.getString("EmailWebLink");
            link_value= "<html>Visit company mail site <a href= \""+Email_WebLink +
                    "\" >Click to login </a></html>";
            rstat="Success";
            String S[]=Email_WebLink.split("\\\\");
            Email_WebLink=S[0]+"/"+S[1];
            //vw_LNemailWeblnk.setText(Html.fromHtml(link_value));
            vw_LNemailWeblnk.setText(Email_WebLink);
            Linkify.addLinks(vw_LNemailWeblnk, Linkify.WEB_URLS);
            vw_LNemailWeblnk.setMovementMethod(LinkMovementMethod.getInstance());

            //if (Registration_Status.equalsIgnoreCase("Success")){

            if (Reg_Stat_Sanitized.equals("Success")){
                vw_LNRegStat.setText("Welcome to Company mail System !!! please note down your id details ");
                vw_LNdisplyaName.setText(Display_Name_Tried);
                vw_LNemailAddr.setText(Email_Address);
                vw_LNactionReqd.setText(Action_Required);
            }else {
                vw_LNRegStat.setText("Unfortunately registratio failed ! Perhaps you should try with a differnt name ." +
                        "Check the details log given below and retry ");
                vw_LNactionReqd.setText(Action_Required);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
