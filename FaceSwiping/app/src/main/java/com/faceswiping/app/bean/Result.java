package com.faceswiping.app.bean;

import java.io.Serializable;

/**
 * Created by John on 15/12/12.
 */
public class Result<T> implements Serializable {

    private int errorcode;

    private String errormsg;

    private T data;


    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
