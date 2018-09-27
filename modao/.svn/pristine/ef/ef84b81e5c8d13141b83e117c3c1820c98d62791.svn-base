package nuanliu.com.modao.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.bean.CommonResponse;
import nuanliu.com.modao.bean.UploadImgRes;
import nuanliu.com.modao.global.App;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.dialog.AppDialog;
import nuanliu.com.modao.ui.dialog.ImageStudioDialog;
import nuanliu.com.modao.utils.StringUtil;
import nuanliu.com.modao.widget.luban.Luban;
import nuanliu.com.modao.widget.luban.OnCompressListener;
import nuanliu.com.modao.widget.photopicker.PhotoPagerActivity;
import nuanliu.com.modao.widget.photopicker.PhotoPicker;
import nuanliu.com.modao.widget.photopicker.PhotoPreview;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 维修评价
 */

public class RepairEvaluationActivity extends BaseActivity {

    @BindView(R.id.tv_order_number)
    TextView tvOrderId;
    @BindView(R.id.tv_order_addr)
    TextView tvOrderAddr;
    @BindView(R.id.tv_order_content)
    TextView tvOrderContent;
    @BindView(R.id.rating)
    RatingBar mRatingBar;
    @BindView(R.id.iv_one)
    net.qiujuer.genius.ui.widget.ImageView mIvOne;
    @BindView(R.id.iv_one_delete)
    ImageView mIvOneDelete;
    @BindView(R.id.iv_two_delete)
    ImageView mIvTwoDelete;
    @BindView(R.id.iv_two)
    net.qiujuer.genius.ui.widget.ImageView mIvTwo;
    @BindView(R.id.iv_three)
    net.qiujuer.genius.ui.widget.ImageView mIvThree;
    @BindView(R.id.iv_three_delete)
    ImageView mIvThreeDelete;
    @BindView(R.id.ll_two_defalut)
    LinearLayout mLlTwoDefalut;
    @BindView(R.id.ll_three_default)
    LinearLayout mLlThreeDefault;
    @BindView(R.id.et_note)
    EditText etNote;
    @BindView(R.id.flowlayout)
    TagFlowLayout tagFlowLayout;
    @BindView(R.id.iv_finished_yes)
    ImageView ivFinishedYes;
    @BindView(R.id.iv_finished_no)
    ImageView ivFinishedNo;

    private TextView tv;

    private LayoutInflater mInflater;
    private ImageStudioDialog mIVStudioDialog;
    private Map<String, String> mParameter;
    private List<String> mListPicpath = new ArrayList<>();
    private List<String> mencodingPic = new ArrayList<>();

    private static final int REQUEST_CODE_PHOTO_PREVIEW = 102;
    private static final int REQUEST_CAMERA_CODE = 103;

