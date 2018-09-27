package nuanliu.com.modao.ui.fragment;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseFragment;
import nuanliu.com.modao.bean.AddressItemBean;
import nuanliu.com.modao.ui.activity.MyAddressActivity;
import nuanliu.com.modao.utils.DisplayUtil;
import nuanliu.com.modao.utils.PopupwindowUtils;
import nuanliu.com.modao.widget.TouchTextView;

import static android.app.Activity.RESULT_OK;

/**
 * 纸质发票Fragment
 * A simple {@link Fragment} subclass.
 */
public class PaperInvoiceFragment extends BaseFragment {

    @BindView(R.id.btn_oneself_pick)
    Button btnOneselfPick;
    @BindView(R.id.btn_mail)
    Button btnMail;
    @BindView(R.id.btn_artifical_served)
    Button btnArtificalServed;
    @BindView(R.id.tv_buyer_type)
    TextView tvBuyerType;
    @BindView(R.id.tv_choose_address)
    TextView tvChooseAddress;

    private PopupWindow mPopupWindow;

    private int flag = 1;
    private static final int REQUEST_MAIL_ADDRESS = 108;
    private AddressItemBean item;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_paper_invoice;
    }

    @Override
    protected void logicalPro() {

    }

    @OnClick({R.id.btn_oneself_pick, R.id.btn_mail, R.id.btn_artifical_served, R.id.trl_choose_address, R.id.trl_buyer_type, R.id.btn_invoice_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_oneself_pick:
                changeColorAndBg(btnOneselfPick, btnMail, btnArtificalServed);
                break;
            case R.id.btn_mail:
                changeColorAndBg(btnMail, btnOneselfPick, btnArtificalServed);
                break;
            case R.id.btn_artifical_served:
                changeColorAndBg(btnArtificalServed, btnOneselfPick, btnMail);
                break;
            case R.id.trl_choose_address:
                Intent intent = new Intent(getActivity(), MyAddressActivity.class);
                intent.putExtra("flag", flag);
                startActivityForResult(intent, REQUEST_MAIL_ADDRESS);
                break;
            case R.id.trl_buyer_type:
                initPopupWindow();
                break;
            case R.id.btn_invoice_confirm:
                break;
        }
    }

    private void changeColorAndBg(Button btn1, Button btn2, Button btn3) {
        btn1.setTextColor(getResources().getColor(R.color.white));
        btn1.setBackgroundResource(R.drawable.radio_button_select_bg);
        btn2.setTextColor(getResources().getColor(R.color.orange));
        btn2.setBackgroundResource(R.drawable.btn_select_bg);
        btn3.setTextColor(getResources().getColor(R.color.orange));
        btn3.setBackgroundResource(R.drawable.btn_select_bg);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_MAIL_ADDRESS:
                if (resultCode == RESULT_OK) {
                    if (data.hasExtra("mailAddress")) {
                        item = (AddressItemBean) data.getSerializableExtra("mailAddress");
                        tvChooseAddress.setText(item.getProvice() + item.getCity() + item.getDistrict() + item.getDetailed());
                    }
                }
                break;
        }
    }
}
