package com.example.myapplication;

import android.content.Context;

import java.lang.reflect.Member;
import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedInside implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        L.i("inside XposedInside ...pkg: " + lpparam.packageName);
        if ("com.leiting.lt100".equals(lpparam.packageName)) {
            XposedHelpers.findAndHookMethod("com.leiting.lt100.ui.CommUrlApi", lpparam.classLoader, "MD5", String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                            L.i("MD5 thisObject-Method---->" + (param.thisObject instanceof Method));
                            L.i("MD5 thisObject-Class---->" + (param.thisObject instanceof Class));

                            L.i("MD5 result---->" + param.getResult());
                            L.i("MD5 args1---->" + param.args[0]);
                            printStackTrace("MD5");
                        }
                    });


            XposedHelpers.findAndHookMethod("com.leiting.lt100.ui.CommUrlApi", lpparam.classLoader, "getCommParams", Context.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                            L.i("getCommParams result---->" + param.getResult());
                            printStackTrace("getCommParams");


                        }
                    });
        }
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
