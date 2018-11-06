package com.pi.core.util;


import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pi.uikit.BuildConfig;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Locale;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class DebugLog {

    private static boolean IS_SHOW_LOG = BuildConfig.DEBUG;

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static final int D = 0x2;
    private static final int E = 0x5;
    private static final int JSON = 0x7;
    private static final int XML = 0x8;



    private DebugLog() {}

    public static void init(boolean isShowLog) {
        IS_SHOW_LOG = isShowLog;
    }

    public static void d(String msg) {
        printLog(D, null, msg);
    }

    public static void d(String tag, String msg) {
        printLog(D, tag, msg);
    }

    public static void d(String tag, String msg, int offset) {
        printLog(D, tag, msg, offset);
    }

    public static void e(String msg) {
        printLog(E, null, msg);
    }

    public static void e(String tag, String msg) {
        printLog(E, tag, msg);
    }

    public static void json(String jsonFormat) {

            printLog(JSON, "json", jsonFormat);
    }

    public static void xml(String xml) {
        printLog(XML, null, xml);
    }

    private static void printLog(int type, String tagStr, String msg, int offset) {
        if (IS_SHOW_LOG) {
            Thread currentThread = Thread.currentThread();
            StackTraceElement[] stackTrace = currentThread.getStackTrace();

            int index = 4 + offset;
            String className = stackTrace[index].getFileName();
            String methodName = stackTrace[index].getMethodName();
            int lineNumber = stackTrace[index].getLineNumber();

            String tag = (tagStr == null ? "DebugLog" : tagStr);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TID:").append(String.format(Locale.getDefault(), "%06d", currentThread.getId()))
                    .append(" [ (").append(className).append(":").append(lineNumber)
                    .append(") <").append(methodName).append(">").append(" ] ");

            if (msg != null && type != JSON && type != XML) {
                stringBuilder.append(msg);
            }

            String logStr = stringBuilder.toString();
//            FileLogUtil.logInfo(tag + ":" + logStr + (type == JSON || type == XML ? msg : ""));
            switch (type) {
                case D:
                    Log.d(tag, logStr);
                    break;
                case E:
                    Log.e(tag, logStr);
                    break;
                case JSON: {
                    if (TextUtils.isEmpty(msg)) {
                        Log.d(tag, "Empty or Null json content");
                        return;
                    }
                    String message;
                    try {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        JsonParser parser = new JsonParser();
                        JsonElement element = parser.parse(msg);
                        message = gson.toJson(element);
//                        FileLogUtil.logInfo(message);
                    } catch (Exception e) {
                        e("Error in print log:" + e.getMessage());
                        return;
                    }
                    printLine(tag, true);
                    message = logStr + LINE_SEPARATOR + message;
                    String[] lines = message.split(LINE_SEPARATOR);
                    StringBuilder jsonContent = new StringBuilder();
                    for (String line : lines) {
//                        line = line.length() > 200 ? (line.substring(0, 200) + "...") : line;
                        jsonContent.append(line).append(LINE_SEPARATOR);
                    }
                    Log.d(tag, jsonContent.toString());
                    printLine(tag, false);
                }
                break;
                case XML:{
                    if (TextUtils.isEmpty(msg)) {
                        Log.d(tag, "Empty or Null json content");
                        return;
                    }
                    printLine(tag, true);
                    try {
                        int commIndex = msg.indexOf("<");
                        String header = msg.substring(0, commIndex) + "\n";
                        String xml = msg.substring(commIndex);
                        Source xmlInput = new StreamSource(new StringReader(xml));
                        StringWriter stringWriter = new StringWriter();
                        StreamResult xmlOutput = new StreamResult(stringWriter);
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                        transformer.setOutputProperty(OutputKeys.METHOD, "html");
                        transformer.transform(xmlInput, xmlOutput);
                        String xmlString = xmlOutput.getWriter().toString();
                        Log.d(tag, header + xmlString);
                    } catch (Exception e) {
                        Log.d(tag, msg);
                    }
                    printLine(tag, false);
                }
                break;
            }
        }
    }

    private static void printLog(int type, String tagStr, String msg) {
        printLog(type, tagStr, msg, 1);
    }

    private static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }
}