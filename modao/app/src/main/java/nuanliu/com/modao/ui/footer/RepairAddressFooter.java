package nuanliu.com.modao.ui.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import nuanliu.com.modao.R;
import nuanliu.com.modao.ui.activity.AddRepairAddressActivity;
import nuanliu.com.modao.utils.IntentUtils;
import nuanliu.com.modao.widget.refreshview.XRefreshView;
import nuanliu.com.modao.widget.refreshview.callback.IFooterCallBack;

public class RepairAddressFooter extends LinearLayout implements IFooterCallBack {

    private Context mContext;
    private View mContentView;
    private LinearLayout otherAddress;
    private boolean showing = true;

    public RepairAddressFooter(Context context) {
        super(context);
        initView(context);
    }

    public RepairAddressFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        ViewGroup moreView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.add_repair_address_footer, this);
        moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mContentView = moreView.findViewById(R.id.add_repair_addr_content);
        otherAddress = (LinearLayout) moreView.findViewById(R.id.ll_other_address);
    }

    @Override
    public void callWhenNotAutoLoadMore(XRefreshView xRefreshView) {
        otherAddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(mContext, AddRepairAddressActivity.class);
            }
        });
    }

    @Override
    public void onStateReady() {

    }

    @Override
    public void onStateRefreshing() {

    }

    @Override
    public void onReleaseToLoadMore() {

    }

    @Override
    public void onStateFinish(boolean hidefooter) {

    }

    @Override
    public void onStateComplete() {

    }

    @Override
    public void show(boolean show) {
        if (show == showing) {
            return;
        }
        showing = show;
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        lp.height = show ? LayoutParams.WRAP_CONTENT : 0;
        mContentView.setLayoutParams(lp);
    }

    @Override
    public boolean isShowing() {
        return showing;
    }

    @Override
    public int getFooterHeight() {
        return getMeasuredHeight();
    }
}
