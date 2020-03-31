package com.wzm.baselib.net;

import java.io.Serializable;

/**
 * Created by wangzm on 2019/4/11
 */
public class TokenInfo implements Serializable {


    /**
     * access_token : 384bf4a36354fca79acc24ac066b7231
     * refresh_token : 5f49d62e41e75638d03e39f9caf12113
     * token_type : Bearer
     * expires_in : 43199
     */

    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
