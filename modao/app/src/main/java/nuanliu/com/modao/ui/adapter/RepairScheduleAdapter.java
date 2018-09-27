package nuanliu.com.modao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.bean.RepairOrderProgressBean;
import nuanliu.com.modao.bean.RepairScheduleBean;
import nuanliu.com.modao.ui.dialog.AppDialog;
import nuanliu.com.modao.ui.dialog.ChooseMapDialog;
import nuanliu.com.modao.utils.MySpannableString;

public class RepairScheduleAdapter<T> extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<T> mDatas = new ArrayList<>();
    private MySpannableString mySpannableString;
    private List<RepairOrderProgressBean.BodyBean.PostionBean> list;
    private String content;

    public RepairScheduleAdapter(Context context, List<RepairOrderProgressBean.BodyBean.PostionBean> list) {
        mContext = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    public void addData(List<T> list) {

        if (list != null && list.size() != 0) {
            int startposition = mDatas.size();
            mDatas.addAll(list);
            notifyItemRangeInserted(startposition, mDatas.size());
        }
    }

    public void clear() {

        int size = mDatas.size();
        if (size != 0) {
            mDatas.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    private void delete(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        if (position < mDatas.size() - 1) {
            notifyItemChanged(position);
        }
    }

    public void refreshData(List list) {
        if (list != null && list.size() != 0) {
            clear();
            addData(list);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mInflater.inflate(R.layout.item_repair_schedule, parent, false);
        return new ViewHolderSchedule(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RepairScheduleAdapter.ViewHolderSchedule) {
            ViewHolderSchedule holderSchedule = (ViewHolderSchedule) holder;
            List<RepairScheduleBean> mdata = (List<RepairScheduleBean>) mDatas;
            RepairScheduleBean bean = mdata.get(position);

            holderSchedule.mViewLineTop.setBackgroundColor(ContextCompat.getColor(mContext, position == 0 ? R.color.white : R.color.tint_gray_line));
            holderSchedule.mIvSheduleIndex.setBackground(ContextCompat.getDrawable(mContext, position == 0 ? R.drawable.orange_circle : R.drawable.gray_circle));
            holderSchedule.mViewLineBottom.setBackgroundColor(ContextCompat.getColor(mContext, R.color.tint_gray_line));
            holderSchedule.mViewLineBottom.setVisibility(position == getItemCount() - 1 ? View.GONE : View.VISIBLE);
            holderSchedule.mTvSheduleHead.setTextColor(ContextCompat.getColor(mContext, position == 0 ? R.color.orange : R.color.charactertintgray));
            holderSchedule.mTvSheduleContent.setTextColor(ContextCompat.getColor(mContext, position == 0 ? R.color.black : R.color.charactertintgray));

            holderSchedule.mTvSheduleHead.setText(bean.getMhead());
            holderSchedule.mTvScheduleDate.setText(bean.getMdate());
            content = bean.getMcontent();
            holderSchedule.mTvSheduleContent.setText(content);
            if (bean.getMhead().contains("分配工单")) {
                final String tel = content.substring(content.length() - 11, content.length());
                clickPicAndPhone(content, holderSchedule.mTvSheduleContent, tel);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolderSchedule extends RecyclerView.ViewHolder {

        @BindView(R.id.view_line_top)
        View mViewLineTop;
        @BindView(R.id.iv_shedule_index)
        ImageView mIvSheduleIndex;
        @BindView(R.id.tv_shedule_head)
        TextView mTvSheduleHead;
        @BindView(R.id.tv_schedule_date)
        TextView mTvScheduleDate;
        @BindView(R.id.tv_shedule_content)
        TextView mTvSheduleContent;
        @BindView(R.id.view_line_bottom)
        View mViewLineBottom;
//        @BindView(R.id.iv_location)
//        ImageView ivLocation;

        public ViewHolderSchedule(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            AutoUtils.autoSize(itemView);
        }
    }

    private void clickPicAndPhone(String str, TextView tv, String phone) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString imgSpanText = new SpannableString(str);
        builder.append(setPhoneClickableSpan(imgSpanText, phone));
        builder.append(setPicClickableSpan(setImageSpan()));
        tv.setText(builder);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private SpannableString setImageSpan() {
        String text = "     ";
        SpannableString imgSpanText = new SpannableString(text);
        imgSpanText.setSpan(new ImageSpan(mContext, R.mipmap.dingwei, DynamicDrawableSpan.ALIGN_BASELINE),
                4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return imgSpanText;
    }

    private SpannableString setPhoneClickableSpan(SpannableString textStr, final String phone) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new ClickableSpan() {
                                    @Override
                                    public void onClick(View widget) {
                                        new AppDialog(mContext).builder()
                                                .setTitle("维修工电话")
                                                .setMsg("确认拨打电话：" + phone + "吗")
                                                .setNegativeButton("取消", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                    }
                                                }).setPositiveButton("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                                intent.setData(Uri.parse("tel:" + phone));
                                                mContext.startActivity(intent);
                                            }
                                        }).show();
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setColor(mContext.getResources().getColor(R.color.characterblue));
                                        ds.setUnderlineText(false);
                                        ds.clearShadowLayer();
                                    }
                                }, subjectSpanText.length() - 11, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return subjectSpanText;
    }

    private SpannableString setPicClickableSpan(SpannableString textStr) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new ClickableSpan() {
                                    @Override
                                    public void onClick(View widget) {
                                        BaseActivity baseActivity = (BaseActivity) mContext;
                                        FragmentManager fm = baseActivity.getSupportFragmentManager();
                                        ChooseMapDialog chooseMapDialog = (ChooseMapDialog) fm.findFragmentByTag("openmap");
                                        if (chooseMapDialog == null) {
                                            chooseMapDialog = new ChooseMapDialog();
                                            chooseMapDialog.show(baseActivity.getSupportFragmentManager(), "openmap");
                                        } else {
                                            fm.beginTransaction().add(chooseMapDialog, "openmap").commitAllowingStateLoss();
                                        }
                                        chooseMapDialog.setmLat(String.valueOf(list.get(0).getLat()));
                                        chooseMapDialog.setmLng(String.valueOf(list.get(0).getLng()));
//                                        chooseMapDialog.setmLat(String.valueOf(31.885541));
//                                        chooseMapDialog.setmLng(String.valueOf(117.31724));
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setColor(mContext.getResources().getColor(R.color.black));
                                        ds.setUnderlineText(false);
                                        ds.clearShadowLayer();
                                    }
                                }, 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return subjectSpanText;
    }
}
