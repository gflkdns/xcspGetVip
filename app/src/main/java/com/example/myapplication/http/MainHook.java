package com.example.myapplication.http;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * createTime: 2020/4/24.9:45
 * updateTime: 2020/4/24.9:45
 * author: singleMan.
 * desc:
 */
public class MainHook implements IXposedHookLoadPackage {

    final String TARGET_PACKAGE_NAME = "";//被Hook的包名

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals("com.xcsp.xcfilms"))
            return;
        XposedHelpers.findAndHookMethod("com.stub.StubApp",
                loadPackageParam.classLoader,
                "attachBaseContext",
                Context.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        //获取到Context对象，通过这个对象来获取classloader
                        Context context = (Context) param.args[0];
                        //获取classloader，之后hook加固后的就使用这个classloader
                        ClassLoader classLoader = context.getClassLoader();
                        Log.d("sanbo", "已经绕过360加固:" + classLoader.getClass());

                        OkHttpHooker.attach(classLoader);

                        Class BrowserApiService = Class.forName("com.xiaocao.p2p.data.source.http.BrowserApiService", true, classLoader);

                        Log.d("sanbo", "BrowserApiService!=null");
                        Method[] methods = BrowserApiService.getDeclaredMethods();
                        Log.d("sanbo", "methods!=null" + methods.length);
                        for (int j = 0; j < methods.length; j++) {
                            try {
                                Log.d("sanbo", "hook-->" + methods[j].getName());


//                                int finalJ = j;
//                                XposedHelpers.findAndHookMethod("com.xiaocao.p2p.data.source.http.BrowserApiService",
//                                        classLoader,
//                                        methods[j].getName(), methods[j].getParameterTypes(), new XC_MethodHook() {
//                                            @Override
//                                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                                Log.d("sanbo", "api " + methods[finalJ].getName() + "-->" + param.getResult());
//                                            }
//                                        });
                            } catch (Throwable e) {
                                Log.d("sanbo", "hook err-->" + methods[j].getName(), e);
                            }
                        }


                        XposedHelpers.findAndHookMethod("me.goldze.mvvmhabit.http.BaseResponse",
                                classLoader,
                                "getResult", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        // Log.d("sanbo", "getResult "+ new Gson().toJson(param.getResult()));
                                    }
                                });
                        XposedHelpers.findAndHookMethod("com.xiaocao.p2p.entity.RecommandVideosEntity",
                                classLoader,
                                "getHide_ad", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        Log.d("sanbo", "getHide_ad ");
                                        param.setResult(1);
                                    }
                                });
                        XposedHelpers.findAndHookMethod("com.xiaocao.p2p.entity.RecommandVideosEntity",
                                classLoader,
                                "getIs_share", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        Log.d("sanbo", "getIs_share ");
                                        param.setResult(1);
                                    }
                                });
                        //去广告
                        XposedHelpers.findAndHookMethod("b.k.a.k.p0",
                                classLoader,
                                "v", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        Log.d("sanbo", "v ");
                                        param.setResult(true);
                                    }
                                });

                    }
                });

        XposedHelpers.findAndHookMethod("android.app.Activity",
                loadPackageParam.classLoader,
                "onCreate", android.os.Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        Log.d("sanbo", "5当前页面:" + param.thisObject.getClass());
                    }
                });


    }
}
