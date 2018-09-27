package nuanliu.com.modao.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nuanliu.com.modao.R;
import nuanliu.com.modao.bean.OweItemBean;
import nuanliu.com.modao.widget.refreshview.recyclerview.BaseRecyclerAdapter;

public class OweListAdapter<T> extends BaseRecyclerAdapter<OweListAdapter.ViewHolderOwe> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<T> mDatas = new ArrayList<>();

    public OweListAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void addData(List<T> list) {
        if (list != null && list.size() != 0) {
            int startposition = mDatas.size();
            mDatas.addAll(list);
            notifyItemRangeInserted(startposition, mDatas.size());
        }
    }

    public void clear() {
        int size = mDatas.size();
        if (size != 0) {
            mDatas.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public ViewHolderOwe getViewHolder(View view) {
        return new ViewHolderOwe(view, false);
    }

    @Override
    public ViewHolderOwe onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.owe_list_item, parent, false);
        return new ViewHolderOwe(view, true);
    }

    @Override
    public void onBindViewHolder(OweListAdapter.ViewHolderOwe holder, int position, boolean isItem) {
        if (holder instanceof OweListAdapter.ViewHolderOwe) {
            ViewHolderOwe viewHolderOwe = holder;
            OweItemBean item = (OweItemBean) mDatas.get(position);
            viewHolderOwe.tvHeatYear.setText(item.getYear() + "年度");
            viewHolderOwe.tvOweAmount.setText("￥" + item.getTotal_c());
            viewHolderOwe.tvHeatArea.setText(item.getUseArea() + "㎡");
            viewHolderOwe.tvHeatPrice.setText(item.getPrice() + "㎡/元");
            viewHolderOwe.tvHeatAmount.setText(String.format("%.2f", Double.parseDouble(item.getShouldPay())) + "元");
            viewHolderOwe.tvExtraAmount.setText(item.getSimoney() + "元");
            viewHolderOwe.tvCutAmount.setText(item.getRemission() + "元");
            viewHolderOwe.tvDiscountAmount.setText(item.getConcession() + "元");
            viewHolderOwe.tvOweDays.setText(item.getArrearsDays() + "天");
            viewHolderOwe.tvZnj.setText(item.getLateFee_t() + "元");
            viewHolderOwe.tvPreviousAmount.setText(item.getKnots() + "元");
        }
    }

    @Override
    public int getAdapterItemCount() {
        return mDatas.size();
    }

    class ViewHolderOwe extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_heat_year)
        TextView tvHeatYear;
        @BindView(R.id.tv_owe_amount)
        TextView tvOweAmount;
        @BindView(R.id.tv_heat_area)
        TextView tvHeatArea;
        @BindView(R.id.tv_heat_price)
        TextView tvHeatPrice;
        @BindView(R.id.tv_heat_amount)
        TextView tvHeatAmount;
        @BindView(R.id.tv_extra_amount)
        TextView tvExtraAmount;
        @BindView(R.id.tv_cut_amount)
        TextView tvCutAmount;
        @BindView(R.id.tv_discount_amount)
        TextView tvDiscountAmount;
        @BindView(R.id.tv_owe_days)
        TextView tvOweDays;
        @BindView(R.id.tv_znj)
        TextView tvZnj;
        @BindView(R.id.tv_previous_amount)
        TextView tvPreviousAmount;

        public ViewHolderOwe(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ViewHolderOwe(View itemView, boolean isitem) {
            super(itemView);
            if (isitem) {
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
