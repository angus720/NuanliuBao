package nuanliu.com.modao.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nuanliu.com.modao.R;
import nuanliu.com.modao.bean.CouponBean;
import nuanliu.com.modao.utils.DialogShow;

public class CouponDialog extends Dialog {

    private ListView listView;
    private Context mContext;
    private List<CouponBean> couponBeans;
    private String couponId;
    private CouponSelectAdapter mAdapter;
    private CouponSelectListener couponSelectListener;

    public static final String COUPON_NOT_USE = "2";

    private RelativeLayout rl_coupon_not_use;
    private Button confirmBtn;
    private ImageView iv_coupon_not_use;

    public CouponDialog(@NonNull Context context, List<CouponBean> couponBeans, String couponId) {
        super(context, R.style.popupDialog);
        mContext = context;
        this.couponBeans = couponBeans;
        this.couponId = couponId;
        DialogShow.windowDeploy(this);
    }

    public CouponDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_coupon_info);
        View footView = LayoutInflater.from(getContext()).inflate(R.layout.coupon_not_use_item, null);
        rl_coupon_not_use = (RelativeLayout) footView.findViewById(R.id.rl_coupon_not_use);
        iv_coupon_not_use = (ImageView) footView.findViewById(R.id.iv_not_use_coupon);
        confirmBtn = (Button) footView.findViewById(R.id.coupon_confirm);
        initEvent();
        listView = (ListView) findViewById(R.id.coupon_listview);
        mAdapter = new CouponSelectAdapter();
        listView.setAdapter(mAdapter);
        listView.addFooterView(footView);
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(listClickListener);
    }

    private void initEvent() {
        rl_coupon_not_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_coupon_not_use.setImageResource(R.mipmap.pay_choose);
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    class CouponSelectAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return couponBeans == null ? 0 : couponBeans.size();
        }

        @Override
        public Object getItem(int i) {
            return couponBeans == null ? null : couponBeans.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(mContext).inflate(R.layout.coupon_select_item, null);
            TextView couponName = (TextView) view.findViewById(R.id.coupon_name);
            ImageView ivCouponCheck = (ImageView) view.findViewById(R.id.iv_coupon_check);
            CouponBean couponBean = couponBeans.get(i);
            couponName.setText(couponBean.getCouponName());
            if (couponBean.getCouponId().equals(couponId)) {
                ivCouponCheck.setImageResource(R.mipmap.pay_choose);
                iv_coupon_not_use.setImageResource(R.mipmap.pay_unchoose);
            } else {
                ivCouponCheck.setImageResource(R.mipmap.pay_unchoose);
                iv_coupon_not_use.setImageResource(R.mipmap.pay_choose);
            }
            return view;
        }
    }

    AdapterView.OnItemClickListener listClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (null != couponSelectListener) {
                couponSelectListener.chooseCoupon(i);
            }
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         **/

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }

    public void setCouponSelectListener(CouponSelectListener couponSelectListener) {
        this.couponSelectListener = couponSelectListener;
    }

    public interface CouponSelectListener {
        void chooseCoupon(int pos);
    }
}
