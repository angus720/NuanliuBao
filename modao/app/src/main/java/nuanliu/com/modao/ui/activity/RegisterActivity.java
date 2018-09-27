package nuanliu.com.modao.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.base.BaseResponse;
import nuanliu.com.modao.bean.CodeBean;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.utils.IntentUtils;
import nuanliu.com.modao.widget.ClearEditText;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 用户注册
 */

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.cet_user_name)
    ClearEditText cetUserName;
    @BindView(R.id.cet_user_password)
    ClearEditText cetUserPassword;
    @BindView(R.id.cet_confirm_password)
    ClearEditText cetConfirmPassword;
    @BindView(R.id.cet_user_phone)
    ClearEditText cetUserPhone;
    @BindView(R.id.cet_verify_code)
    ClearEditText cetVerifyCode;

    private String userName;
    private String telPhone;
    private String passwd;
    private String confirmPasswd;
    private String veriCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    private void initView() {
        getToolBar().setTitle("注册");
    }

    private void initData() {
        userName = cetUserName.getText().toString().trim() + "";
        telPhone = cetUserPhone.getText().toString().trim() + "";
        passwd = cetUserPassword.getText().toString().trim() + "";
        confirmPasswd = cetConfirmPassword.getText().toString().trim() + "";
        veriCode = cetVerifyCode.getText().toString().trim() + "";
    }

    @OnClick({R.id.tv_get_verify_code, R.id.btn_complete_register, R.id.register_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_verify_code:
                telPhone = cetUserPhone.getText().toString().trim() + "";
                if (null != telPhone) {
                    getVeriCode(telPhone);
                }
                break;
            case R.id.btn_complete_register:
                initData();
                finduser(userName, telPhone);
                break;
            case R.id.register_agreement:
                IntentUtils.startActivity(this, UserAgreementActivity.class);
                break;
        }
    }

    private void finduser(final String userName, final String telPhone) {
        ServiceFactory
                .getApis()
                .finduser(userName, telPhone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse>bindToLifecycle())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse userBaseResponse) {
                        if (userBaseResponse.getStatus() == 1) {
                            if (passwd.equals(confirmPasswd)) {
                                registerUser(userName, passwd, telPhone, veriCode);
                            } else {
                                Toast.makeText(RegisterActivity.this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (userBaseResponse.getStatus_name().contains("手机号已存在")) {
                                Toast.makeText(RegisterActivity.this, "手机号已注册", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void registerUser(String userName, String passwd, String telPhone, String veriCode) {
        ServiceFactory
                .getApis()
                .registerUser(userName, passwd, telPhone, veriCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse>bindToLifecycle())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse userBaseResponse) {
                        if (userBaseResponse.getStatus() == 1) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            IntentUtils.startActivity(RegisterActivity.this, LoginActivity.class);
                        } else {
                            if (userBaseResponse.getStatus_name().contains("验证码超时")) {
                                Toast.makeText(RegisterActivity.this, "验证码超时", Toast.LENGTH_SHORT).show();
                            } else if (userBaseResponse.getStatus_name().contains("验证码错误")) {
                                Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                            } else if (userBaseResponse.getStatus_name().contains("手机号已存在")) {
                                Toast.makeText(RegisterActivity.this, "手机号已注册", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void getVeriCode(String telphone) {
        ServiceFactory
                .getApis()
                .getVeriCode(telphone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse<CodeBean>>bindToLifecycle())
                .subscribe(new Subscriber<BaseResponse<CodeBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse<CodeBean> codeBeanBaseResponse) {
                        if (codeBeanBaseResponse.getStatus() == 1) {
                            Toast.makeText(RegisterActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                            veriCode = codeBeanBaseResponse.getBody().getCode();
                        } else {
                            if (codeBeanBaseResponse.getStatus_name().contains("短信发送失败")) {
                                Toast.makeText(RegisterActivity.this, "短信发送失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
