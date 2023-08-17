package com.wen.flow.framework.log;

import android.text.TextUtils;


import androidx.annotation.Nullable;

import com.wen.flow.framework.log.kLog.BaseLog;
import com.wen.flow.framework.log.kLog.FileLog;
import com.wen.flow.framework.log.kLog.JsonLog;
import com.wen.flow.framework.log.kLog.XmlLog;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * This is a Log tool，with this you can the following
 * <ol>
 * <li>use KLog.d(),you could print whether the method execute,and the default tag is current class's name</li>
 * <li>use KLog.d(msg),you could print log as before,and you could location the method with a click in Android Studio Logcat</li>
 * <li>use KLog.json(),you could print json string with well format automatic</li>
 * </ol>
 *
 * @author zhaokaiqiang
 * github https://github.com/ZhaoKaiQiang/KLog
 * 15/11/17 扩展功能，添加对文件的支持
 * 15/11/18 扩展功能，增加对XML的支持，修复BUG
 * 15/12/8  扩展功能，添加对任意参数的支持
 * 15/12/11 扩展功能，增加对无限长字符串支持
 * 16/6/13  扩展功能，添加对自定义全局Tag的支持,修复内部类不能点击跳转的BUG
 * 16/6/15  扩展功能，添加不能关闭的KLog.debug(),用于发布版本的Log打印,优化部分代码
 * 16/6/20  扩展功能，添加堆栈跟踪功能KLog.trace()
 */
public final class KLog {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String NULL_TIPS = "Log with null object";

    private static final String DEFAULT_MESSAGE = "execute";
    private static final String PARAM = "Param";
    private static final String NULL = "null";
    private static final String TAG_DEFAULT = "KLog";
    private static final String SUFFIX = ".java";

    public static final int JSON_INDENT = 4;

    public static final int V = 0x1;
    public static final int D = 0x2;
    public static final int I = 0x3;
    public static final int W = 0x4;
    public static final int E = 0x5;
    public static final int A = 0x6;

    private static final int JSON = 0x7;
    private static final int XML = 0x8;

    private static final int STACK_TRACE_INDEX_5 = 5;
    private static final int STACK_TRACE_INDEX_4 = 4;

    private static String mGlobalTag;
    private static boolean mIsGlobalTagEmpty = true;
    private static boolean IS_SHOW_LOG = true;


    // 初始化方法，用於設定是否顯示日誌
    public static void init(boolean isShowLog) {
        IS_SHOW_LOG = isShowLog;
    }

    // 初始化方法，用於設定是否顯示日誌和全局標籤
    public static void init(boolean isShowLog, @Nullable String tag) {
        IS_SHOW_LOG = isShowLog;
        mGlobalTag = tag;
        mIsGlobalTagEmpty = TextUtils.isEmpty(mGlobalTag);
    }

    public static void v() {
        printLog(V, null, DEFAULT_MESSAGE);
    }

    public static void v(Object msg) {
        printLog(V, null, msg);
    }

    public static void v(String tag, Object... objects) {
        printLog(V, tag, objects);
    }

    public static void d() {
        printLog(D, null, DEFAULT_MESSAGE);
    }

    public static void d(Object msg) {
        printLog(D, null, msg);
    }

    public static void d(String tag, Object... objects) {
        printLog(D, tag, objects);
    }

    public static void i() {
        printLog(I, null, DEFAULT_MESSAGE);
    }

    public static void i(Object msg) {
        printLog(I, null, msg);
    }

    public static void i(String tag, Object... objects) {
        printLog(I, tag, objects);
    }

    public static void w() {
        printLog(W, null, DEFAULT_MESSAGE);
    }

    public static void w(Object msg) {
        printLog(W, null, msg);
    }

    public static void w(String tag, Object... objects) {
        printLog(W, tag, objects);
    }

    public static void e() {
        printLog(E, null, DEFAULT_MESSAGE);
    }

    public static void e(Object msg) {
        printLog(E, null, msg);
    }

    public static void e(String tag, Object... objects) {
        printLog(E, tag, objects);
    }

    public static void a() {
        printLog(A, null, DEFAULT_MESSAGE);
    }

    public static void a(Object msg) {
        printLog(A, null, msg);
    }

    public static void a(String tag, Object... objects) {
        printLog(A, tag, objects);
    }

    // 格式化輸出 JSON 字符串日誌
    public static void json(String jsonFormat) {
        printLog(JSON, null, jsonFormat);
    }

    // 格式化輸出帶有指定標籤的 JSON 字符串日誌
    public static void json(String tag, String jsonFormat) {
        printLog(JSON, tag, jsonFormat);
    }

    // 格式化輸出 XML 字符串日誌
    public static void xml(String xml) {
        printLog(XML, null, xml);
    }

    // 格式化輸出帶有指定標籤的 XML 字符串日誌
    public static void xml(String tag, String xml) {
        printLog(XML, tag, xml);
    }

    // 將日誌輸出到檔案中
    public static void file(File targetDirectory, Object msg) {
        printFile(null, targetDirectory, null, msg);
    }

