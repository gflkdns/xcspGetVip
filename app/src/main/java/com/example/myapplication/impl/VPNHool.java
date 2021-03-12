package com.example.myapplication.impl;

import android.content.Context;

import com.example.myapplication.utils.L;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @Copyright © 2021 analsys Inc. All rights reserved.
 * @Description: VPN破解使用的
 * @Version: 1.0
 * @Create: 2021/03/71 10:53:34
 * @author: sanbo
 */
public class VPNHool {
    public static void hook(XC_LoadPackage.LoadPackageParam lpparam) {
        L.i("inside XposedInside ...pkg: " + lpparam.packageName);
        if ("com.leiting.lt100".equals(lpparam.packageName)) {
            hookVpn(lpparam, "com.leiting.lt100.ui.CommUrlApi", "雷霆213");
        } else if ("com.blackhole.hd100".equals(lpparam.packageName)) {
            hookVpn(lpparam, "com.blackhole.hd100.ui.CommUrlApi", "黑洞402");
        } else if ("com.honeybee.newapp".equals(lpparam.packageName)) {
            //老包：com.honeybee.app
            hookVpn(lpparam, "com.honeybee.newapp.ui.CommUrlApi", "蜜蜂303");
        } else if ("com.network.xf100".equals(lpparam.packageName)) {
            hookVpn(lpparam, "com.network.xf100.ui.CommUrlApi", "旋风707");
        }
    }


    private static void hookVpn(XC_LoadPackage.LoadPackageParam lpparam, String hookClassName, String channelName) {
        XposedHelpers.findAndHookMethod(hookClassName, lpparam.classLoader, "MD5", String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                        L.i("[" + channelName + "] MD5 result---->" + param.getResult());
                        L.i("[" + channelName + "] MD5 args1--->" + param.args[0]);
//                        printStackTrace(channelName + ".MD5");
                    }
                });


        XposedHelpers.findAndHookMethod(hookClassName, lpparam.classLoader, "getCommParams", Context.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                        L.i("[" + channelName + "] getCommParams result---->" + param.getResult());
//                        printStackTrace(channelName + ".getCommParams");
                    }
                });
        XposedHelpers.findAndHookMethod(hookClassName, lpparam.classLoader, "getUniqueId", Context.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {


                        L.i("[" + channelName + "] getUniqueId result---->" + param.getResult());
//                        printStackTrace(channelName + ".getUniqueId");
                    }
                });
    }

    private static void printStackTrace(String methodName) {
        L.i("=============printStackTrace[" + methodName + "]=========");
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        for (int i = 0; i < stackElements.length; i++) {
            StackTraceElement element = stackElements[i];
            L.i("at " + element.getClassName() + "." + element.getMethodName() + "(" + element
                    .getFileName() + ":" + element.getLineNumber() + ")");
        }
    }
}
