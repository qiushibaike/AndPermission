/*
 * Copyright 2018 Zhenjie Yan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.permission.runtime.setting;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.yanzhenjie.permission.source.Source;

/**
 * Created by Zhenjie Yan on 2018/4/30.
 */
public class SettingPage {

    private static final String MARK = Build.MANUFACTURER.toLowerCase();

    private static final String MANUFACTURER_HUAWEI = "huawei";//华为
    private static final String MANUFACTURER_XIAOMI = "xiaomi";//小米
    private static final String MANUFACTURER_OPPO = "oppo";
    private static final String MANUFACTURER_VIVO = "vivo";
    private static final String MANUFACTURER_MEIZU = "meizu";//魅族
    private static final String MANUFACTURER_SONY = "sony";//索尼
    private static final String MANUFACTURER_LG = "lg";
    private static final String MANUFACTURER_SAMSUNG = "samsung";//三星
    private static final String MANUFACTURER_LETV = "letv";//乐视
    private static final String MANUFACTURER_ZTE = "zte";//中兴
    private static final String MANUFACTURER_YULONG = "YuLong";//酷派
    private static final String MANUFACTURER_LENOVO = "lenovo";//联想

    private Source mSource;

    public SettingPage(Source source) {
        this.mSource = source;
    }

    /**
     * Start.
     *
     * @param requestCode this code will be returned in onActivityResult() when the activity exits.
     */
    public void start(int requestCode) {
        Intent intent;
        if (MARK.contains(MANUFACTURER_HUAWEI)) {
            intent = huaweiApi(mSource.getContext());
        } else if (MARK.contains(MANUFACTURER_XIAOMI)) {
            intent = xiaomiApi(mSource.getContext());
        } else if (MARK.contains(MANUFACTURER_OPPO)) {
            intent = oppoApi(mSource.getContext());
        } else if (MARK.contains(MANUFACTURER_VIVO)) {
            intent = vivoApi(mSource.getContext());
        } else if (MARK.contains(MANUFACTURER_MEIZU)) {
            intent = meizuApi(mSource.getContext());
        } else if (MARK.contains(MANUFACTURER_SONY)) {
            intent = sonyApi(mSource.getContext());
        } else if (MARK.contains(MANUFACTURER_LG)) {
            intent = LG(mSource.getContext());
        } else if (MARK.contains(MANUFACTURER_LETV)) {
            intent = Letv(mSource.getContext());
        } else {
            intent = defaultApi(mSource.getContext());
        }
        try {
            mSource.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            intent = defaultApi(mSource.getContext());
            mSource.startActivityForResult(intent, requestCode);
        }
    }

    public static Intent LG(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", context.getPackageName());
        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
        intent.setComponent(comp);
        if (hasActivity(context, intent)) {
            return intent;
        }
        return defaultApi(context);
    }

    public static Intent Letv(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", context.getPackageName());
        ComponentName comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
        intent.setComponent(comp);
        if (hasActivity(context, intent)) {
            return intent;
        }
        return defaultApi(context);
    }

    /**
     * 只能打开到自带安全软件
     *
     */
    public static Intent _360(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.putExtra("packageName", context.getPackageName());
        ComponentName comp = new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        intent.setComponent(comp);
        if (hasActivity(context, intent)) {
            return intent;
        }
        return defaultApi(context);
    }

    private static Intent sonyApi(Context context) {
        Intent intent = new Intent();
        intent.putExtra("packageName", context.getPackageName());
        ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
        intent.setComponent(comp);
        if (hasActivity(context, intent)) {
            return intent;
        }
        return defaultApi(context);
    }

    private static Intent defaultApi(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return intent;
    }

    private static Intent huaweiApi(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        if (hasActivity(context, intent)) return intent;

        return defaultApi(context);
    }

    private static Intent xiaomiApi(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.putExtra("extra_pkgname", context.getPackageName());
        if (hasActivity(context, intent)) return intent;

        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        if (hasActivity(context, intent)) return intent;

        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        if (hasActivity(context, intent)) return intent;

        return defaultApi(context);
    }

    private static Intent vivoApi(Context context) {
        Intent intent = new Intent();
        intent.putExtra("packagename", context.getPackageName());
        intent.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
        if (hasActivity(context, intent)) return intent;

        intent.setClassName("com.iqoo.secure", "com.iqoo.secure.safeguard.SoftPermissionDetailActivity");
        if (hasActivity(context, intent)) return intent;

        return defaultApi(context);
    }

    private static Intent oppoApi(Context context) {
        Intent intent = new Intent();
        intent.putExtra("packageName", context.getPackageName());
        intent.setClassName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
        if (hasActivity(context, intent)) return intent;

        intent.setClassName("com.oppo.safe", "com.oppo.safe.permission.PermissionAppListActivity");
        if (hasActivity(context, intent)) return intent;

        return defaultApi(context);
    }

    private static Intent meizuApi(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.putExtra("packageName", context.getPackageName());
        intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity");
        if (hasActivity(context, intent)) return intent;

        return defaultApi(context);
    }

    private static boolean hasActivity(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }
}