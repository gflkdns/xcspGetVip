package com.example.myapplication;

import com.example.myapplication.impl.AllMethodHool;
import com.example.myapplication.impl.VPNHool;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedInside implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        VPNHool.hook(lpparam);
        AllMethodHool.hook(lpparam);
    }


}
