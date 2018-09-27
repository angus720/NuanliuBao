package nuanliu.com.modao.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.base.BaseResponse;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.MainActivity;
import nuanliu.com.modao.utils.IntentUtils;
import nuanliu.com.modao.utils.SpUtil;
import nuanliu.com.modao.widget.ClearEditText;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 意见反馈
 */

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.tv_text_num)
    TextView tvTextNum;
    @BindView(R.id.cet_feedback)
    ClearEditText etFeedback;

    private static final int MAX_NUM = 300;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    private void initView() {
        getToolBar().setTitle("意见反馈");
        etFeedback.addTextChangedListener(watcher);
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            content = etFeedback.getText().toString();
            tvTextNum.setText(content.length() + "/" + MAX_NUM);
            if (content.length() > MAX_NUM) {
                Toast.makeText(FeedbackActivity.this, "输入内容已达上限", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @OnClick(R.id.btn_commit)
    public void onClick() {
        if (null != content) {
            feedBack(SpUtil.getUser().getTelphone(), content);
        } else {
            Toast.makeText(this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void feedBack(String telPhone, String content) {
        ServiceFactory
                .getApis()
                .feedback(telPhone, content)
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
                            Toast.makeText(FeedbackActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                            IntentUtils.startActivity(FeedbackActivity.this, MainActivity.class);
                        }
                    }
                });
    }

}
