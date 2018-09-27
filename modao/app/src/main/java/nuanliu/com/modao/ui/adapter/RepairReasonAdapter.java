package nuanliu.com.modao.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nuanliu.com.modao.R;

public class RepairReasonAdapter extends BaseAdapter {

    private Context context;
    private ArrayList list;

    public RepairReasonAdapter(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (null == convertView) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.reason_list, null);
            vh.tvReason = (TextView) convertView.findViewById(R.id.tv_reason);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tvReason.setText(list.get(position) + "");
        return convertView;
    }

    class ViewHolder {
        private TextView tvReason;
    }
}
