package com.wzm.baselib.widget;

import java.io.Serializable;

public class AreaInfoListBean implements Serializable {



    /**
     * AREA_CODE : 410302000000
     * AREA_NAME : 老城区
     */

    private String AREA_CODE;
    private String AREA_NAME;

    public String getAREA_CODE() {
        return AREA_CODE;
    }

    public void setAREA_CODE(String AREA_CODE) {
        this.AREA_CODE = AREA_CODE;
    }

    public String getAREA_NAME() {
        return AREA_NAME;
    }

    public void setAREA_NAME(String AREA_NAME) {
        this.AREA_NAME = AREA_NAME;
    }
}
