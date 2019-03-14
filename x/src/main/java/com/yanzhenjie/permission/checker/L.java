package com.yanzhenjie.permission.checker;

import android.text.TextUtils;
import android.util.Log;

import com.yanzhenjie.permission.BuildConfig;

import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY)
final class L {

    public static final String TAG = "qsbk.permission";
    private static final boolean logable = BuildConfig.DEBUG;

    static void logi(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (logable) {
            Log.i(TAG, msg);
        }
    }

    static void logi(Object anchor, String msg) {
        if (logable) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(anchor.getClass().getSimpleName());
            sb.append("]");
            sb.append(msg);
            Log.i(TAG, sb.toString());
        }
    }

    static void logw(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (logable) {
            Log.w(TAG, msg);
        }
    }

    static void logw(Object anchor, String msg) {
        if (logable) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(anchor.getClass().getSimpleName());
            sb.append("]");
            sb.append(msg);
            Log.w(TAG, sb.toString());
        }
    }

    static void loge(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (logable) {
            Log.e(TAG, msg);
        }
    }

    static void loge(Object anchor, String msg) {
        if (logable) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(anchor.getClass().getSimpleName());
            sb.append("]");
            sb.append(msg);
            Log.e(TAG, sb.toString());
        }
    }
}
