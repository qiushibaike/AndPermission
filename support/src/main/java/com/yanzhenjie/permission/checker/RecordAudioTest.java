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
import android.media.MediaRecorder;
import android.os.SystemClock;

import java.io.File;

/**
 * Created by Zhenjie Yan on 2018/1/14.
 */
class RecordAudioTest implements PermissionTest {

    private static final long SLEEP_CHECK_MILLIS = 50L;
    private Context mContext;

    RecordAudioTest(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean test() throws Throwable {
        File mTempFile = null;
        MediaRecorder mediaRecorder = new MediaRecorder();
        boolean hasPermission;
        try {
            mTempFile = File.createTempFile("permission", "test");

            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(mTempFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();

            if (!ManufacturerSupportUtil.isAndroidM()) {
                if (ManufacturerSupportUtil.isHUAWEI()
                        || ManufacturerSupportUtil.isOPPO()
                        || ManufacturerSupportUtil.isVIVO()
                        || ManufacturerSupportUtil.isXIAOMI()
                        || ManufacturerSupportUtil.isNubia()) {
                    // oppo/vivo/huawei/xiaomi/nubia部分机型即使权限禁止后，不会抛异常，需要延迟SLEEP_CHECK_MILLIS重新检测
                    String name = Thread.currentThread().getName();
                    L.logw("即使权限禁止后，也不会抛异常，需要sleep " + SLEEP_CHECK_MILLIS + "ml重新检测，手机品牌：" + ManufacturerSupportUtil.getManufacturer() + "，" + name);
                    SystemClock.sleep(SLEEP_CHECK_MILLIS);
                    if (mTempFile != null && mTempFile.exists() && mTempFile.length() > 0) {
                        hasPermission = true;
                        L.logi("录音文件大于0，认为有权限，手机品牌：" + ManufacturerSupportUtil.getManufacturer() + "，文件大小：" + mTempFile.length());
                    } else {
                        hasPermission = false;
                        L.logw("录音文件小于0，没有权限，手机品牌：" + ManufacturerSupportUtil.getManufacturer());
                    }
                } else {
                    L.logi("其他手机认为有Audio权限，手机品牌：" + ManufacturerSupportUtil.getManufacturer());
                    hasPermission = true;
                }
            } else {
                L.logi("Android6.x+手机认为有Audio权限，手机品牌：" + ManufacturerSupportUtil.getManufacturer());
                hasPermission = true;
            }
        } catch (Throwable e) {
            hasPermission = !existMicrophone(mContext);
        } finally {
            try {
                mediaRecorder.stop();
            } catch (Exception ignored) {
            }
            try {
                mediaRecorder.release();
            } catch (Exception ignored) {
            }

            if (mTempFile != null && mTempFile.exists()) {
                mTempFile.delete();
            }
        }
        return hasPermission;
    }

    private static boolean existMicrophone(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }

}