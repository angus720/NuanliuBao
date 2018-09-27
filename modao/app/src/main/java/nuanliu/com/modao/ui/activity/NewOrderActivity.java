package nuanliu.com.modao.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.bean.CommonResponse;
import nuanliu.com.modao.bean.UploadImgRes;
import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.global.App;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.adapter.RepairReasonAdapter;
import nuanliu.com.modao.ui.dialog.ImageStudioDialog;
import nuanliu.com.modao.utils.PopupwindowUtils;
import nuanliu.com.modao.utils.StringUtil;
import nuanliu.com.modao.widget.TouchRelativeLayout;
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
 * 报修单
 */

public class NewOrderActivity extends BaseActivity {

    @BindView(R.id.tv_order_content)
    TextView tvOrderContent;
    @BindView(R.id.et_note)
    EditText etNote;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_choose_man)
    ImageView ivMan;
    @BindView(R.id.iv_choose_women)
    ImageView ivWomen;
    @BindView(R.id.tv_appoint_time)
    TextView tvAppointTime;
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
    @BindView(R.id.trl_choose_content)
    TouchRelativeLayout trlChooseContent;

    private PopupWindow mPopupWindow;
    private ArrayList list = new ArrayList();
    private ListView reason_list;
    private RepairReasonAdapter mAdapter;
    private LinearLayout llBlank;
    private ImageStudioDialog mIVStudioDialog;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private List<String> mListPicpath = new ArrayList<>();
    private List<String> mencodingPic = new ArrayList<>();
    private Map<String, String> mParameter;
    private String mAddress;
    private String residentId;
    private String mCategory;
    private String orderReason;
    private String mTelephone;
    private String mContent;
    private String mDescription = "";
    private String mPictures = "";
    private String mContact;
    private String mAppointTime;
    private int gender;
    private int state = 1;

    private static final int REQUEST_CODE_REPAIR_ADDRESS = 101;
    private static final int REQUEST_CODE_PHOTO_PREVIEW = 102;
    private static final int REQUEST_CAMERA_CODE = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
        checkPermission();
        initPop();
        initDate();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_order;
    }

    private void initView() {
        getToolBar().setTitle("报修单");
        refreshImageview();
    }

    private void initEvent() {

        final TextWatcher mTextWatcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        etNote.addTextChangedListener(mTextWatcher);
        etNote.setFilters(new InputFilter[]{new InputFilter.LengthFilter(300)});//设置长度
    }

    @OnClick({R.id.trl_choose_content, R.id.iv_one, R.id.iv_two, R.id.iv_three, R.id.gender_man, R.id.gender_women, R.id.trl_add_address, R.id.trl_appointment_time, R.id.btn_confirm_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trl_choose_content:
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                } else {
                    PopupwindowUtils.showAsDropDown(trlChooseContent, mPopupWindow);
                }
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
            case R.id.gender_man:
                changeImage(ivMan, ivWomen);
                gender = 1;
                break;
            case R.id.gender_women:
                changeImage(ivWomen, ivMan);
                gender = 2;
                break;
            case R.id.trl_add_address:
                Intent intent = new Intent(this, RepairAddressActivity.class);
                intent.putExtra("state", state);
                startActivityForResult(intent, REQUEST_CODE_REPAIR_ADDRESS);
                break;
            case R.id.trl_appointment_time:
                showTime();
                break;
            case R.id.btn_confirm_submit:
                getData();
                if (null != mContact && null != mTelephone &&
                        !mAddress.equals("请添加地址") &&
                        !mAppointTime.equals("预约时间")) {

                    //通过 picpath 判断图片个数
                    if (mListPicpath.size() == 0) {
                        showLoading("提交中...");
                        createWorkOrder();
                    } else {
                        mencodingPic = null;
                        mencodingPic = new ArrayList<>();

                        for (int i = 0; i < mListPicpath.size(); i++) {
                            compresspic(mListPicpath.get(i));
                        }
                    }
                } else {
                    Toast.makeText(this, "请正确填写信息", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_order_reason_select, null);
        mAdapter = new RepairReasonAdapter(this, list);
        reason_list = (ListView) view.findViewById(R.id.reason_list);
        llBlank = (LinearLayout) view.findViewById(R.id.ll_blank);
        reason_list.setAdapter(mAdapter);
        mPopupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setContentView(view);

        reason_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvOrderContent.setText(String.valueOf(list.get(position)));
                mPopupWindow.dismiss();
            }
        });
        llBlank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }

    private void initDate() {
        list.add("暖气不热");
        list.add("漏电");
        list.add("漏水");
        list.add("其他");
    }

    private void showTime() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 1);//设置起始年份
        Calendar endDate = Calendar.getInstance();
        endDate.set(2025, 1, 1);//设置结束年份
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String format = simpleDateFormat.format(date);
                if (date.getTime() >= new Date().getTime()) {
                    tvAppointTime.setText(format);
                    tvAppointTime.setTextColor(getResources().getColor(R.color.black));
                } else {
                    Toast.makeText(NewOrderActivity.this, "请重新设置时间", Toast.LENGTH_SHORT).show();
                }
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(R.color.black)//标题文字颜色
                .setSubmitColor(R.color.black)//确定按钮文字颜色
                .setCancelColor(R.color.black)//取消按钮文字颜色
