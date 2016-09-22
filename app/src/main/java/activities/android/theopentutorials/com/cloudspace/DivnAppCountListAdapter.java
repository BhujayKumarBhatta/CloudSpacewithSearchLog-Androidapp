package activities.android.theopentutorials.com.cloudspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by acer on 02-06-2016.
 */
public class DivnAppCountListAdapter extends BaseAdapter {

    private static ArrayList<String> divNappCountList;

    private LayoutInflater mInflater;
    public DivnAppCountListAdapter(Context context, ArrayList<String> results) {
        divNappCountList = results;
        mInflater = LayoutInflater.from(context);
            }
    static class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4 ;
        TextView textView5 ;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView1 = null;
        TextView textView2 = null;
        TextView textView3 = null;
        TextView textView4 = null;
        TextView textView5 = null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.div_list_napp_row_meta, null);
        }
        ViewHolder holder = new ViewHolder();
        holder.textView1 = (TextView) convertView.findViewById(R.id.divappcountDiv);
        //textView1 = (TextView) convertView.findViewById(R.id.divappcountDiv);
            holder.textView2 = (TextView) convertView.findViewById(R.id.divappcountApp);
            holder.textView3 = (TextView) convertView.findViewById(R.id.divappcountHosts);
            holder.textView4 = (TextView) convertView.findViewById(R.id.divappcountCpu);
            holder.textView5 = (TextView) convertView.findViewById(R.id.divappcountStorage);
            String S[] = divNappCountList.get(position).toString().split(",");
            convertView.setTag(holder);
            holder.textView1.setText(S[0]+","+S[1]);
            holder.textView2.setText(S[3]);
            holder.textView3.setText(S[4]);
            holder.textView4.setText(S[5]);
            holder.textView5.setText(S[6]);
            return convertView;
    }


/*
String countForaDivision = divNsub 0+","+appcnt 1+" apps"+","+subapcnt 2+" apps"+","+hstcnt 3+" servers"
                            +","+cpu 4+" cpu"+","+storagae 5+" GB storage";
 */
    public int getCount() {
        return divNappCountList.size();
    }
    public Object getItem(int position) {
        return divNappCountList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
 }

