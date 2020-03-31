package com.wzm.baselib.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.List;

public class PhoneInfoUtil {
    /**
     * 获取android系统版本号
     */
    public static String getOSVer() {
        return Build.VERSION.RELEASE;//android 型号
    }

    /**
     * 获取手机型号
     */
    public static String getPhoneModel() {
        return Build.MODEL; //手机型号
    }

    public static String getPhoneNumber(Activity context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        if (tm.getLine1Number() != null) {
            return tm.getLine1Number().toString();
        } else {
            return "";
        }
    }

    /**
     * 获取imsi号
     */
    public static String getIMSI(Activity context) {
        String imsiStr = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return imsiStr;
        }
        if (tm.getSubscriberId() != null) {
            return StringHelper.convertToString(tm.getSubscriberId());
        } else {
            return "";
        }
    }

    public static String getIMEI(Activity context) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String iemiStr="";
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            iemiStr= Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            if (tm.getImei() != null) {
                iemiStr= StringHelper.convertToString(tm.getImei());
            }
        }else{
            if (tm.getDeviceId() != null) {
                iemiStr= StringHelper.convertToString(tm.getDeviceId());
            }
        }
        return iemiStr;
    }


    /**
     * 拨打电话
     *
     * @param context         上下文
     * @param callPhoneNumber 要拨打的电话号码
     */
    public static void callPhone(Context context, String callPhoneNumber) {

        try {
            if (StringHelper.isNotBlank(callPhoneNumber)) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + callPhoneNumber));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "电话号码为空", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "抱歉，拨打电话失败，请确认您的设备是否具备打电话的功能", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 检测是否安装支付宝
     * @param context
     * @return
     */
    public static boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    /**
     * 检测是否安装微信
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

}
