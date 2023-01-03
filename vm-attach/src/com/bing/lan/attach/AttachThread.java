package com.bing.lan.attach;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.IOException;
import java.util.List;

/**
 * 需要将下面 jar 包添加进来作为依赖库
 * D:\develop\jdks\corretto-1.8.0_332\lib\tools.jar
 */
public class AttachThread extends Thread {

  public static void main(String[] args) {
    // 自动检测
    // new AttachThread().start();

    // 如果实在检测不到就手动填写进来吧
    new AttachThread("22316").start();
  }

  /**
   * 记录程序启动时的 VM 集合
   */
  private List<VirtualMachineDescriptor> listBefore = null;
  /**
   * 要加载的agent.jar
   */
  private final String jar = "E:\\workspace\\my-workspace\\jvm-workspace\\java-agent-sample\\out" +
      "\\artifacts\\java_agent_jar\\java-agent.jar";

  private String attachPid = null;

  /**
   * 手动输入 java 进程id
   */
  private AttachThread(String attachPid) {
    this.attachPid = attachPid;
  }

  /**
   * 自动检测 java 进程
   */
  private AttachThread() {
    listBefore = VirtualMachine.list();
  }

  @Override
  public void run() {
    if (attachPid == null) {
      autoCheck();
      return;
    }

    try {
      VirtualMachine vm = VirtualMachine.attach(attachPid);
      if (vm != null) {
        System.out.println("attach成功, 请注意观察目标虚拟机");
        vm.loadAgent(jar);
        vm.detach();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void autoCheck() {
    VirtualMachine vm;
    List<VirtualMachineDescriptor> listAfter;
    int count = 1;

    try {
      do {
        listAfter = VirtualMachine.list();
        vm = hasNewVm(listAfter);
        Thread.sleep(1000);
        System.out.println("第" + count++ + "次检测虚拟机");
      } while (null == vm && count <= 10);

      if (vm != null) {
        System.out.println("检测到虚拟机，即将 attach");
        vm.loadAgent(jar);
        vm.detach();
      } else {
        System.out.println("未检测到虚拟机");
      }
    } catch (Exception e) {
      System.out.println("异常：" + e);
    }
  }

  /**
   * 判断是否有新的 JVM 程序运行
   */
  private VirtualMachine hasNewVm(List<VirtualMachineDescriptor> listAfter) throws IOException, AttachNotSupportedException {
    for (VirtualMachineDescriptor vmd : listAfter) {
      if (!listBefore.contains(vmd)) {
        // 如果 VM 有增加，，我们开始监控这个 VM
        System.out.println("有新的 vm 程序：" + vmd.displayName());
        return VirtualMachine.attach(vmd);
      }
    }
    return null;
  }

}