package com.bing.lan.agent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import static com.bing.lan.agent.PreMain.CLAZZ_FILE;

/**
 * Created by lanbing at 2022/12/30 18:02
 */

public class MyClassFileTransformer implements ClassFileTransformer {

  @Override
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
      byte[] classfileBuffer) throws IllegalClassFormatException {

    if (!className.equalsIgnoreCase("com/bing/lan/hello/PrintUtil")) {
      System.out.println("loader className: " + className);
      return null;
    }
    System.out.println("=================================================================");
    System.out.println("======= transform className: " + className + " =======");
    System.out.println("=================================================================");
    return getBytesFromFile(CLAZZ_FILE);
  }

  public static byte[] getBytesFromFile(String fileName) {
    File file = new File(fileName);
    try (InputStream is = new FileInputStream(file)) {
      // precondition
      long length = file.length();
      byte[] bytes = new byte[(int) length];
      // Read in the bytes
      int offset = 0;
      int numRead = 0;
      while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
        offset += numRead;
      }
      if (offset < bytes.length) {
        throw new IOException("Could not completely read file " + file.getName());
      }
      is.close();
      return bytes;
    } catch (Exception e) {
      System.out.println("error occurs in _ClassTransformer!" + e.getClass().getName());
      return null;
    }
  }
}