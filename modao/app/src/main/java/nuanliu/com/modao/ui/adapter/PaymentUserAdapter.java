package nuanliu.com.modao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseResponse;
import nuanliu.com.modao.bean.ResidentItemBean;
import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.activity.EditResidentInfoActivity;
import nuanliu.com.modao.ui.dialog.AppDialog;
import nuanliu.com.modao.utils.SpUtil;
import nuanliu.com.modao.widget.refreshview.recyclerview.BaseRecyclerAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 缴费用户列表Adapter
 */

public class PaymentUserAdapter<T> extends BaseRecyclerAdapter<PaymentUserAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<T> mDatas = new ArrayList<>();

    private ItemClickListener mItemClickListener;

    public PaymentUserAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
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
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view, false);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_payment_user, parent, false);
        return new ViewHolder(view, true);
    }

    @Override
    public void onBindViewHolder(PaymentUserAdapter.ViewHolder holder, int position, boolean isItem) {
        if (holder instanceof PaymentUserAdapter.ViewHolder) {
            ViewHolder viewHolder = holder;
            ResidentItemBean item = (ResidentItemBean) mDatas.get(position);
            viewHolder.userName.setText(item.getHouseholder());
            viewHolder.userCardNumber.setText(item.getAccount_h());
            viewHolder.userAddress.setText(item.getAddress());
        }
    }

    @Override
    public int getAdapterItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_card)
        TextView userCardNumber;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.user_address)
        TextView userAddress;
        @BindView(R.id.go_next)
        ImageView goNext;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ViewHolder(View itemView, boolean isitem) {
            super(itemView);
            if (isitem) {
                ButterKnife.bind(this, itemView);
            }
        }

        @OnClick({R.id.item_user, R.id.ll_edit_resident, R.id.ll_delete_resident})
        public void onClick(View view) {
            final ResidentItemBean bean = (ResidentItemBean) mDatas.get(getLayoutPosition());
            switch (view.getId()) {
                case R.id.item_user:
                    if (null != mItemClickListener) {
                        mItemClickListener.onItemClick(bean, getLayoutPosition());
                    }
                    break;
                case R.id.ll_edit_resident:
                    Intent intent1 = new Intent(mContext, EditResidentInfoActivity.class);
                    intent1.putExtra(Fields.RESIDENT_ITEM, bean);
                    mContext.startActivity(intent1);
                    break;
                case R.id.ll_delete_resident:
                    new AppDialog(mContext).builder()
                            .setMsg("确定要删除该住户吗？")
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    delResident(SpUtil.getUser().getUsername(), bean.getResidentid(), getLayoutPosition());
                                }
                            }).show();
                    break;
            }
        }
    }

    private void delResident(String userName, String id, final int position) {
        ServiceFactory
                .getApis()
                .delbdResident(userName, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

    public interface ItemClickListener {
        void onItemClick(ResidentItemBean item, int position);
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }
}
