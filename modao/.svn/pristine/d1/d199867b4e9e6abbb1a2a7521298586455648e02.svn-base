package nuanliu.com.modao.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.base.BaseResponse;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.utils.IntentUtils;
import nuanliu.com.modao.utils.SpUtil;
import nuanliu.com.modao.widget.ClearEditText;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 修改密码
 */

public class ModifyPasswdActivity extends BaseActivity {

    @BindView(R.id.cet_old_passwd)
    ClearEditText cetOldPasswd;
    @BindView(R.id.cet_new_password)
    ClearEditText cetNewPassword;
    @BindView(R.id.cet_confirm_password)
    ClearEditText cetConfirmPassword;

    private String oldPasswd;
    private String newPasswd;
    private String confirmPasswd;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_passwd;
    }

    private void initView() {
        getToolBar().setTitle("修改密码");
    }

    private void initData() {
        oldPasswd = cetOldPasswd.getText().toString().trim();
        newPasswd = cetNewPassword.getText().toString().trim();
        confirmPasswd = cetConfirmPassword.getText().toString().trim();
        userName = SpUtil.getUser().getUsername();
    }

    @OnClick(R.id.btn_save)
    public void onClick() {
        initData();
        if (!oldPasswd.equals(newPasswd)) {
            if (newPasswd.equals(confirmPasswd)) {
                modifyPasswd(userName,oldPasswd, newPasswd);
            } else {
                Toast.makeText(this, "两次输入的密码不相同", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "新密码不能与旧密码相同", Toast.LENGTH_SHORT).show();
        }
    }

    private void modifyPasswd(String userName, String oldPasswd, String newPasswd) {
        ServiceFactory
                .getApis()
                .uppwdbyUsername(userName, oldPasswd,newPasswd)
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
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getStatus() == 1) {
                            Toast.makeText(ModifyPasswdActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                            IntentUtils.startActivity(ModifyPasswdActivity.this,LoginActivity.class);
                        } else {
                            Toast.makeText(ModifyPasswdActivity.this, "修改密码失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
