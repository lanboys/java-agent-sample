package com.bing.lan.hello;

import java.lang.management.ManagementFactory;

/**
 * Created by lanbing at 2023/1/3 13:32
 */

public class PrintUtil {

  private static String PID = "-1";
  private static long pid = -1;

  static {
    // https://stackoverflow.com/a/7690178
    try {
      String jvmName = ManagementFactory.getRuntimeMXBean().getName();
      int index = jvmName.indexOf('@');

      if (index > 0) {
        PID = Long.toString(Long.parseLong(jvmName.substring(0, index)));
        pid = Long.parseLong(PID);
      }
    } catch (Throwable e) {
      // ignore
    }
  }

  public void print(String msg) {
    // System.out.println("我是被替换的类...");
    System.out.println("PID: " + pid + "   " + msg);
  }

}