    // 將帶有指定標籤的日誌輸出到檔案中
    public static void file(String tag, File targetDirectory, Object msg) {
        printFile(tag, targetDirectory, null, msg);
    }

    // 將帶有指定標籤和檔案名稱的日誌輸出到檔案中
    public static void file(String tag, File targetDirectory, String fileName, Object msg) {
        printFile(tag, targetDirectory, fileName, msg);
    }

    // 輸出 Debug 等級的日誌，即使在正式版本也顯示
    public static void debug() {
        printDebug(null, DEFAULT_MESSAGE);
    }

    // 輸出帶有指定標籤的 Debug 等級的日誌，即使在正式版本也顯示
    public static void debug(Object msg) {
        printDebug(null, msg);
    }

    // 輸出帶有指定標籤和參數的 Debug 等級的日誌，即使在正式版本也顯示
    public static void debug(String tag, Object... objects) {
        printDebug(tag, objects);
    }

    public static void trace() {
        printStackTrace();
    }

    // 輸出堆疊追踪信息，用於查看方法調用堆疊
    private static void printStackTrace() {

        if (!IS_SHOW_LOG) {
            return;
        }

        Throwable tr = new Throwable();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        String message = sw.toString();

        String traceString[] = message.split("\\n\\t");
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (String trace : traceString) {
            if (trace.contains("at com.socks.library.KLog")) {
                continue;
            }
            sb.append(trace).append("\n");
        }
        String[] contents = wrapperContent(STACK_TRACE_INDEX_4, null, sb.toString());
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        BaseLog.printDefault(D, tag, headString + msg);
    }

    /**
     * 根據不同類型的日誌，將輸出內容分配給對應的輸出類別進行處理
     */
    private static void printLog(int type, String tagStr, Object... objects) {

        if (!IS_SHOW_LOG) {
            return;
        }

        // 將輸出內容封裝為陣列，其中包含標籤、訊息和頭部字串
        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        // 根據日誌類型進行分支處理
        switch (type) {
            case V:
            case D:
            case I:
            case W:
            case E:
            case A:
                // 輸出常規的日誌
                BaseLog.printDefault(type, tag, headString + msg);
                break;
            case JSON:
                // 輸出 JSON 字符串日誌
                JsonLog.printJson(tag, msg, headString);
                break;
            case XML:
                // 輸出 XML 字符串日誌
                XmlLog.printXml(tag, msg, headString);
                break;
        }

    }

    /**
     * 輸出 Debug 等級的日誌，即使在正式版本也會顯示
     */
    private static void printDebug(String tagStr, Object... objects) {
        // 將輸出內容封裝為陣列，其中包含標籤、訊息和頭部字串
        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        // 輸出 Debug 等級的日誌，即使在正式版本也顯示
        BaseLog.printDefault(D, tag, headString + msg);
    }

    /**
     * 將日誌輸出到檔案中
     */
    private static void printFile(String tagStr, File targetDirectory, String fileName, Object objectMsg) {

        if (!IS_SHOW_LOG) {
            return;
        }

        // 將輸出內容封裝為陣列，其中包含標籤、訊息和頭部字串
        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objectMsg);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        // 將日誌輸出到檔案中
        FileLog.printFile(tag, targetDirectory, fileName, headString, msg);
    }

    /**
     * 封裝並返回輸出內容的相關資訊
     */
    private static String[] wrapperContent(int stackTraceIndex, String tagStr, Object... objects) {

        // 獲取當前線程的堆疊追踪
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        // 獲取特定索引處的堆疊追踪元素
        StackTraceElement targetElement = stackTrace[stackTraceIndex];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }

        if (className.contains("$")) {
            className = className.split("\\$")[0] + SUFFIX;
        }

        // 獲取方法名稱和行號
        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }

        // 設定標籤，優先使用全局標籤
        String tag = (tagStr == null ? className : tagStr);

        if (mIsGlobalTagEmpty && TextUtils.isEmpty(tag)) {
            tag = TAG_DEFAULT;
        } else if (!mIsGlobalTagEmpty) {
            tag = mGlobalTag;
        }

        // 獲取訊息，如果 objects 為 null 則顯示 NULL_TIPS，否則格式化參數
        String msg = (objects == null) ? NULL_TIPS : getObjectsString(objects);
        // 組合頭部字串
        String headString = "[ (" + className + ":" + lineNumber + ")#" + methodName + " ] ";

        // 返回封裝好的內容
        return new String[]{tag, msg, headString};
    }

    /**
     * 格式化輸出參數的字串
     */
    private static String getObjectsString(Object... objects) {
        // 如果參數數量大於 1，則逐個格式化並拼接
        if (objects.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(NULL).append("\n");
                } else {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(object.toString()).append("\n");
                }
            }
            return stringBuilder.toString();
        }
        // 如果只有一個參數，則直接轉換成字串
        else if (objects.length == 1) {
            Object object = objects[0];
            return object == null ? NULL : object.toString();
        }
        // 如果參數為空，返回 NULL
        else {
            return NULL;
        }
    }


}