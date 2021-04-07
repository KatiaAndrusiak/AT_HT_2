package com.miamato;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.logging.log4j.Logger;

public class LogUtil {

    public static void logStackTrace(Throwable e, Logger logger){

        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        logger.error(stringWriter.toString());
    }

}