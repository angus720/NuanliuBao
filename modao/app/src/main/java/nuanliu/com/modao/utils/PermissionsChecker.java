package nuanliu.com.modao.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * @创建者 SoleJoke
 * @创建时间 on 2017-07-04
 * @描述      权限检测的工具类
 * @更新者 　$Author
 * @更新时间 　2017-07-04
 * @更新描述　　${ToDo}
 */

public class PermissionsChecker {


    // 判断是否缺少权限
    public static boolean lacksPermission(Context mContext,String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}
