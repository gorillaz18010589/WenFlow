/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.wen.flow.framework.navigation.navigator;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Map;

/**
 * 默認使用hide-show，而不是替換來切換Fragment的FragmentNavigator；
 * 如果需要使用replace來切換Fragment，則可以使用{@link ReplaceFragment}註解來標記
 * 任何fragment即使套用這個方法,但按下系統onBack,該當前的fragment仍然會被殺死,類似replace,所以onBack還是要處理
 * 像我這邊LoginRegisterActivity在loginFragment,registerFragment切換時,我目前是不讓他使用onBack按鈕的處理方式
 */

/*
   用HideShowFragmentNavigator生命流程,navigate方法的replace改成show/hide方式處理,生命週期不會重置
    LoginRegisterActivity -> 一開始初始化在loginFragment ->
    按下tabRegister之後切換到RegisterFragment在按下LoginTab切回到LoginFragment生命流程

*
一開始初始化在loginFragment ->
2023-08-22 00:16:32.889 28997-28997/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:88)#onAttach ] 生命週期 onAttach()
2023-08-22 00:16:32.890 28997-28997/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:45)#onCreate ] 生命週期 onCreate()
2023-08-22 00:16:32.894 28997-28997/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:69)#onCreateView ] 生命週期 onCreateView()
2023-08-22 00:16:33.173 28997-28997/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:63)#onViewCreated ] 生命週期 onViewCreated()
2023-08-22 00:16:33.205 28997-28997/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:57)#onStart ] 生命週期 onStart()
2023-08-22 00:16:33.213 28997-28997/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:51)#onResume ] 生命週期 onResume()
2023-08-22 00:16:35.758 28997-28997/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:106)#onPause ] 生命週期 onPause(),按下register離開頁面才會有暫停

按下tabRegister之後切換到RegisterFragment
2023-08-22 00:16:35.760 28997-28997/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:421)#onAttach ] 生命週期 onAttach()
2023-08-22 00:16:35.761 28997-28997/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:378)#onCreate ] 生命週期 onCreate()
2023-08-22 00:16:35.762 28997-28997/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:402)#onCreateView ] 生命週期 onCreateView()
2023-08-22 00:16:36.044 28997-28997/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:396)#onViewCreated ] 生命週期 onViewCreated()
2023-08-22 00:16:36.046 28997-28997/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:390)#onStart ] 生命週期 onStart()
2023-08-22 00:16:36.060 28997-28997/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:384)#onResume ] 生命週期 onResume()
2023-08-22 00:16:37.739 28997-28997/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:439)#onPause ] 生命週期 onPause() 按下login離開頁面才會有暫停


在按下LoginTab切回到LoginFragment,因為兩個fragment都已經創建之後就只會呼叫onResume
2023-08-22 00:16:37.740 28997-28997/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:51)#onResume ] 生命週期 onResume()

* */

/*
    一般navigation,用replace去替換生命流程
    LoginRegisterActivity -> 一開始初始化在loginFragment -> 按下tabRegister之後切換到RegisterFragment在按下LoginTab切回到LoginFragment生命流程

* 2023-08-22 00:12:13.749 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:88)#onAttach ] 生命週期 onAttach()
2023-08-22 00:12:13.750 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:45)#onCreate ] 生命週期 onCreate()
2023-08-22 00:12:13.754 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:69)#onCreateView ] 生命週期 onCreateView()
2023-08-22 00:12:14.041 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:63)#onViewCreated ] 生命週期 onViewCreated()
2023-08-22 00:12:14.076 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:57)#onStart ] 生命週期 onStart()
2023-08-22 00:12:14.084 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:51)#onResume ] 生命週期 onResume()
2023-08-22 00:12:32.498 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:106)#onPause ] 生命週期 onPause()
2023-08-22 00:12:32.499 28524-28524/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:422)#onAttach ] 生命週期 onAttach()
2023-08-22 00:12:32.500 28524-28524/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:379)#onCreate ] 生命週期 onCreate()
2023-08-22 00:12:32.501 28524-28524/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:403)#onCreateView ] 生命週期 onCreateView()
2023-08-22 00:12:32.760 28524-28524/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:397)#onViewCreated ] 生命週期 onViewCreated()
2023-08-22 00:12:32.763 28524-28524/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:391)#onStart ] 生命週期 onStart()
2023-08-22 00:12:32.777 28524-28524/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:385)#onResume ] 生命週期 onResume()
2023-08-22 00:13:10.283 28524-28524/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:440)#onPause ] 生命週期 onPause()
2023-08-22 00:13:10.285 28524-28524/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:434)#onStop ] 生命週期 onStop()
2023-08-22 00:13:10.291 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:100)#onStop ] 生命週期 onStop()
2023-08-22 00:13:10.293 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:88)#onAttach ] 生命週期 onAttach()
2023-08-22 00:13:10.294 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:45)#onCreate ] 生命週期 onCreate()
2023-08-22 00:13:10.295 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:69)#onCreateView ] 生命週期 onCreateView()
2023-08-22 00:13:10.473 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:63)#onViewCreated ] 生命週期 onViewCreated()
2023-08-22 00:13:10.475 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:57)#onStart ] 生命週期 onStart()
2023-08-22 00:13:10.494 28524-28524/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:416)#onDestroyView ] 生命週期 onDestroyView()
2023-08-22 00:13:10.498 28524-28524/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:410)#onDestroy ] 生命週期 onDestroy()
2023-08-22 00:13:10.498 28524-28524/com.wen.flow V/RegisterFragment.java: [ (RegisterFragment.java:428)#onDetach ] 生命週期 onDetach()
2023-08-22 00:13:10.503 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:82)#onDestroyView ] 生命週期 onDestroyView()
2023-08-22 00:13:10.504 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:76)#onDestroy ] 生命週期 onDestroy()
2023-08-22 00:13:10.505 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:94)#onDetach ] 生命週期 onDetach()
2023-08-22 00:13:10.508 28524-28524/com.wen.flow V/LoginFragment.java: [ (LoginFragment.java:51)#onResume ] 生命週期 onResume()

* */
@Navigator.Name("fragment")
public class HideShowFragmentNavigator extends FragmentNavigator {

