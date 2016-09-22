package activities.android.theopentutorials.com.cloudspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by acer on 06-09-2016.
 */
public class AdapterDjangoServerList extends BaseAdapter {
    private static ArrayList<String> DJ_Server_List;

    private LayoutInflater mInflater;
    public AdapterDjangoServerList (Context context, ArrayList<String> results) {
        DJ_Server_List = results;
        mInflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        CheckBox CheckBox1;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4 ;
        TextView textView5 ;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        CheckBox CheckBox1 = null;
        TextView TextView1 = null;
        TextView textView2 = null;
        TextView textView3 = null;
        TextView textView4 = null;
        TextView textView5 = null;


        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.searchlog_server_list_row_meta, null);
        }
        ViewHolder holder = new ViewHolder();
        holder.CheckBox1 = (CheckBox) convertView.findViewById(R.id.Checkbox1);
        holder.textView1 = (TextView) convertView.findViewById(R.id.txtvWSrvNameDJ);
        holder.textView2 = (TextView) convertView.findViewById(R.id.txtvWIPaddrDJ);
        holder.textView3 = (TextView) convertView.findViewById(R.id.txtvwSSHDJ);
        holder.textView4 = (TextView) convertView.findViewById(R.id.txtVwSRVIDDJ);
        holder.textView5 = (TextView) convertView.findViewById(R.id.txtVwOSIDDJ);

        String S[] = DJ_Server_List.get(position).toString().split(",");
        /*
        String srv_details= Server_ID +","+ Server_Name+","+Server_IP+","+SSH_Port+","+Server_OS ;
         */
        convertView.setTag(holder);
        holder.textView1.setText(S[1]);
        holder.textView2.setText(S[2]);
        holder.textView3.setText(S[3]);
        holder.textView4.setText(S[0]);
        holder.textView5.setText(S[4]);

       // CheckBoxView1.setOnCheckedChangeListener(myCheckChangList);
        //CheckBoxView1.setTag(position);
        //CheckBoxView1.setChecked(p.box);
        return convertView;

    }


    public int getCount() {
        return DJ_Server_List.size();
    }
    public Object getItem(int position) {
        return DJ_Server_List.get(position);
    }
    public long getItemId(int position) {
        return position;
    }


}
