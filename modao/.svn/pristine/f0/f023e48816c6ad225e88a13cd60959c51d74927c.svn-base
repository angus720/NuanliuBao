package nuanliu.com.modao.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import nuanliu.com.modao.R;


/**
 * Created by Sole on 2016/12/25.
 *
 * @更新者 ${Author} on ${DATA}
 */

public class PopupwindowUtils {

    public static PopupWindow initPopupWindow(Context context,@LayoutRes int layoutId) {
//
        View view = View.inflate(context, layoutId, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow popupWindow = new PopupWindow(view, width, height);

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.my_menu_animstylepopup);
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
//                    popupWindow.dismiss();
//                    return true;
//                }
//                return false;
//            }
//        });
        return popupWindow;

    }

    public static PopupWindow initPopupWindow(Context context,@LayoutRes int layoutId,int width,int height) {
        //
        View view = View.inflate(context, layoutId, null);
//        int width = LinearLayout.LayoutParams.MATCH_PARENT;
//        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow popupWindow = new PopupWindow(view, width, height);

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.my_menu_animstylepopup);
        return popupWindow;

    }
    public static void showAsDropDown(View view,PopupWindow popupWindow) {
        if (Build.VERSION.SDK_INT < 24) {

            popupWindow.showAsDropDown(view);

        } else {

            int[] location = new int[2];

            view.getLocationOnScreen(location);

            int x = location[0];

            int y = location[1];

            popupWindow.showAtLocation(view, Gravity.CLIP_VERTICAL, 0, y + view.getHeight());

        }
    }


}
