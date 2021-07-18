package com.seek.hrm.Validation;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.seek.hrm.R;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceComparerTest {

    ResourceComparer resourceComparer;
    @Before
    public void init(){
        resourceComparer = new ResourceComparer();
    }
    @Test
    public void isEqualString() {
        Context context = ApplicationProvider.getApplicationContext();
        boolean result = resourceComparer.isEqualString(context,
                R.string.app_name, "HeartBeat");
        assertTrue(result);
    }
}