package com.bing.lan.hello;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * java -javaagent:E:\workspace\my-workspace\jvm-workspace\java-agent-sample\out\artifacts\java_agent_jar\java-agent.jar -jar hello-agent.jar
 * <p>
 * https://cloud.tencent.com/developer/article/1580382?from=article.detail.1574129
 */
public class HelloMain {

  public static void main(String[] args) {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    int count = 0;
    while (true) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
        break;
      }
      count++;
      if (count > 1500) {
        break;
      }
      new PrintUtil().print(dateFormat.format(new Date()));
    }
  }
}