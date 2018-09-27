package nuanliu.com.modao.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import nuanliu.com.modao.R;


/**
 * Created by Administrator on 2016/10/11.
 */

public class LoadingDialog extends Dialog {
    private TextView refreshMsg;

    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog);
        initView();
    }

    public void show(String string) {
        this.show();
        if (refreshMsg != null) {
            refreshMsg.setText(string);
        }
    }


    private void initView() {
        View view = View.inflate(getContext(), R.layout.dialog_inside_pg, null);
        refreshMsg = (TextView) view.findViewById(R.id.refreshing_message);
        setCanceledOnTouchOutside(false);
        setContentView(view);
    }
}
