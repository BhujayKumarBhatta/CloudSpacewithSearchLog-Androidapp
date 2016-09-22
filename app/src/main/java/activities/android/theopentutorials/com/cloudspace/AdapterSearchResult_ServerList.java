package activities.android.theopentutorials.com.cloudspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by acer on 13-09-2016.
 */
public class AdapterSearchResult_ServerList extends BaseAdapter {
    private static JSONArray DJ_Result_Server_List;

    private LayoutInflater mInflater;
    public AdapterSearchResult_ServerList (Context context, JSONArray results) {
        DJ_Result_Server_List = results;
        mInflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        //CheckBox CheckBox1_rs;
        TextView textView1_rs;
        TextView textView2_rs;
        TextView textView3_rs;
        TextView textView4_rs ;
        TextView textView5_rs ;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
       // CheckBox CheckBox1_rs = null;
        TextView TextView1_rs = null;
        TextView textView2_rs = null;
        TextView textView3_rs = null;
        TextView textView4_rs = null;
        TextView textView5_rs = null;


        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.searchlog_result_server_list_row_meta, null);
        }
        ViewHolder holder = new ViewHolder();
        //holder.CheckBox1_rs = (CheckBox) convertView.findViewById(R.id.Checkbox_resultserver);
        holder.textView1_rs = (TextView) convertView.findViewById(R.id.txtvWSrvNameDJ_resultserver);
        holder.textView2_rs = (TextView) convertView.findViewById(R.id.txtvWIPaddrDJ_resultserver);
        holder.textView3_rs = (TextView) convertView.findViewById(R.id.txtvwSSHDJ_resultserver);
        holder.textView4_rs = (TextView) convertView.findViewById(R.id.txtVwSRVIDDJ_resultserver);
        holder.textView5_rs = (TextView) convertView.findViewById(R.id.txtVwOSIDDJ_resultserver);

        //String S[] = DJ_Server_List.get(position).toString().split(",");
        /*
        try {
            String S[] = DJ_Result_Server_List.get(position).toString().split(",");
            convertView.setTag(holder);
            holder.textView1_rs.setText(S[0]);
            holder.textView2_rs.setText(S[1]);
            holder.textView3_rs.setText(S[2]));
        }catch (Exception e){e.printStackTrace();}
        */

        JSONObject result_server_jo = new JSONObject();
        try {
                result_server_jo = DJ_Result_Server_List.getJSONObject(position);
                convertView.setTag(holder);
                holder.textView1_rs.setText(result_server_jo.getString("name"));
                holder.textView2_rs.setText(result_server_jo.getString("ip"));
                holder.textView3_rs.setText(result_server_jo.getString("osinstance"));
                holder.textView4_rs.setText(result_server_jo.getString("id"));
                //holder.textView5.setText(S[4]);
            }catch (Exception e){e.printStackTrace();}

        /*
        String srv_details= Server_ID +","+ Server_Name+","+Server_IP+","+SSH_Port+","+Server_OS ;
         */
        // CheckBoxView1.setOnCheckedChangeListener(myCheckChangList);
        //CheckBoxView1.setTag(position);
        //CheckBoxView1.setChecked(p.box);
        return convertView;

    }


    public int getCount() {
        return DJ_Result_Server_List.length();
    }
    public Object getItem(int position) {
        JSONObject  item = new JSONObject();
        try {
            item=(DJ_Result_Server_List.getJSONObject(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public long getItemId(int position) {
        return position;
    }
}
