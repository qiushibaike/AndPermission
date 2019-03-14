/*
 * Copyright © Zhenjie Yan
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
package com.yanzhenjie.permission.checker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by Zhenjie Yan on 2018/1/25.
 */
class PhoneStateReadTest implements PermissionTest {

    /**
     * 默认的deviceid
     *
     * 1. 华为horor6
     */
    private static final String DEFAULT_DEVICEID = "000000000000000";

    private Context mContext;

    PhoneStateReadTest(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean test() throws Throwable {
        PackageManager packageManager = mContext.getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) return true;

        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

        // No phone radio
        if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            L.logi(" No phone radio，PhoneType为：" + tm.getPhoneType());
            return true;
        }
        String deviceId = tm.getDeviceId();
        boolean b1 = !TextUtils.isEmpty(deviceId) && !(TextUtils.equals(deviceId, DEFAULT_DEVICEID));
        if (b1) {
            L.logi("有deviceId：" + deviceId + "，设备品牌：" + ManufacturerSupportUtil.getManufacturer());
            return true;
        }

        String subscriberId = tm.getSubscriberId();

        boolean b2 = !TextUtils.isEmpty(subscriberId) && !(TextUtils.equals(subscriberId, DEFAULT_DEVICEID));
        if (b2) {
            L.logi("有subscriberId：" + subscriberId + "，设备品牌：" + ManufacturerSupportUtil.getManufacturer());
            return true;
        }

        L.logw("获取不到deviceid，没有READ_PHONE_STATE，deviceId：" + deviceId);
        return false;
    }
}