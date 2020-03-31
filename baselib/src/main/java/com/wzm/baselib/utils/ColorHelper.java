package com.wzm.baselib.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.wzm.baselib.R;

/**
 * @Description:纯色图片改变颜色的工具类
 * @Author: yaochangliang
 * @CreateDate: 2019-12-06 14:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2019-12-06 14:17
 * @UpdateRemark: 更新说明
 */

public class ColorHelper {
    //修改图标的颜色，这里修改成APP的主题色，要求图标必须是纯色
    public static void drawColor(Context context, View view, int resId) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        int color = ContextCompat.getColor(context, R.color.appThemeColor);
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        wrappedDrawable.mutate();
        DrawableCompat.setTint(wrappedDrawable, color);
        view.setBackground(wrappedDrawable);
    }

    //修改图标的颜色，这里修改成指定颜色，要求图标必须是纯色
    public static void drawColor(Context context, View view, int resId, int color) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        wrappedDrawable.mutate();
        DrawableCompat.setTint(wrappedDrawable, color);
        view.setBackground(wrappedDrawable);
    }

}
