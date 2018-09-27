package nuanliu.com.modao.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import nuanliu.com.modao.R;
import nuanliu.com.modao.bean.RegionBean;

public class RegionAdapter extends BaseAdapter {

    private Context context;
    private List<RegionBean.BodyBean> list;

    public RegionAdapter(Context context, List<RegionBean.BodyBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RegionBean.BodyBean getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.community_list_item, null);
            vh.tvCommunityName = (TextView) convertView.findViewById(R.id.tv_community_name);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tvCommunityName.setText(list.get(position).getName() + "");
        return convertView;
    }

    class ViewHolder {
        private TextView tvCommunityName;
    }
}
