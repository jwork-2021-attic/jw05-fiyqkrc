package com.pFrame.log;

public class Log {

    public static final int Error=1;
    public static final int Warning=2;
    public static final int Info=3;

    private static int logLevel=3;

    public static void setLogLevel(int a){
        Log.logLevel=a;
    }

    public static void ErrorLog(Object obj,String message){
        if(Log.logLevel>=Error)
            System.out.println(String.format("%s report Error: %s", obj.getClass().getName(),message));
    }

    public static void WarningLog(Object obj,String message){
        if(Log.logLevel>=Warning)
            System.out.println(String.format("%s report Warning: %s",obj.getClass().getName(),message));
    }

    public static void InfoLog(Object obj,String message){
        if(Log.logLevel>=Info)
            System.out.println(String.format("%s report Info: %s",obj.getClass().getName(),message));
    }
}
