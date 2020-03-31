package com.wzm.baselib.utils.permission;

import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.wzm.baselib.utils.StringHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Map;

/**
 * 描述：动态权限工具类
 * 类名： PermissionShowUtil
 * 创作者： wangzm
 * 时间：2019/12/6
 */

public class PermissionShowUtil {

    @SuppressLint("CheckResult")
    public static void requestPermissions(String model, FragmentActivity activity, OnPermissionListener onPermissionListener) {

        Map<String, Object> tempMap = PermissionInfo.getMap(model);
        new RxPermissions(activity).requestEachCombined((String[]) tempMap.get("permission"))
                .subscribe(permission -> {
                    if (permission.granted) {
                        onPermissionListener.onGranted();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        Toast.makeText(activity, StringHelper.convertToString(tempMap.get("refusedMsg")),Toast.LENGTH_SHORT).show();
                        onPermissionListener.onShouldShow();
                    } else {
                        Toast.makeText(activity,StringHelper.convertToString(tempMap.get("shouldShowMsg")),Toast.LENGTH_SHORT).show();
                        PermissionSettingUtils.openSettingPage(activity);
                    }
                });
    }

    public interface OnPermissionListener{
        void onGranted();
        void onShouldShow();
    }
}
