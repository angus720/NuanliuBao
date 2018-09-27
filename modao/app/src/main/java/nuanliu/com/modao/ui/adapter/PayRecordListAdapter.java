package nuanliu.com.modao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.bean.PayRecordItemBean;
import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.ui.activity.BillDetailActivity;
import nuanliu.com.modao.widget.refreshview.recyclerview.BaseRecyclerAdapter;

public class PayRecordListAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<PayRecordItemBean> mDatas;
    private SimpleDateFormat simpleDateFormat;

    public static final int YEAR_AMOUNT = 1;//头部年度和总金额
    public static final int BILL_DETAIL = 2;//具体缴费item

    public PayRecordListAdapter(Context mContext, List<PayRecordItemBean> list) {
        this.mContext = mContext;
        this.mDatas = list;
        mInflater = LayoutInflater.from(mContext);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    @Override
    public int getAdapterItemViewType(int position) {
        if (mDatas.get(position) == null) {
            return YEAR_AMOUNT;
        }
        return mDatas.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new ViewHolderRecord(view, false);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        RecyclerView.ViewHolder holder = null;
        if (YEAR_AMOUNT == viewType) {
            View view = mInflater.inflate(R.layout.item_pay_years_list, parent, false);
            holder = new ViewHolderYear(view, true);
        } else if (BILL_DETAIL == viewType) {
            View view = mInflater.inflate(R.layout.item_pay_record, parent, false);
            holder = new ViewHolderRecord(view, true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, boolean isItem) {
        PayRecordItemBean item = mDatas.get(position);
        switch (getItemViewType(position)) {
            case YEAR_AMOUNT:
                ViewHolderYear holderYear = (ViewHolderYear) holder;
                holderYear.payYears.setText(item.getYear() + "年");
                holderYear.payAllMoney.setText("支出￥：" + String.valueOf(item.getAmount_t()));
                break;
            case BILL_DETAIL:
                ViewHolderRecord holderOrder = (ViewHolderRecord) holder;
                holderOrder.payUser.setText(item.getHouseholder());
                holderOrder.userAddress.setText(item.getAddre());
                holderOrder.recordNumber.setText(item.getId());
                holderOrder.recordItemMoney.setText("-" + String.valueOf(item.getAmount()));
                holderOrder.payTime.setText(simpleDateFormat.format(new Date(item.getTime())));
                break;
        }
    }

    @Override
    public int getAdapterItemCount() {
        return mDatas.size();
    }

    public void addData(List<PayRecordItemBean> list) {
        if (list != null && list.size() != 0) {
            mDatas.addAll(list);
        }
    }


    class ViewHolderRecord extends RecyclerView.ViewHolder {

        @BindView(R.id.pay_user)
        TextView payUser;
        @BindView(R.id.record_number)
        TextView recordNumber;
        @BindView(R.id.record_item_money)
        TextView recordItemMoney;
        @BindView(R.id.user_address)
        TextView userAddress;
        @BindView(R.id.pay_time)
        TextView payTime;

        public ViewHolderRecord(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ViewHolderRecord(View itemView, boolean isitem) {
            super(itemView);
            if (isitem) {
                ButterKnife.bind(this, itemView);
            }
        }

        @OnClick({R.id.item_pay_record})
        public void onClick(View view) {
            PayRecordItemBean bean = mDatas.get(getLayoutPosition());
            switch (view.getId()) {
                case R.id.item_pay_record:
                    Intent intent = new Intent(mContext, BillDetailActivity.class);
                    intent.putExtra(Fields.BILL_ID, bean.getId());
                    mContext.startActivity(intent);
                    break;
            }
        }
    }

    class ViewHolderYear extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_year_total)
        LinearLayout llYearTotal;
        @BindView(R.id.pay_years)
        TextView payYears;
        @BindView(R.id.pay_all_money)
        TextView payAllMoney;

        public ViewHolderYear(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ViewHolderYear(View itemView, boolean isitem) {
            super(itemView);
            if (isitem) {
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
