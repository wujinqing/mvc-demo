package com.jin.mvc.demo.thread;

/**
 * 1.wait只能在synchronized代码块里面调用。
 * 2.wait会释放锁(释放调用wait方法的对象的锁)
 * 3.可以通过notify()/notifyAll()来唤醒或者在等待完指定时间后自动唤醒，（这两个方法只能在synchronized代码块里面调用）
 *
 *
 * Thread.sleep暂停当前线程，并且不会释放任何锁
 *
 * https://www.baeldung.com/java-thread-lifecycle
 *
 * @author wu.jinqing
 * @date 2021年09月03日
 */
public class Test1 {
    private static Object lock = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            synchronized (lock)
            {
                synchronized (lock2) {
                    System.out.println("t1.1");
                    try {
                        lock.wait(5000);
                        System.out.println("t1.2");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock)
            {
//                synchronized (lock2) {
                    System.out.println("t2.1");
                    try {
                        lock.wait(5000);
                        System.out.println("t2.2");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//            }
        });

        t1.start();
        Thread.sleep(100);
        t2.start();
    }
}
