package nuanliu.com.modao.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.ui.fragment.ElectronicInvoiceFragment;
import nuanliu.com.modao.ui.fragment.PaperInvoiceFragment;

/**
 * 发票信息
 */

public class InvoiceInfoActivity extends BaseActivity {


    @BindView(R.id.rb_paper_invoice)
    RadioButton rbPaperInvoice;
    @BindView(R.id.rb_electronic_invoice)
    RadioButton rbElectronicInvoice;
    @BindView(R.id.rg_invoice)
    RadioGroup rgInvoice;
    @BindView(R.id.fl_invoice_info)
    FrameLayout flInvoiceInfo;

    private PaperInvoiceFragment paperInvoiceFragment;
    private ElectronicInvoiceFragment electronicInvoiceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invoice_info;
    }

    private void initView() {
        getToolBar().setTitle("发票信息");
    }

    /**
     * 顶部按钮点击切换Fragment
     */
    private void initEvent() {
        rgInvoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                hideFrament(fragmentTransaction);

                switch (checkedId) {
                    case R.id.rb_paper_invoice:
                        if (null == paperInvoiceFragment) {
                            paperInvoiceFragment = new PaperInvoiceFragment();
                            fragmentTransaction.add(R.id.fl_invoice_info, paperInvoiceFragment, "纸质发票");
                        } else {
                            fragmentTransaction.show(paperInvoiceFragment);
                        }
                        changeColorAndBg(rbPaperInvoice, rbElectronicInvoice);
                        break;
                    case R.id.rb_electronic_invoice:
                        if (null == electronicInvoiceFragment) {
                            electronicInvoiceFragment = new ElectronicInvoiceFragment();
                            fragmentTransaction.add(R.id.fl_invoice_info, electronicInvoiceFragment, "电子发票");
                        } else {
                            fragmentTransaction.show(electronicInvoiceFragment);
                        }
                        changeColorAndBg(rbElectronicInvoice, rbPaperInvoice);
                        break;
                }
                fragmentTransaction.commit();
            }
        });
        rbPaperInvoice.setChecked(true);
    }

    private void changeColorAndBg(RadioButton rb1, RadioButton rb2) {
        rb1.setTextColor(getResources().getColor(R.color.white));
        rb1.setBackgroundResource(R.drawable.radio_button_select_bg);
        rb2.setTextColor(getResources().getColor(R.color.black));
        rb2.setBackgroundResource(R.drawable.radio_button_unselect_bg);
    }

    /**
     * 隐藏显示fragment
     *
     * @param fragmentTransaction
     */

    private void hideFrament(FragmentTransaction fragmentTransaction) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        paperInvoiceFragment = (PaperInvoiceFragment) supportFragmentManager.findFragmentByTag("纸质发票");
        electronicInvoiceFragment = (ElectronicInvoiceFragment) supportFragmentManager.findFragmentByTag("电子发票");
        if (paperInvoiceFragment != null) {
            fragmentTransaction.hide(paperInvoiceFragment);
        }
        if (electronicInvoiceFragment != null) {
            fragmentTransaction.hide(electronicInvoiceFragment);
        }
    }
}
