package com.wen.flow.framework.log.kLog;


import android.util.Log;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * 文件日誌類別
 * 用於將日誌訊息寫入文件
 */
public class FileLog {

    private static final String FILE_PREFIX = "KLog_";
    private static final String FILE_FORMAT = ".log";

    /**
     * 將日誌訊息寫入文件
     *
     * @param tag             日誌標籤
     * @param targetDirectory 目標文件夾
     * @param fileName        文件名稱（可選，如為null將自動生成）
     * @param headString      日誌標頭訊息
     * @param msg             日誌訊息
     */
    public static void printFile(String tag, File targetDirectory, @Nullable String fileName, String headString, String msg) {
        fileName = (fileName == null) ? getFileName() : fileName;

        // 嘗試將日誌訊息保存到指定的文件中
        if (save(targetDirectory, fileName, msg)) {
            Log.d(tag, headString + " 寫入日誌成功！檔案位於 >>>" + targetDirectory.getAbsolutePath() + "/" + fileName);
        } else {
            Log.e(tag, headString + "寫入日誌失敗！");
        }
    }

    // 實際執行將日誌訊息保存到文件的方法
    private static boolean save(File dic, @NonNull String fileName, String msg) {
        File file = new File(dic, fileName);

        try {
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(msg);
            outputStreamWriter.flush();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 生成文件名稱的方法
    private static String getFileName() {
        Random random = new Random();
        return FILE_PREFIX + Long.toString(System.currentTimeMillis() + random.nextInt(10000)).substring(4) + FILE_FORMAT;
    }
}