    private static final String TAG = "FragmentNavigator";

    private final Context mContext;
    private final FragmentManager mFragmentManager;
    private final int mContainerId;

    public HideShowFragmentNavigator(@NonNull Context context, @NonNull FragmentManager manager,
        int containerId) {
        super(context, manager, containerId);
        mContext = context;
        mFragmentManager = manager;
        mContainerId = containerId;
    }

    @Nullable
    @Override
    public NavDestination navigate(@NonNull Destination destination,
        @Nullable Bundle args, @Nullable NavOptions navOptions,
        @Nullable Navigator.Extras navigatorExtras) {
        if (mFragmentManager.isStateSaved()) {
            Log.i(TAG,
                "Ignoring navigate() call: FragmentManager has already" + " saved its state");
            return null;
        }
        String className = destination.getClassName();
        if (className.charAt(0) == '.') {
            className = mContext.getPackageName() + className;
        }

        final FragmentTransaction ft = mFragmentManager.beginTransaction();

        int enterAnim = navOptions != null ? navOptions.getEnterAnim() : -1;
        int exitAnim = navOptions != null ? navOptions.getExitAnim() : -1;
        int popEnterAnim = navOptions != null ? navOptions.getPopEnterAnim() : -1;
        int popExitAnim = navOptions != null ? navOptions.getPopExitAnim() : -1;
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = enterAnim != -1 ? enterAnim : 0;
            exitAnim = exitAnim != -1 ? exitAnim : 0;
            popEnterAnim = popEnterAnim != -1 ? popEnterAnim : 0;
            popExitAnim = popExitAnim != -1 ? popExitAnim : 0;
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
        }

        /////////////////////////////////////////////
        Fragment fragment = mFragmentManager.getPrimaryNavigationFragment();
        boolean toReplace = false;
        if (fragment != null) {
            toReplace = toReplaceFragment(fragment);
            if (toReplace) {
                //ft.remove(fragment);
            } else {
                ft.setMaxLifecycle(fragment, Lifecycle.State.STARTED);
                ft.hide(fragment);
            }
        }
        Fragment frag;
        if (toReplace) {
            frag = instantiateFragment(mContext, mFragmentManager, className, args);
            frag.setArguments(args);
            ft.replace(mContainerId, frag);
        } else {
            frag = mFragmentManager.findFragmentByTag(className);
            if (frag != null) {
                ft.setMaxLifecycle(frag, Lifecycle.State.RESUMED);
                ft.show(frag);
            } else {
                frag = instantiateFragment(mContext, mFragmentManager, className, args);
                frag.setArguments(args);
                ft.add(mContainerId, frag, className);
            }
        }

        //ft.replace(mContainerId, frag);
        ft.setPrimaryNavigationFragment(frag);
        /////////////////////////////////////////////

        final @IdRes int destId = destination.getId();

        ArrayDeque<Integer> mBackStack=null;
        try {
            Field backStack = FragmentNavigator.class.getDeclaredField("mBackStack");
            backStack.setAccessible(true);
            mBackStack = (ArrayDeque<Integer>) backStack.get(this);
        } catch (Exception e) {
            e.printStackTrace(); // 不可达
        }
        
        final boolean initialNavigation = mBackStack.isEmpty();
        // TODO Build first class singleTop behavior for fragments
        final boolean isSingleTopReplacement = navOptions != null
            && !initialNavigation
            && navOptions.shouldLaunchSingleTop()
            && mBackStack.peekLast() == destId;

        boolean isAdded;
        if (initialNavigation) {
            isAdded = true;
        } else if (isSingleTopReplacement) {
            // Single Top means we only want one instance on the back stack
            if (mBackStack.size() > 1) {
                // If the Fragment to be replaced is on the FragmentManager's
                // back stack, a simple replace() isn't enough so we
                // remove it from the back stack and put our replacement
                // on the back stack in its place
                mFragmentManager.popBackStack(
                    generateBackStackName(mBackStack.size(), mBackStack.peekLast()),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ft.addToBackStack(generateBackStackName(mBackStack.size(), destId));
            }
            isAdded = false;
        } else {
            ft.addToBackStack(generateBackStackName(mBackStack.size() + 1, destId));
            isAdded = true;
        }
        if (navigatorExtras instanceof Extras) {
            Extras extras = (Extras) navigatorExtras;
            for (Map.Entry<View, String> sharedElement : extras.getSharedElements().entrySet()) {
                ft.addSharedElement(sharedElement.getKey(), sharedElement.getValue());
            }
        }
        ft.setReorderingAllowed(true);
        ft.commit();
        // The commit succeeded, update our view of the world
        if (isAdded) {
            mBackStack.add(destId);
            return destination;
        } else {
            return null;
        }
    }

    @NonNull
    private String generateBackStackName(int backStackIndex, int destId) {
        return backStackIndex + "-" + destId;
    }

    /**
     * 前一个Fragment是否需要被replace掉
     *
     * @return
     */
    private static boolean toReplaceFragment(Fragment fragment) {
        return fragment.getClass().isAnnotationPresent(ReplaceFragment.class);
    }
}
