package activities.android.theopentutorials.com.cloudspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void TriggerSettings(View view) {
        Intent GoToSettingIntent = new Intent(this, SettingPgViewActivity.class);
        //intentListView.putExtra("ListViewValue1",intent_for_listview);
        GoToSettingIntent.putExtra("ListViewValue1","My Value");
        startActivity(GoToSettingIntent);
    }


    public void GoToHotestedApplication(View view) {
        Intent GoToHostedApplicationIntent = new Intent(this, ApplicationListActivity.class);
        //intentListView.putExtra("ListViewValue1",intent_for_listview);
        //GoToHostedApplicationIntent.putExtra("ListViewValue1","My Value");
        startActivity(GoToHostedApplicationIntent);
    }
    public void GoToHotestedApplication1(View view) {
        Intent GoToHostedApplicationIntent1 = new Intent(this, DivListNappCountActivity.class);
        //intentListView.putExtra("ListViewValue1",intent_for_listview);
        //GoToHostedApplicationIntent.putExtra("ListViewValue1","My Value");
        startActivity(GoToHostedApplicationIntent1);
    }
    public void TriggerRackdata(View view) {
        Intent GoToHostedApplicationIntent = new Intent(this, RackListingActivity.class);
        //intentListView.putExtra("ListViewValue1",intent_for_listview);
        //GoToHostedApplicationIntent.putExtra("ListViewValue1","My Value");
        startActivity(GoToHostedApplicationIntent);
    }
    public void GotodcimMain(View view) {
        Intent GoTodcimMainIntent = new Intent(this, DcimMainActivity.class);
        startActivity(GoTodcimMainIntent);
    }

    public void gotoDcimExpan(View view) {
        Intent gotoDcimExpanIntent = new Intent(this, DcimExpandActivity.class);
        startActivity(gotoDcimExpanIntent);
    }

    public void gotoDivtoAppAcitivity(View view) {
        Intent gotoDivtoAppIntent = new Intent(this, DivtoAppActivity.class);
        startActivity(gotoDivtoAppIntent);
    }

    public void gotoLotusReg(View view) {
        Intent gotoLotusregIntent = new Intent(this, LotusRegActivity.class);
        startActivity(gotoLotusregIntent);
    }

    public void gotoLogin(View view) {
        Intent gotoLoginIntent = new Intent(this, LoginActivity2.class);
        startActivity(gotoLoginIntent);
    }


    public void gotoDjangoLogin(View view) {
        Intent gotoDjangoLoginIntent = new Intent(this, LoginSearchLogDjangoActivity.class);
        startActivity(gotoDjangoLoginIntent);
    }

}
