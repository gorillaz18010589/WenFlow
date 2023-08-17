package com.wen.flow.framework.log.kLog;

import android.util.Log;

import com.wen.flow.framework.log.KLog;

/*
* 提供可超過一般android的log限制
* */
public class BaseLog {
    private static final int MAX_LENGTH = 4000;

    /**
     * 預設的日誌輸出方法，可處理較長的日誌消息
     *
     * @param type 日誌類型（如 KLog.V, KLog.D 等）
     * @param tag  日誌標籤
     * @param msg  日誌訊息
     */
    public static void printDefault(int type, String tag, String msg) {
        int index = 0;
        int length = msg.length();
        int countOfSub = length / MAX_LENGTH;

        // 如果訊息長度超過限制，進行分段輸出
        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + MAX_LENGTH);
                printSub(type, tag, sub);
                index += MAX_LENGTH;
            }
            printSub(type, tag, msg.substring(index, length));
        } else {
            printSub(type, tag, msg);
        }
    }

    // 實際執行日誌輸出的方法
    private static void printSub(int type, String tag, String sub) {
        switch (type) {
            case KLog.V:
                Log.v(tag, sub);
                break;
            case KLog.D:
                Log.d(tag, sub);
                break;
            case KLog.I:
                Log.i(tag, sub);
                break;
            case KLog.W:
                Log.w(tag, sub);
                break;
            case KLog.E:
                Log.e(tag, sub);
                break;
            case KLog.A:
                Log.wtf(tag, sub);
                break;
        }
    }
}
