package com.bing.lan.agent;

import com.bing.lan.hello.HelloMain;
import com.bing.lan.hello.PrintUtil;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * Created by lanbing at 2023/1/3 11:15
 */

public class AgentMain {

  public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
    inst.addTransformer(new MyClassFileTransformer(), true);
    // retransformClasses 是 Java SE 6 里面的新方法，它跟 redefineClasses 一样，可以批量转换类定义
    inst.retransformClasses(PrintUtil.class);
    System.out.println("我是两个参数的 Java Agent agentmain");
  }

  public static void agentmain(String agentArgs) {
    System.out.println("我是一个参数的 Java Agent agentmain");
  }
}
