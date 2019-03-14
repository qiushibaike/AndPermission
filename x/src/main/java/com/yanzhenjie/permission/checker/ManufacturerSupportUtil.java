package com.yanzhenjie.permission.checker;

import android.os.Build;

import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY)
final class ManufacturerSupportUtil {

    /**
     * Build.MANUFACTURER
     */
    static final String MANUFACTURER_HUAWEI = "huawei";
    static final String MANUFACTURER_XIAOMI = "xiaomi";
    static final String MANUFACTURER_OPPO = "oppo";
    static final String MANUFACTURER_VIVO = "vivo";
    static final String MANUFACTURER_MEIZU = "meizu";
    static final String MANUFACTURER_NUBIA = "nubia";
    static final String manufacturer = Build.MANUFACTURER;

    public static String getManufacturer() {
        return manufacturer.toLowerCase();
    }

    public static boolean isHUAWEI() {
        return getManufacturer().contains(MANUFACTURER_HUAWEI);
    }

    public static boolean isXIAOMI() {
        return getManufacturer().contains(MANUFACTURER_XIAOMI);
    }

    public static boolean isVIVO() {
        return getManufacturer().contains(MANUFACTURER_VIVO);
    }

    public static boolean isOPPO() {
        return getManufacturer().contains(MANUFACTURER_OPPO);
    }

    public static boolean isMEIZU() {
        return getManufacturer().contains(MANUFACTURER_MEIZU);
    }

    public static boolean isNubia() {
        return getManufacturer().contains(MANUFACTURER_NUBIA);
    }

    public static boolean isAndroidM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
