package activities.android.theopentutorials.com.cloudspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by acer on 08-06-2016.
 */
public class AdapterServerList extends BaseAdapter {

    private static ArrayList<String> ServerCountList;

    private LayoutInflater mInflater;
    public AdapterServerList (Context context, ArrayList<String> results) {
        ServerCountList = results;
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

    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView textView1 = null;
        TextView textView2 = null;
        TextView textView3 = null;
        TextView textView4 = null;
        TextView textView5 = null;
        TextView textView6  =null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.serverlist_child_row_meta, null);
        }
        ViewHolder holder = new ViewHolder();
        holder.textView1 = (TextView) convertView.findViewById(R.id.txtvWSrvName);
        //textView1 = (TextView) convertView.findViewById(R.id.divappcountDiv);
        holder.textView2 = (TextView) convertView.findViewById(R.id.txtvWIPaddr);
        holder.textView3 = (TextView) convertView.findViewById(R.id.txtVwCPU);
        holder.textView4 = (TextView) convertView.findViewById(R.id.txtVwStorage);
        holder.textView5 = (TextView) convertView.findViewById(R.id.txtVwRAM);
        holder.textView6 = (TextView) convertView.findViewById(R.id.txtvwenv);
        String S[] = ServerCountList.get(position).toString().split(",");
        /*
        String serverdetail=host_name+","+host_ip+","+host_cpu+","+host_ram+
                                        ","+host_storage+","+env_name;
         */
        convertView.setTag(holder);
        holder.textView1.setText(S[0]);
        holder.textView2.setText(S[1]);
        holder.textView3.setText(S[2]+" Cpu");
        holder.textView4.setText(S[3]+" Ram");
        holder.textView5.setText(S[4]+" GB storage");
        holder.textView6.setText(S[5]);
        return convertView;
    }

    public int getCount() {
        return ServerCountList.size();
    }
    public Object getItem(int position) {
        return ServerCountList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }


}
