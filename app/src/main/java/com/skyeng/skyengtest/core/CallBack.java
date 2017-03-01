package com.skyeng.skyengtest.core;

public class CallBack<T> {

    public void onSuccess() {
    }

    public void onSuccess(T result) {
    }

    public void onSuccess(String result) {
    }


    public void onFail(String message) {

    }

    public void onFail(T result) {

    }
}
