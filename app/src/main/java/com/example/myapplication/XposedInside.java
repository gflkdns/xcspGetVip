package com.example.myapplication;

import android.content.Context;

import com.example.myapplication.utils.L;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedInside implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        L.i("inside XposedInside ...pkg: " + lpparam.packageName);
        if ("com.leiting.lt100".equals(lpparam.packageName)) {
            hookVpn(lpparam, "com.leiting.lt100.ui.CommUrlApi", "雷霆213");
        } else if ("com.blackhole.hd100".equals(lpparam.packageName)) {
            hookVpn(lpparam, "com.blackhole.hd100.ui.CommUrlApi", "黑洞402");
        } else if ("com.honeybee.newapp".equals(lpparam.packageName)) {
            //老包：com.honeybee.app
            hookVpn(lpparam, "com.honeybee.newapp.ui.CommUrlApi", "蜜蜂303");

//            XposedHelpers.findAndHookMethod("com.honeybee.newapp.ui.CommUrlApi", lpparam.classLoader, "MD5", String.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//
//
//                            L.i("【蜜蜂303】MD5 result---->" + param.getResult());
//                            L.e("【蜜蜂303】MD5 args1---->" + param.args[0]);
//                        }
//                    });
//
//
//            XposedHelpers.findAndHookMethod("com.honeybee.newapp.ui.CommUrlApi", lpparam.classLoader, "getCommParams", Context.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//
//                            L.w("【蜜蜂303】getCommParams result---->" + param.getResult());
//
//
//                        }
//                    });
//            XposedHelpers.findAndHookMethod("com.honeybee.newapp.ui.CommUrlApi", lpparam.classLoader, "getUniqueId", Context.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//
//                            L.i("【蜜蜂303】getUniqueId result---->" + param.getResult());
//                        }
//                    });
        }

    }

    private void hookVpn(XC_LoadPackage.LoadPackageParam lpparam, String hookClassName, String channelName) {
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

    private void printStackTrace(String methodName) {
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
