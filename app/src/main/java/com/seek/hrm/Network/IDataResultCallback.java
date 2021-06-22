package com.seek.hrm.Network;

public interface IDataResultCallback<T> {
    void onSuccess(boolean success,T data);
}
