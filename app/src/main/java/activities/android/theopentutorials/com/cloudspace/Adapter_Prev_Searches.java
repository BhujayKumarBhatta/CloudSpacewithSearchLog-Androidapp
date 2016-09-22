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
 * Created by acer on 15-09-2016.
 */
public class Adapter_Prev_Searches extends BaseAdapter {
    private static JSONArray DJ_prev_sid;

    private LayoutInflater mInflater;
    public Adapter_Prev_Searches (Context context, JSONArray results) {
        DJ_prev_sid = results;
        mInflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        //CheckBox CheckBox1_rs;
        TextView textView1_sid;
        TextView textView2_sid;
        TextView textView3_sid;
        TextView textView4_sid;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // CheckBox CheckBox1_rs = null;
        TextView TextView1_sid = null;
        TextView TextView2_sid = null;
        TextView TextView3_sid = null;
        TextView TextView4_sid = null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.previous_searchid_list_row_meta, null);
        }
        ViewHolder holder = new ViewHolder();
        holder.textView1_sid = (TextView) convertView.findViewById(R.id.vwid_prev_searchid);
        holder.textView2_sid = (TextView) convertView.findViewById(R.id.vwid_prev_searchby);
        holder.textView3_sid = (TextView) convertView.findViewById(R.id.vwid_prev_search_time);
        holder.textView4_sid = (TextView) convertView.findViewById(R.id.vwid_prev_other);


        JSONObject prev_sid_jo = new JSONObject();
        try {
            prev_sid_jo = DJ_prev_sid.getJSONObject(position);
            convertView.setTag(holder);
            holder.textView1_sid.setText(prev_sid_jo.getString("id"));
            holder.textView2_sid.setText(prev_sid_jo.getString("searchedby"));
            //holder.textView3_sid.setText(prev_sid_jo.getString("searchdate"));
            holder.textView4_sid.setText(prev_sid_jo.getString("created"));

        }catch (Exception e){e.printStackTrace();}


        return convertView;

    }


    public int getCount() {
        return DJ_prev_sid.length();
    }
    public Object getItem(int position) {
        JSONObject  item = new JSONObject();
        try {
            item=(DJ_prev_sid.getJSONObject(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public long getItemId(int position) {
        return position;
    }
}
