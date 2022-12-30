package com.bing.lan.agent;

import com.bing.lan.hello.PrintUtil;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Objects;

public class PreMain {

  public static final String CLAZZ_FILE = "E:\\workspace\\my-workspace\\jvm-workspace\\java-agent-sample\\transform-clazz\\PrintUtil.class";

  public static void premain(String agentArgs, Instrumentation instrumentation) {
      /*
          转换发生在 premain 函数执行之后，main 函数执行之前，这时每装载一个类，transform 方法就会执行一次，看看是否需要转换，
          所以，在 transform（Transformer 类中）方法中，程序用 className.equals("TransClass") 来判断当前的类是否需要转换。
      */

    System.out.println("我是两个参数的 Java Agent premain");
    // 方式一：
    addTransformer(instrumentation);

    // 方式二：
    // redefineClasses(instrumentation);
  }

  private static void addTransformer(Instrumentation instrumentation) {
    instrumentation.addTransformer(new MyClassFileTransformer());
  }

  private static void redefineClasses(Instrumentation instrumentation) {
    ClassDefinition def = new ClassDefinition(PrintUtil.class, Objects.requireNonNull(MyClassFileTransformer
        .getBytesFromFile(CLAZZ_FILE)));
    try {
      instrumentation.redefineClasses(new ClassDefinition[]{def});
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (UnmodifiableClassException e) {
      throw new RuntimeException(e);
    }
  }

  public static void premain(String agentArgs) {
    System.out.println("我是一个参数的 Java Agent premain");
  }
}
