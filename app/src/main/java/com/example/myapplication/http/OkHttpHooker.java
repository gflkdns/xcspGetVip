package com.example.myapplication.http;

import static com.example.myapplication.http.OkCompat.Cls_OkHttpClient$Builder;
import static com.example.myapplication.http.OkCompat.F_Builder_interceptors;
import static com.example.myapplication.http.OkCompat.M_Builder_build;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.getObjectField;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;


/**
 * createTime: 2020/4/16.13:56
 * updateTime: 2020/4/16.13:56
 * author: singleMan.
 * desc:
 */
public class OkHttpHooker {

    public static final String TAG = "sanbo";


    /**
     * @param classLoader
     */
    public static void attach(final ClassLoader classLoader) {

        Class OkHttpClient_BuilderClass = null;
        try {
            OkHttpClient_BuilderClass = Class.forName(Cls_OkHttpClient$Builder, true, classLoader);
            findAndHookMethod(OkHttpClient_BuilderClass, M_Builder_build, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    List interceptors = (List) getObjectField(param.thisObject, F_Builder_interceptors);
                    for (int i = 0; i < interceptors.size(); i++) {
                        Log.d(TAG, "找到拦截器：" + interceptors.get(i).getClass());
                        Method[] methods = interceptors.get(i).getClass().getDeclaredMethods();
                        for (int j = 0; j < methods.length; j++) {
                            Log.d(TAG, "\t" + methods[i].toString());
                        }
                    }
                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "没有发现OkHttp相关，可能未使用 or 被混淆");
        }


    }

}