//                .setTitleBgColor(R.color.white)//标题背景颜色 Night mode
//                .setBgColor(R.color.white)//滚轮背景颜色 Night mode
//                .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
        pvTime.show();
    }

    private void changeImage(ImageView iv1, ImageView iv2) {
        iv1.setImageResource(R.mipmap.pay_choose);
        iv2.setImageResource(R.mipmap.pay_unchoose);
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

    private void getData() {
        mContent = etNote.getText().toString().trim();
        mAddress = tvAddress.getText().toString();
        mTelephone = etPhone.getText().toString();
        mAppointTime = tvAppointTime.getText().toString();
        mContact = etContact.getText().toString();
        if (gender == 1) {
            mContact = mContact + "先生";
        } else if (gender == 2) {
            mContact = mContact + "女士";
        }
        orderReason = tvOrderContent.getText().toString();
        if (orderReason.equals("暖气不热")) {
            mCategory = "0";
        } else if (orderReason.equals("漏电")) {
            mCategory = "1";
        } else if (orderReason.equals("漏水")) {
            mCategory = "2";
        } else if (orderReason.equals("其他")) {
            mCategory = "3";
        }
    }

    /**
     * 上传图片 并创建工单
     *
     * @param encoding
     */
    private void uploadPic(List<String> encoding) {

        showLoading("工单生成中");

        mParameter = new HashMap();
        mParameter.put("source", 4 + "");
        mParameter.put("kind", 3 + "");
        mParameter.put("type", 3 + "");
        mParameter.put("address", mAddress);
        mParameter.put("telephone", mTelephone);
        mParameter.put("content", mContent);
        mParameter.put("category", mCategory);
        mParameter.put("comment", "");
        mParameter.put("description", mDescription);
        mParameter.put("contacts", mContact);
        mParameter.put("appointTime", mAppointTime);
        mParameter.put("residentid", residentId);
        mParameter.put("projectid", "");
        mParameter.put("voice", "");

        Observer<CommonResponse> observer = new Observer<CommonResponse>() {
            @Override
            public void onCompleted() {
                dismissLoading();
                finish();
                clearViewData();
            }

            @Override
            public void onError(Throwable e) {
                dismissLoading();
                Toast.makeText(App.getContext(), "error,请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(CommonResponse createWorkOrderBean) {
                if (createWorkOrderBean.getStatus() == 1) {
                    Toast.makeText(App.getContext(), "报修成功", Toast.LENGTH_SHORT).show();
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

                        return ServiceFactory.getRepairapis().createWorkOrder(mParameter);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
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

    /**
     * 生成工单请求
     */
    private void createWorkOrder() {

        showLoading("工单生成中");
        //     * "address":"地址",
        //     * "telephone":"联系电话",
        //     * "content":"维修内容",
        //     * "description":"故障描述",
        //     * "contact":"联系人"
        //     * "appointTime":"预约时间"
        //     * "pictures":"照片"
        //      "category" : 不热(0), | 漏电(1), |漏水(2),  |其他(3);
        //      "source":  系统警报(1), 用户报修(2), 电话(3), APP(4), 维修公众号(5);
        //      "kind": 1[维修] | 2[检修] | 3[报修]
        //      "type": 1[锅炉房] | 2[管井] | 3[住户]
        //      residentid 住户id
        //       "voice":"音频",
        //       "comment":"备注"
        mParameter = new HashMap();
        mParameter.put("source", 4 + "");
        mParameter.put("kind", 3 + "");
        mParameter.put("type", 3 + "");
        mParameter.put("address", mAddress);
        mParameter.put("telephone", mTelephone);
        mParameter.put("category", mCategory);
        mParameter.put("content", mContent);
        mParameter.put("comment", "");
        mParameter.put("residentid", residentId);
        mParameter.put("description", mDescription);
        mParameter.put("contacts", mContact);
        mParameter.put("appointTime", mAppointTime);
        mParameter.put("pictures", mPictures);
        mParameter.put("projectid", "");
        mParameter.put("voice", "");

        Subscriber<CommonResponse> baseBeanSubscriber = new Subscriber<CommonResponse>() {
            @Override
            public void onCompleted() {
                dismissLoading();
                finish();
                clearViewData();
            }

            @Override
            public void onError(Throwable e) {
                dismissLoading();
                Toast.makeText(App.getContext(), "请重试", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNext(CommonResponse createWorkOrderBean) {
                if (createWorkOrderBean.getStatus() == 1) {
                    Toast.makeText(App.getContext(), "报修成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(App.getContext(), "请重试", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ServiceFactory.getRepairapis().createWorkOrder(mParameter)
                .delaySubscription(900, TimeUnit.MICROSECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBeanSubscriber);
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
                    ActivityCompat.requestPermissions(NewOrderActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_CODE);
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
            case REQUEST_CODE_REPAIR_ADDRESS:
                if (resultCode == RESULT_OK) {
                    if (data.hasExtra(Fields.CHOOSE_ADDRESS)) {
                        mAddress = data.getStringExtra(Fields.CHOOSE_ADDRESS);
                        residentId = data.getStringExtra(Fields.RESIDENT_ID);
                        tvAddress.setText(mAddress);
                        tvAddress.setTextColor(getResources().getColor(R.color.black));
                    }
                }
                break;
        }
    }

    /**
     * 提交后清空
     */
    private void clearViewData() {
        etNote.setText("");
        etContact.setText("");
        etPhone.setText("");
        tvAppointTime.setText("预约时间");
        tvAddress.setText("请添加地址");
    }
}
