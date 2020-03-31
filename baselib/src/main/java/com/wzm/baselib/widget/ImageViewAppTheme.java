package com.wzm.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.wzm.baselib.R;

/**
 * @Description:纯色图片显示主题颜色的控件
 * @Author: yaochangliang
 * @CreateDate: 2019-12-09 10:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2019-12-09 10:50
 * @UpdateRemark: 更新说明
 */

public class ImageViewAppTheme extends AppCompatImageView {
    Context context;
    private int resourceId;


    public ImageViewAppTheme(Context context) {
        this(context, null);
    }

    public ImageViewAppTheme(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewAppTheme(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        //获取自定义属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageViewAppTheme);
        if (typedArray != null) {
            resourceId = typedArray.getResourceId(R.styleable.ImageViewAppTheme_resourceId,0);
            typedArray.recycle();
        }
        initView(context);
    }

    /**
     * 将纯色图标 改为当前APP的主题颜色
     * @param context
     */
    private void initView(Context context){
        Drawable drawable= ContextCompat.getDrawable(context, resourceId);
        int color= ContextCompat.getColor(context, R.color.appThemeColor);
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        wrappedDrawable.mutate();
        DrawableCompat.setTint(wrappedDrawable, color);
        setBackground(wrappedDrawable);
    }

    public void setResourceId(int resourceId){
        this.resourceId=resourceId;
        initView(context);
    }
}