    private int isRepair = 0;
    private String orderId;
    private String orderAddr;
    private int category;
    private String orderContent;
    private StringBuffer evaluation = new StringBuffer();
    private int rate;
    private int consequence = 1;
    private String mPictures = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(this);
        initView();
        initData();
        checkPermission();
        initEvent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repair_evaluation;
    }

    private void initView() {
        getToolBar().setTitle("维修评价");
        refreshImageview();
        initFlowLayout();
    }

    private void initData() {
        if (null != getIntent()) {
            orderId = getIntent().getStringExtra("orderId");
            orderAddr = getIntent().getStringExtra("orderAddr");
            category = getIntent().getIntExtra("category", -1);
            switch (category) {
                case 0:
                    orderContent = "暖气不热";
                    break;
                case 1:
                    orderContent = "漏电";
                    break;
                case 2:
                    orderContent = "漏水";
                    break;
                case 3:
                    orderContent = "其他";
                    break;
            }
            tvOrderId.setText(orderId);
            tvOrderAddr.setText(orderAddr);
            tvOrderContent.setText(orderContent);
        }

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate = (int)rating;
//                Toast.makeText(RepairEvaluationActivity.this, "评价了" + rating + "星", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initEvent() {
    }

    @OnClick({R.id.finished_yes, R.id.finished_no, R.id.iv_one, R.id.iv_two, R.id.iv_three, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finished_yes:
                consequence = 1;
                changeImage(ivFinishedYes, ivFinishedNo);
                break;
            case R.id.finished_no:
                consequence = 2;
                changeImage(ivFinishedNo, ivFinishedYes);
                unFinishedDialog();
                break;
            case R.id.iv_one:
                pictureClick(1, "imone.jpg");
                break;
            case R.id.iv_two:
                pictureClick(2, "imTwo.jpg");
                break;
            case R.id.iv_three:
                pictureClick(3, "imthree.jpg");
                break;
            case R.id.btn_submit:
                if (0 != rate) {
                    //通过 picpath 判断图片个数
                    if (mListPicpath.size() == 0) {
                        showLoading("提交中...");
                        evaluateOrder();
                    } else {
                        mencodingPic = null;
                        mencodingPic = new ArrayList<>();

                        for (int i = 0; i < mListPicpath.size(); i++) {
                            compresspic(mListPicpath.get(i));
                        }
                    }
                } else {
                    Toast.makeText(this, "请为维修师傅打分", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void uploadPic(List<String> encoding) {

        showLoading("提交中");

        mParameter = new HashMap();
        mParameter.put("orderid", orderId);
        mParameter.put("act", isRepair + "");
        mParameter.put("maintainerEvaluate", rate + "");
        mParameter.put("serviceEvaluate", rate + "");
        mParameter.put("consequence", consequence + "");
        mParameter.put("evaluate", evaluation.toString());
        mParameter.put("remark", etNote.getText().toString());

        Observer<CommonResponse> observer = new Observer<CommonResponse>() {
            @Override
            public void onCompleted() {
                dismissLoading();
                finish();
            }

            @Override
            public void onError(Throwable e) {
                dismissLoading();
                Toast.makeText(App.getContext(), "error,请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(CommonResponse createWorkOrderBean) {
                if (createWorkOrderBean.getStatus() == 1) {
                    Toast.makeText(App.getContext(), "评价已提交", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(App.getContext(), "请重试", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ServiceFactory.getRepairapis().upLoadImg(encoding)
                .subscribeOn(Schedulers.io())
                .filter(new Func1<UploadImgRes, Boolean>() {
                    @Override
                    public Boolean call(UploadImgRes uploadImgRes) {
                        return uploadImgRes.getStatus() == 1;
                    }
                })
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<UploadImgRes, Observable<CommonResponse>>() {
                    @Override
                    public Observable<CommonResponse> call(UploadImgRes uploadImgRes) {

                        mParameter.put("pictures", uploadImgRes.getBody().getUrl());

                        return ServiceFactory.getRepairapis().evaluationOrder(mParameter);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void changeImage(ImageView iv1, ImageView iv2) {
        iv1.setImageResource(R.mipmap.pay_choose);
        iv2.setImageResource(R.mipmap.pay_unchoose);
    }

    private void unFinishedDialog() {
        new AppDialog(this)
                .builder()
                .setMsg("维修未完成，是否重新报修？")
                .setNegativeButton("否", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isRepair = 0;
                    }
                })
                .setPositiveButton("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isRepair = 1;
                    }
                }).show();
    }

    /**
     * 评价
     */
    private void evaluateOrder() {
        showLoading("提交中");
        mParameter = new HashMap();
        mParameter.put("orderid", orderId);
        mParameter.put("act", isRepair + "");
        mParameter.put("maintainerEvaluate", rate + "");
        mParameter.put("serviceEvaluate", rate + "");
        mParameter.put("consequence", consequence + "");
        mParameter.put("evaluate", evaluation.toString());
        mParameter.put("remark", etNote.getText().toString());
        mParameter.put("pictures", mPictures);

        Subscriber<CommonResponse> baseBeanSubscriber = new Subscriber<CommonResponse>() {
            @Override
            public void onCompleted() {
                dismissLoading();
                finish();
            }

            @Override
            public void onError(Throwable e) {
                dismissLoading();
                Toast.makeText(App.getContext(), "请重试", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNext(CommonResponse createWorkOrderBean) {
                if (createWorkOrderBean.getStatus() == 1) {
                    Toast.makeText(App.getContext(), "评价已提交", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(App.getContext(), "请重试", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ServiceFactory.getRepairapis().evaluationOrder(mParameter)
                .delaySubscription(900, TimeUnit.MICROSECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBeanSubscriber);
    }

    /**
     * 图片点击
     *
     * @param number
     * @param name
     */
    private void pictureClick(int number, String name) {

        if (mListPicpath.size() >= number) {

            //预览+加删除
            Intent intent = new Intent(this, PhotoPagerActivity.class);
            intent.putExtra(PhotoPreview.EXTRA_CURRENT_ITEM, number - 1);
            intent.putStringArrayListExtra(PhotoPreview.EXTRA_PHOTOS, (ArrayList<String>) mListPicpath);
            intent.putExtra(PhotoPreview.EXTRA_SHOW_DELETE, true);
            startActivityForResult(intent, REQUEST_CODE_PHOTO_PREVIEW);
        } else {
            mIVStudioDialog = ImageStudioDialog.newInstance(name, false, 3 - mListPicpath.size());
            mIVStudioDialog.show(getSupportFragmentManager(), name, new ImageStudioDialog.Callback() {
                @Override
                public void callback(List<String> list) {
                    mListPicpath.addAll(list);
                    refreshImageview();
                }
            });
        }
    }

    private void refreshImageview() {

        mIvOne.setVisibility(View.VISIBLE);
        mIvTwo.setVisibility(mListPicpath.size() >= 1 ? View.VISIBLE : View.GONE);
        mLlTwoDefalut.setVisibility(mListPicpath.size() >= 1 ? View.VISIBLE : View.GONE);
        mIvThree.setVisibility(mListPicpath.size() >= 2 ? View.VISIBLE : View.GONE);
        mLlThreeDefault.setVisibility(mListPicpath.size() >= 2 ? View.VISIBLE : View.GONE);

        switch (mListPicpath.size()) {
            case 0:
                mIvOne.setImageBitmap(null);
                mIvTwo.setImageBitmap(null);
                mIvThree.setImageBitmap(null);
                break;
            case 1:
                Glide.with(this).load(mListPicpath.get(0)).into(mIvOne);
                mIvTwo.setImageBitmap(null);
                mIvThree.setImageBitmap(null);
                break;
            case 2:
                Glide.with(this).load(mListPicpath.get(0)).into(mIvOne);
                Glide.with(this).load(mListPicpath.get(1)).into(mIvTwo);
                mIvThree.setImageBitmap(null);
                break;
            case 3:
                Glide.with(this).load(mListPicpath.get(0)).into(mIvOne);
                Glide.with(this).load(mListPicpath.get(1)).into(mIvTwo);
                Glide.with(this).load(mListPicpath.get(2)).into(mIvThree);
                break;
        }
    }

    /**
     * 压缩图片
     *
     * @param path
     */
    private void compresspic(String path) {

        Luban.with(this)
                .load(path)
                .ignoreBy(100)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        String picEncodingToString = StringUtil.encodeBase64File(file);
                        mencodingPic.add(picEncodingToString);
                        if (mencodingPic.size() == mListPicpath.size()) {
                            uploadPic(mencodingPic);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();
    }

    private void checkPermission() {
        //相机
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //进入到这里代表没有权限.
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    //已经禁止提示了
                    Toast.makeText(App.getContext(), "您已禁止该权限，需要重新开启。", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(RepairEvaluationActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_CODE);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PhotoPicker.REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        mListPicpath.addAll(data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS));
                        refreshImageview();
                    }
                }
                break;
            case REQUEST_CODE_PHOTO_PREVIEW:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        mListPicpath.clear();
                        mListPicpath.addAll(data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS));
                        refreshImageview();
                    }
                }
                break;
        }
    }

    private void initFlowLayout() {
        final String[] mVals = new String[]
                {"服务态度好", "很专业", "准备很齐全", "懂的多", "按时到达",
                        "很耐心", "很热情", "速度快，操作熟练"};

        tagFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                tv = (TextView) mInflater.inflate(R.layout.evaluation_tv,
                        tagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                evaluation.setLength(0);
                Iterator<Integer> iter = selectPosSet.iterator();
                while (iter.hasNext()) {
                    int i = iter.next();
                    evaluation.append(mVals[i]).append(";");
                }
                if (evaluation.length() != 0) {
                    evaluation = evaluation.deleteCharAt(evaluation.length() - 1);
                }
                Log.e("evaluation:", evaluation.toString());
            }
        });
    }
}
