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
import android.hardware.Camera;

import java.lang.reflect.Field;

/**
 * Created by YanZhenjie on 2018/1/15.
 */
class CameraTest implements PermissionTest {

    private Context mContext;

    CameraTest(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean test() throws Throwable {
        Camera camera = null;
        boolean hasPermission = false;
        try {
            camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            camera.setParameters(parameters);
            camera.setPreviewCallback(PREVIEW_CALLBACK);
            camera.startPreview();

            if (!ManufacturerSupportUtil.isAndroidM()) {
                if (ManufacturerSupportUtil.isVIVO()) {
                    try {
                        Field fieldPassword = camera.getClass().getDeclaredField("mHasPermission");
                        fieldPassword.setAccessible(true);
                        hasPermission = (boolean) fieldPassword.get(camera);
                        L.logw("Vivo手机，权限：" + hasPermission);
                    } catch (Exception e) {
                        e.printStackTrace();
                        L.logw("Vivo手机，认为没有权限");
                        hasPermission = false;
                    }
                }/* else if (ManufacturerSupportUtil.isMEIZU()) {
                    try {
                        // setParameters 是针对魅族MX5 做的。MX5 通过Camera.open() 拿到的Camera对象不为null
                        Camera.Parameters mParameters = camera.getParameters();
                        camera.setParameters(mParameters);
                        hasPermission = true;
                        L.loge("Meizu手机，认为有权限");
                    } catch (Exception e) {
                        L.loge("Meizu手机，认为没有权限");
                        hasPermission = false;
                    }
                }*/ else {
                    L.logi("没有抛出异常认为有Camera权限，手机品牌：" + ManufacturerSupportUtil.getManufacturer());
                    hasPermission = true;
                }
            } else {
                L.logi("Android6.x+没有抛出异常认为有Camera权限，手机品牌：" + ManufacturerSupportUtil.getManufacturer());
                hasPermission = true;
            }
        } catch (Throwable e) {
            L.logw("抛出异常认为没有Camera权限，手机品牌：" + ManufacturerSupportUtil.getManufacturer());
            boolean existCamera = existCamera(mContext);
            hasPermission = !existCamera;
            return hasPermission;
        } finally {
            if (camera != null) {
                camera.stopPreview();
                camera.setPreviewCallback(null);
                camera.release();
            }
        }
        return hasPermission;
    }

    private static boolean existCamera(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private static final Camera.PreviewCallback PREVIEW_CALLBACK = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
        }
    };
}