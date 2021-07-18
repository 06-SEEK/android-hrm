package com.seek.hrm.Validation;

import android.content.Context;

public class ResourceComparer {
    public boolean isEqualString(Context context, int resId,String string ){
        return context.getString(resId).equals(string);
    }
}
