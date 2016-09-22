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
public class AdapterSearchResult_LogLines extends BaseAdapter {
    private static JSONArray DJ_Log_Lines_List;

    private LayoutInflater mInflater;
    public AdapterSearchResult_LogLines (Context context, JSONArray results) {
        DJ_Log_Lines_List = results;
        mInflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        //CheckBox CheckBox1_rs;
        TextView textView1_logline;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // CheckBox CheckBox1_rs = null;
        TextView TextView1_logline = null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.searchlog_result_log_line_meta, null);
        }
        ViewHolder holder = new ViewHolder();
        //holder.CheckBox1_rs = (CheckBox) convertView.findViewById(R.id.Checkbox_resultserver);
        holder.textView1_logline = (TextView) convertView.findViewById(R.id.vwid_result_logline_row_meta);

        //String S[] = DJ_Server_List.get(position).toString().split(",");


        JSONObject log_line_jo = new JSONObject();
        try {
            log_line_jo = DJ_Log_Lines_List.getJSONObject(position);
            convertView.setTag(holder);
            holder.textView1_logline.setText(log_line_jo.getString("logline"));

        }catch (Exception e){e.printStackTrace();}


        return convertView;

    }


    public int getCount() {
        return DJ_Log_Lines_List.length();
    }
    public Object getItem(int position) {
        JSONObject  item = new JSONObject();
        try {
            item=(DJ_Log_Lines_List.getJSONObject(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public long getItemId(int position) {
        return position;
    }
}
