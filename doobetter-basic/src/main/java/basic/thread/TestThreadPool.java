package basic.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池中只生成了两个线程对象，20个线程目标对象共享他们。从程序中可以看出，使用JDK提供的线程池一般分为3步：
 * 1.创建线程目标对象，可以是不同的，例如程序中的Runner;
 * 2.使用Executors创建线程池，返回一个ExecutorService类型的对象；
 * 3.使用线程池执行线程目标对象，exec.execute(run),阳后，结束线程池中的线程，exec.shutdown();
 * 
 * @author YAO
 *
 */
public class TestThreadPool {

	public static void testCachedThreadPool() {
		// 创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们
		ExecutorService pool = Executors.newCachedThreadPool();
		// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
		Runner t1 = new Runner(1);
		Runner t2 = new Runner(2);
		Runner t3 = new Runner(3);
		Runner t4 = new Runner(4);
		Runner t5 = new Runner(5);
		// 将线程放入池中进行执行
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);

		try {
			Thread.sleep(5000); // 让前几个线程都处理完各自的目标对象，空闲下载后再执行可以重复利用
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pool.execute(t1);
		// 关闭线程池
		pool.shutdown();

	}

	public static void testScheduledThreadPool() {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
		// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
		Runner t1 = new Runner(1);
		Runner t2 = new Runner(2);
		Runner t3 = new Runner(3);
		Runner t4 = new Runner(4);
		Runner t5 = new Runner(5);
		// 将线程放入池中进行执行
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		// 使用延迟执行风格的方法
		pool.schedule(t4, 10, TimeUnit.MILLISECONDS);
		pool.schedule(t5, 10, TimeUnit.MILLISECONDS);
		
		// 关闭线程池
		pool.shutdown();
	}

	public static void testFixedThreadPool() {
		ExecutorService exec = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 20; i++) {
			exec.execute(new Runner(i));
		}
		exec.shutdown();
	}

	public static void testSelfDefined(){
		  //创建等待队列   
      BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(20);   
      //创建一个单线程执行程序，它可安排在给定延迟后运行命令或者定期地执行。   
      ThreadPoolExecutor pool = new ThreadPoolExecutor(2,3,2,TimeUnit.MILLISECONDS,bqueue);   
      //创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口   
      Runner t1 = new Runner(1);   
      Runner t2 = new Runner(2);   
      Runner t3 = new Runner(3);   
      Runner t4 = new Runner(4);   
      Runner t5 = new Runner(5);   
      Runner t6 = new Runner(6);   
      Runner t7 = new Runner(7);   
      //将线程放入池中进行执行   
      pool.execute(t1);   
      pool.execute(t2);   
      pool.execute(t3);   
      pool.execute(t4);   
      pool.execute(t5);   
      pool.execute(t6);   
      pool.execute(t7);   
      //关闭线程池   
      pool.shutdown();
	}
	
	
	public static void main(String[] args) {
		testSelfDefined();
	}

}

class Runner implements Runnable {

	int index = 0;

	public Runner(int index) {
		this.index = index;
	}

	public void run() {
		System.out.println("Thread Name ： " + Thread.currentThread().getName() + " , 目标对象 ：" + index);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
