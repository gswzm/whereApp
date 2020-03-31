package com.wzm.baselib.utils.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * 描述：权限判断类
 * 类名： PermissionsLogUtils
 * 创作者： wangzm
 * 时间：2019/7/25
 */

public class PermissionsLogUtils {
    // 查看权限是否已申请

    /**
     *  查看权限是否已申请
     *  true 有权限  false 至少一个没有权限
     * @param context
     * @param permissions 权限集合或单个权限
     * @return
     */
    public static boolean checkPermissions(Activity context, String... permissions) {
        boolean hasPermission=true;
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if(isPermission(context,permission)){
                    hasPermission= false;
                    break;
                }
            }
        }
        return hasPermission;
    }

    /**
     * 是否有权限 true 没有权限 false 有权限
     * @param context
     * @param permission
     * @return
     */
    private static boolean isPermission(Activity context, String permission){
        PackageManager pm = context.getPackageManager();
        return PackageManager.PERMISSION_DENIED ==
                pm.checkPermission(permission, context.getPackageName());
    }
   }