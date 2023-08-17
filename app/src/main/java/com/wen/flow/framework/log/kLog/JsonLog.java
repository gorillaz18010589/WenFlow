package com.wen.flow.framework.log.kLog;

import android.util.Log;

import com.wen.flow.framework.log.KLog;
import com.wen.flow.framework.log.KLogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON格式日誌類別
 * 用於解析並格式化JSON字符串，並將其輸出到日誌中
 */
public class JsonLog {

    /**
     * 將格式化的JSON字符串輸出到日誌中
     *
     * @param tag        日誌標籤
     * @param msg        JSON字符串
     * @param headString 日誌標頭訊息
     */
    public static void printJson(String tag, String msg, String headString) {

        String message;

        try {
            // 嘗試解析JSON字符串，並根據JSON格式進行格式化
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(KLog.JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(KLog.JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        // 輸出日誌
        KLogUtil.printLine(tag, true);
        message = headString + KLog.LINE_SEPARATOR + message;
        String[] lines = message.split(KLog.LINE_SEPARATOR);
        for (String line : lines) {
            Log.d(tag, "║ " + line);
        }
        KLogUtil.printLine(tag, false);
    }
}
