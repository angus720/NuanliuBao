package nuanliu.com.modao.ui.fragment;


import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseFragment;
import nuanliu.com.modao.utils.DisplayUtil;
import nuanliu.com.modao.utils.PopupwindowUtils;
import nuanliu.com.modao.widget.TouchTextView;

/**
 * 电子发票
 * A simple {@link Fragment} subclass.
 */
public class ElectronicInvoiceFragment extends BaseFragment {

    @BindView(R.id.tv_buyer_type)
    TextView tvBuyerType;
    @BindView(R.id.tv_rate)
    TextView tvRate;

    private PopupWindow mPopupWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_electronic_invoice;
    }

    @Override
    protected void logicalPro() {

    }

    @OnClick({R.id.trl_rate, R.id.trl_buyer_type, R.id.btn_invoice_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trl_rate:
                break;
            case R.id.trl_buyer_type:
                initPopupWindow();
                break;
            case R.id.btn_invoice_confirm:
                break;
        }
    }

    private void initPopupWindow() {
        if (null == mPopupWindow) {
            mPopupWindow = PopupwindowUtils.initPopupWindow(getActivity(), R.layout.popup_buyer_type_select, DisplayUtil.dip2px(getActivity(), 150), -2);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            TouchTextView item1 = (TouchTextView) mPopupWindow.getContentView().findViewById(R.id.tv_enterprise);
            TouchTextView item2 = (TouchTextView) mPopupWindow.getContentView().findViewById(R.id.tv_government_institution);
            TouchTextView item3 = (TouchTextView) mPopupWindow.getContentView().findViewById(R.id.tv_personal);
            TouchTextView item4 = (TouchTextView) mPopupWindow.getContentView().findViewById(R.id.tv_other);
            ItemClickListener clickListener = new ItemClickListener();
            item1.setOnClickListener(clickListener);
            item2.setOnClickListener(clickListener);
            item3.setOnClickListener(clickListener);
            item4.setOnClickListener(clickListener);
            mPopupWindow.showAsDropDown(tvBuyerType, 0, 10);
        } else {
            mPopupWindow.showAsDropDown(tvBuyerType, 0, 10);
        }
    }

    public class ItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_enterprise:
                    tvBuyerType.setText("企业");
                    break;
                case R.id.tv_government_institution:
                    tvBuyerType.setText("机关事业单位");
                    break;
                case R.id.tv_personal:
                    tvBuyerType.setText("个人");
                    break;
                case R.id.tv_other:
                    tvBuyerType.setText("其他");
                    break;
            }
            mPopupWindow.dismiss();
        }
    }

}
