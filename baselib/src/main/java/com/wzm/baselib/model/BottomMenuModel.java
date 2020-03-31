package com.wzm.baselib.model;

/**
 * @Description:主界面切换fragment的实体类
 * @Author: yaochangliang
 * @CreateDate: 2019-12-03 09:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2019-12-03 09:23
 * @UpdateRemark: 更新说明
 */

public class BottomMenuModel {
    private int selectedImage;
    private int unSelectedImage;
    private String text;
    private int selectedTextColor;
    private int unSelectedTextColor;

    //纯色的图片的资源ID，构造方法有两种 二选一
    private int chunSeImage;

    public int getChunSeImage() {
        return chunSeImage;
    }

    public void setChunSeImage(int chunSeImage) {
        this.chunSeImage = chunSeImage;
    }

    /**
     *
     * @param selectedImage 选择时候的图片，
     * @param unSelectedImage 未选择的图片
     * @param text 显示的菜单名称
     */
    //构造方法有两种 二选一
    public BottomMenuModel(int selectedImage, int unSelectedImage, String text) {
        this.selectedImage = selectedImage;
        this.unSelectedImage = unSelectedImage;
        this.text = text;
    }

    //构造方法有两种 二选一
    public BottomMenuModel(int chunSeImage,String text) {
        this.text = text;
        this.chunSeImage = chunSeImage;
    }

    public int getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(int selectedImage) {
        this.selectedImage = selectedImage;
    }

    public int getUnSelectedImage() {
        return unSelectedImage;
    }

    public void setUnSelectedImage(int unSelectedImage) {
        this.unSelectedImage = unSelectedImage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public int getUnSelectedTextColor() {
        return unSelectedTextColor;
    }

    public void setUnSelectedTextColor(int unSelectedTextColor) {
        this.unSelectedTextColor = unSelectedTextColor;
    }
}
