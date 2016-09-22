package activities.android.theopentutorials.com.cloudspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by acer on 05-06-2016.
 */
public class AdapterSubappList extends BaseAdapter {

    private static ArrayList<String> appCountList;

    private LayoutInflater mInflater;
    public AdapterSubappList(Context context, ArrayList<String> results) {
        appCountList = results;
        mInflater = LayoutInflater.from(context);
    }
    static class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4 ;
        TextView textView5 ;
        TextView textView6;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView1 = null;
        TextView textView2 = null;
        TextView textView3 = null;
        TextView textView4 = null;
        TextView textView5 = null;
        TextView textView6  =null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.app_list_group_rows_meta, null);
        }
        ViewHolder holder = new ViewHolder();
        holder.textView1 = (TextView) convertView.findViewById(R.id.txtvWSubAppName);
        //textView1 = (TextView) convertView.findViewById(R.id.divappcountDiv);
        holder.textView2 = (TextView) convertView.findViewById(R.id.txtvWSAppName);
        holder.textView3 = (TextView) convertView.findViewById(R.id.txtVwHosts);
        holder.textView4 = (TextView) convertView.findViewById(R.id.txtVwCPU);
        holder.textView5 = (TextView) convertView.findViewById(R.id.txtVwStorage);
        holder.textView6 = (TextView) convertView.findViewById(R.id.txtVwAppid);
        String S[] = appCountList.get(position).toString().split(",");
        convertView.setTag(holder);
        holder.textView1.setText(S[0]);
        holder.textView2.setText(S[1]);
        holder.textView3.setText(S[2]+" hosts");
        holder.textView4.setText(S[3]+" cpu");
        holder.textView5.setText(S[4]+" GB storage");
        holder.textView6.setText("ID:"+S[5]);
        return convertView;
    }
/*
String subAppdetail=subapp_name 0+","+app_name 1+","+host_count 2+","+cpu_count 3+
                                        ","+storage_count 4+","+app_id 5+","+vm_count 6;
 */
    public int getCount() {
        return appCountList.size();
    }
    public Object getItem(int position) {
        return appCountList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
}
