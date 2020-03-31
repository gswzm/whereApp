package com.wzm.baselib.utils.permission;

import android.Manifest;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：动态权限及提示语
 * 类名： PermissionInfo
 * 创作者： wangzm
 * 修改者：zhangxg
 * 时间：2019/12/6
 */
public class PermissionInfo {

    /**
     * 动态权限编码对应的type
     */
    public static final String PHOTO = "photo";
    public static final String STORAGE = "storage";
    public static final String LOCATION = "location";
    public static final String PHONE_STATE = "phone_state";
    public static final String CALL_PHONE = "call_phone";
    public static final String PERMISSIONKEY = "permission";
    public static final String REFUSEDMSG = "refusedMsg";
    public static final String SHOULDSHOWMSG = "shouldShowMsg";

    public static final String[] SPLASH_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static final String[] PERMISSIONS_GROUP_PHOTO = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    public static final String[] PERMISSION_GROUP_STORAGE = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final String[] PERMISSIONS_PHOTO = new String[]{Manifest.permission.CAMERA};

    public static final String[] PERMISSIONS_GROUP_AUDIO = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};

    public static final String[] PERMISSIONS_AUDIO = new String[]{Manifest.permission.RECORD_AUDIO};

    public static final String[] PERMISSIONS_LOCATION = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    public static final String[] PERMISSIONS_PHONE_STATE = new String[]{Manifest.permission.READ_PHONE_STATE};
    public static final String[] PERMISSION_CALL_STATE = new String[]{Manifest.permission.CALL_PHONE};


    /**
     * 读写权限
     */
    public static final String PERMISSION_EXT = "\"智慧扶贫\"应用更新功能需要存储权限，如果您拒绝存储权限，将不能更新应用。";
    public static final String PERMISSION_EXT_REFUSE = "拒绝授予存储权限会导致\"智慧扶贫\"无法升级";
    public static final String PERMISSION_EXT_FORBID = "由于您拒绝了存储权限，导致\"智慧扶贫\"不能升级，请前往设置打开存储权限";

    /**
     * 录音权限
     */
    public static final String PERMISSION_VOICE = "\"智慧扶贫\"录音功能需要麦克风权限和存储权限，如果您拒绝麦克风权限或存储权限，将不能使用录音功能。";
    public static final String PERMISSION_VOICE_REFUSE = "拒绝授予麦克风权限或存储权限会导致不能使用录音功能";
    public static final String PERMISSION_VOICE_FORBID = "由于您拒绝了麦克风权限或存储权限，导致\"智慧扶贫\"录音功能不能使用，请前往设置打开麦克风权限和存储权限";
    /**
     * 拍照
     */
    public static final String PERMISSION_PHOTO = "\"智慧扶贫\"图片选择功能需要拍照权限和存储权限，如果您拒绝拍照权限或存储权限，将不能使用图片选择功能。";
    public static final String PERMISSION_PHOTO_REFUSE = "拒绝授予拍照权限或存储权限会导致不能使用图片选择功能";
    public static final String PERMISSION_PHOTO_FORBID = "由于您拒绝了拍照权限或存储权限，导致\"智慧扶贫\"图片选择功能不能使用，请前往设置打开拍照权限和存储权限";

    /**
     * 打电话
     */
    public static final String PERMISSION_PHONE = "\"智慧扶贫\"拨打电话功能需要拨打电话权限，如果您拒绝拨打电话权限，将不能使用拨打电话功能。";
    public static final String PERMISSION_PHONE_REFUSE = "拒绝授予拨打电话权限会导致不能使用打电话功能";
    public static final String PERMISSION_PHONE_FORBID = "由于您拒绝了拨打电话权限，导致\"智慧扶贫\"打电话功能不能使用，请前往设置打开拨打电话权限";
    /**
     * 定位
     */
    public static final String PERMISSION_LOCATION = "\"智慧扶贫\"需要授予定位权限才能使用定位功能";
    public static final String PERMISSION_LOCATION_REFUSE = " 拒绝授予定位权限会导致\"智慧扶贫\"定位功能异常";
    public static final String PERMISSION_LOCATION_FORBID = "由于您拒绝了定位权限，导致\"智慧扶贫\"不能使用定位功能，请前往设置打开定位权限";


    /**
     * 设备信息
     */
    public static final String PERMISSION_PHONE_STATE_REFUSE = " 拒绝授予获取设备信息权限会导致\"智慧扶贫\"部分功能异常";
    public static final String PERMISSION_PHONE_STATE_FORBID = "由于您拒绝了获取设备信息权限，导致\"智慧扶贫\"不能使用部分功能，请前往设置打开获取设备信息权限";
    /**
     *
     */
    public static final String PERMISSION_SCAN = "\"智慧扶贫\"需要授予相机权限才能使用扫一扫功能。";
    public static final String PERMISSION_GPS = "\"智慧扶贫\"需要您打开GPS开关以便定位，如果您拒绝打开GPS开关，将会定位失败。";


    /**
     * 根据类型，加载不同的权限组
     *
     * @param type
     * @return
     */
    public static Map<String, Object> getMap(String type) {
        if (type.equals(PHOTO)) {//拍照
            Map<String, Object> map = new HashMap<>();
            map.put(PERMISSIONKEY, PERMISSIONS_GROUP_PHOTO);
            map.put(REFUSEDMSG, PERMISSION_PHOTO_REFUSE);
            map.put(SHOULDSHOWMSG, PERMISSION_PHOTO_FORBID);
            return map;
        } else if (type.equals(STORAGE)) {//存储
            Map<String, Object> map = new HashMap<>();
            map.put(PERMISSIONKEY, PERMISSION_GROUP_STORAGE);
            map.put(REFUSEDMSG, PERMISSION_EXT_REFUSE);
            map.put(SHOULDSHOWMSG, PERMISSION_EXT_FORBID);
            return map;
        } else if (type.equals(LOCATION)) {//定位
            Map<String, Object> map = new HashMap<>();
            map.put(PERMISSIONKEY, PERMISSIONS_LOCATION);
            map.put(REFUSEDMSG, PERMISSION_LOCATION_REFUSE);
            map.put(SHOULDSHOWMSG, PERMISSION_LOCATION_FORBID);
            return map;
        } else if (type.equals(PHONE_STATE)) {//设备信息
            Map<String, Object> map = new HashMap<>();
            map.put(PERMISSIONKEY, PERMISSIONS_PHONE_STATE);
            map.put(REFUSEDMSG, PERMISSION_PHONE_STATE_REFUSE);
            map.put(SHOULDSHOWMSG, PERMISSION_PHONE_STATE_FORBID);
            return map;
        }else if (type.equals(CALL_PHONE)) {//打电话
            Map<String, Object> map = new HashMap<>();
            map.put(PERMISSIONKEY, PERMISSION_CALL_STATE);
            map.put(REFUSEDMSG, PERMISSION_PHONE_REFUSE);
            map.put(SHOULDSHOWMSG, PERMISSION_PHONE_FORBID);
            return map;
        }
        return new HashMap<>();
    }

}
