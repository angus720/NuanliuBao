package nuanliu.com.modao.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import nuanliu.com.modao.R;
import nuanliu.com.modao.utils.StringUtil;

/**
 * 查询新卡号Dialog
 */

public class CardInfoDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView tvUserName;
//    private TextView tvOldNumber;
    private TextView tvNewNumber;
    private TextView tvCommunityName;
    private TextView tvBuildingNumber;
    private TextView tvUnitNumber;
    private TextView tvHouseNumber;
    private Button btn_neg;
    private Button btn_pos;
    private View img_line;
    private Display display;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private LinearLayout setView;
    private String residentId;

    public CardInfoDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public CardInfoDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.card_info_dialog, null);
        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
//        tvOldNumber = (TextView) view.findViewById(R.id.tv_user_old_number);
        tvNewNumber = (TextView) view.findViewById(R.id.tv_user_new_number);
        tvCommunityName = (TextView) view.findViewById(R.id.tv_community_name);
        tvBuildingNumber = (TextView) view.findViewById(R.id.tv_building_number);
        tvUnitNumber = (TextView) view.findViewById(R.id.tv_unit_number);
        tvHouseNumber = (TextView) view.findViewById(R.id.tv_house_number);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (View) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);
        setView = (LinearLayout) view.findViewById(R.id.setview);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.MyDialogStyle);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.CustomDailogStyle);
        //添加动画
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.80), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public void setUserName(String userName) {
        if (!StringUtil.isEmpty(userName)) {
            tvUserName.setText(userName);
        } else {
            tvUserName.setText("");
        }
    }

    public void setNewNumber(String newNumber) {
        if (!StringUtil.isEmpty(newNumber)) {
            tvNewNumber.setText(newNumber);
        } else {
            tvNewNumber.setText("");
        }
    }

    public void setCommunityName(String communityName) {
        if (!StringUtil.isEmpty(communityName)) {
            tvCommunityName.setText(communityName);
        } else {
            tvCommunityName.setText("");
        }
    }

    public void setBuildingNumber(String buildingNumber) {
        if (!StringUtil.isEmpty(buildingNumber)) {
            tvBuildingNumber.setText(buildingNumber);
        } else {
            tvBuildingNumber.setText("");
        }
    }

    public void setUnitNumber(String unitNumber) {
        if (!StringUtil.isEmpty(unitNumber)) {
            tvUnitNumber.setText(unitNumber);
        } else {
            tvUnitNumber.setText("");
        }
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

//    public void setOldNumber(String oldNumber) {
//        if (!StringUtil.isEmpty(oldNumber)) {
//            tvOldNumber.setText(oldNumber);
//        } else {
//            tvOldNumber.setText("");
//        }
//    }

    public void setHouseNumber(String houseNumber) {
        if (!StringUtil.isEmpty(houseNumber)) {
            tvHouseNumber.setText(houseNumber);
        } else {
            tvHouseNumber.setText("");
        }
    }

    public CardInfoDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public CardInfoDialog setPositiveButtonTextColor(int color) {
        btn_pos.setTextColor(color);
        return this;
    }

    public CardInfoDialog setPositiveButton(String text,
                                            final View.OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }


    public CardInfoDialog setPositiveButton(String text,
                                            final View.OnClickListener listener, final boolean show) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onClick(v);
                }
                if (!show) {
                    dialog.dismiss();
                }
            }
        });
        return this;
    }

    public CardInfoDialog setNegativeButton(String text,
                                            final View.OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    public CardInfoDialog setView(View v) {
        setView.addView(v);
        return this;
    }

    public Button getBtnNeg() {
        return btn_neg;
    }

    private void setLayout() {
        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_neg.setVisibility(View.VISIBLE);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public CardInfoDialog setCanceledOnTouchOutside(boolean cancled) {
        dialog.setCanceledOnTouchOutside(cancled);
        return this;
    }
}
