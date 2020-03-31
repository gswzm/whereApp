package com.wzm.baselib.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wzm.baselib.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

public class DialogUtilPic {
    public static void showPop(final Activity activity, final int maxNum) {
        View bottomView = View.inflate(activity, R.layout.dc_bottom_layout, null);
        TextView mAlbum = bottomView.findViewById(R.id.tv_album);
        TextView mCamera = bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = bottomView.findViewById(R.id.tv_cancel);

        final PopupWindow pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        activity.getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.dc_main_menu_photo_anim);
        pop.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        mAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册
                PictureSelector.create(activity)
                        .openGallery(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .compress(true)
                        .maxSelectNum(maxNum)
                        .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                        .minSelectNum(1)
                        .imageSpanCount(4)
                        .selectionMode(PictureConfig.MULTIPLE)
                        .forResult(PictureConfig.CHOOSE_REQUEST);

                closePopupWindow(pop);
            }
        });
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
                PictureSelector.create(activity)
                        .openCamera(PictureMimeType.ofImage())
                        .compress(true)
                        .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                        .forResult(PictureConfig.CHOOSE_REQUEST);

                closePopupWindow(pop);
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopupWindow(pop);
            }
        });
    }

    private static void closePopupWindow(PopupWindow pop) {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }
    }
}
