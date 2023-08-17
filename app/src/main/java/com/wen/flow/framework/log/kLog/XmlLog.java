package com.wen.flow.framework.log.kLog;
import android.util.Log;

import com.wen.flow.framework.log.KLog;
import com.wen.flow.framework.log.KLogUtil;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * XML格式日誌類別
 * 用於解析並格式化XML字符串，並將其輸出到日誌中
 * <p>
 * <p>
 * 這個類別的主要方法是 printXml，它接收日誌標籤、XML 字符串和日誌標頭訊息。
 * 它嘗試將傳入的 XML 字符串進行格式化，然後在格式化的 XML 字符串前加上日誌標頭訊息。
 * 最後，它將格式化的 XML 字符串輸出到 Android 日誌中。
 * 該類別使用了一些 Android 標準庫中的類別，如 Log、Source、StreamResult 等，
 * 同時也引用了 KLog 和 KLogUtil，這可能是在程式碼中定義或引用的其他地方。
 * 目的是解析、格式化和輸出 XML 字符串，以便在開發過程中更輕鬆地查看和分析 XML 數據。
 */
public class XmlLog {

    /**
     * 將格式化的XML字符串輸出到日誌中
     *
     * @param tag        日誌標籤
     * @param xml        XML字符串
     * @param headString 日誌標頭訊息
     */
    public static void printXml(String tag, String xml, String headString) {
        if (xml != null) {
            xml = XmlLog.formatXML(xml);
            xml = headString + "\n" + xml;
        } else {
            xml = headString + KLog.NULL_TIPS;
        }

        KLogUtil.printLine(tag, true);
        String[] lines = xml.split(KLog.LINE_SEPARATOR);
        for (String line : lines) {
            if (!KLogUtil.isEmpty(line)) {
                Log.d(tag, "║ " + line);
            }
        }
        KLogUtil.printLine(tag, false);
    }

    // 格式化XML字符串的方法
    private static String formatXML(String inputXML) {
        try {
            Source xmlInput = new StreamSource(new StringReader(inputXML));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (Exception e) {
            e.printStackTrace();
            return inputXML;
        }
    }
}
