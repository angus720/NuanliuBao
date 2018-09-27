package nuanliu.com.modao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseResponse;
import nuanliu.com.modao.bean.MaintenanceOrderBean;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.activity.OrderDetailActivity;
import nuanliu.com.modao.ui.activity.RepairEvaluationActivity;
import nuanliu.com.modao.ui.dialog.AppDialog;
import nuanliu.com.modao.widget.refreshview.recyclerview.BaseRecyclerAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RepariOrderAdapter<T> extends BaseRecyclerAdapter<RepariOrderAdapter.ViewHolderOrder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<T> mDatas = new ArrayList<>();
    private SimpleDateFormat simpleDateFormat;
    private CancelOrderListner cancelOrderListner;

    public RepariOrderAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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

    public void delete(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        if (position <= mDatas.size() - 1) {
            notifyItemChanged(position);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderOrder getViewHolder(View view) {
        return new ViewHolderOrder(view, false);
    }

    @Override
    public ViewHolderOrder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.repair_order_list_item, parent, false);
        return new ViewHolderOrder(view, true);
    }

    @Override
    public void onBindViewHolder(RepariOrderAdapter.ViewHolderOrder holder, int position, boolean isItem) {
        if (holder instanceof RepariOrderAdapter.ViewHolderOrder) {
            ViewHolderOrder viewHolderOrder = holder;
            MaintenanceOrderBean item = (MaintenanceOrderBean) mDatas.get(position);
            viewHolderOrder.tvRepairAddress.setText(item.getAddress());
            viewHolderOrder.tvRepairTime.setText(simpleDateFormat.format(new Date(item.getTime() * 1000L)));
            int status = item.getStatus();
            viewHolderOrder.ivFinishedFlag.setVisibility((status == 4 || status == 5) ? View.VISIBLE : View.GONE);
            viewHolderOrder.tvType.setTextColor((status == 3 || status == 4 || status == 5) ? mContext.getResources().getColor(R.color.charactertintgray) : mContext.getResources().getColor(R.color.orange));
            viewHolderOrder.tvDelOrder.setVisibility((status == 3 || status == 4 || status == 5) ? View.VISIBLE : View.GONE);
            viewHolderOrder.tvRepariStatus.setVisibility((status == 0 || status == 3) ? View.VISIBLE : View.GONE);
            viewHolderOrder.tvRepariStatus.setText((status == 0) ? "撤销报修" : "待评价");
            switch (item.getCategory()) {
                case 0:
                    viewHolderOrder.tvRepairReason.setText("【暖气不热】");
                    break;
                case 1:
                    viewHolderOrder.tvRepairReason.setText("【漏电】");
                    break;
                case 2:
                    viewHolderOrder.tvRepairReason.setText("【漏水】");
                    break;
                case 3:
                    viewHolderOrder.tvRepairReason.setText("【其他】");
                    break;
            }
            switch (status) {
                case 0:
                    viewHolderOrder.tvType.setText("待处理");
                    break;
                case 1:
                case 2:
                    viewHolderOrder.tvType.setText("处理中");
                    break;
                case 3:
                    viewHolderOrder.tvType.setText("已完工");
                    break;
                case 4:
                case 5:
                    viewHolderOrder.tvType.setText("已完结");
                    break;
            }
        }
    }

    @Override
    public int getAdapterItemCount() {
        return mDatas.size();
    }

    class ViewHolderOrder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_repair_address)
        TextView tvRepairAddress;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_repair_reason)
        TextView tvRepairReason;
        @BindView(R.id.tv_repair_time)
        TextView tvRepairTime;
        @BindView(R.id.tv_delete_order)
        TextView tvDelOrder;
        @BindView(R.id.tv_repair_status)
        TextView tvRepariStatus;
        @BindView(R.id.iv_finished_flag)
        ImageView ivFinishedFlag;

        public ViewHolderOrder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ViewHolderOrder(View itemView, boolean isitem) {
            super(itemView);
            if (isitem) {
                ButterKnife.bind(this, itemView);
            }
        }

        @OnClick({R.id.ll_order_item, R.id.tv_delete_order, R.id.tv_repair_status})
        public void onClick(View view) {
            final MaintenanceOrderBean bean = (MaintenanceOrderBean) mDatas.get(getLayoutPosition());
            switch (view.getId()) {
                case R.id.ll_order_item:
                    Intent intent1 = new Intent(mContext, OrderDetailActivity.class);
                    intent1.putExtra("orderId", bean.getOrderid());
                    intent1.putExtra("orderAddr", bean.getAddress());
                    intent1.putExtra("category", bean.getCategory());
                    mContext.startActivity(intent1);
                    break;
                case R.id.tv_delete_order:
                    new AppDialog(mContext).builder()
                            .setMsg("确定要删除该工单吗？")
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deleteOrder(bean.getOrderid(), getLayoutPosition());
                                }
                            }).show();
                    break;
                case R.id.tv_repair_status:
                    switch (bean.getStatus()) {
                        case 0:
                            new AppDialog(mContext).builder()
                                    .setTitle("温馨提示")
                                    .setMsg("报修单已生成，确定撤销报修？")
                                    .setNegativeButton("取消", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    })
                                    .setPositiveButton("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (null != cancelOrderListner) {
                                                cancelOrderListner.onCancel(bean.getOrderid(), "", "用户取消", getLayoutPosition());
                                            }
                                        }
                                    }).show();
                            break;
                        case 3:
                            Intent intent = new Intent(mContext, RepairEvaluationActivity.class);
                            intent.putExtra("orderId", bean.getOrderid());
                            intent.putExtra("orderAddr", bean.getAddress());
                            intent.putExtra("category", bean.getCategory());
                            mContext.startActivity(intent);
                            break;
                    }
                    break;
            }
        }
    }

    private void deleteOrder(String orderId, final int position) {
        ServiceFactory
                .getRepairapis()
                .deleteOrder(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getStatus() == 1) {
                            delete(position);
                        }
                    }
                });
    }

    public interface CancelOrderListner {
        void onCancel(String orderId, String description, String cancelType, int position);
    }

    public void onCancleClick(CancelOrderListner cancelOrderListner) {
        this.cancelOrderListner = cancelOrderListner;
    }

}
