package basic.thread;

/**
 * 使用中断(Interrupt)取消线程
 * 使用join等待另外一个线程结束
 * @author YAO
 *
 */
public class TestThreadCmd {

	public static void main(String[] args) {
		System.out.println("Main start..");
		RunnerA a = new RunnerA(1,0);
		Thread t = new Thread(a);
		t.start();
		try {
	        t.join(2000);
        } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		t.interrupt();

		System.out.println(a.getCount());
	}

}

class RunnerA implements Runnable {

	int index = 0;

	int count = 0;

	public RunnerA(int index) {
		this.index = index;
	}

	public RunnerA(int index, int count) {
		this.index = index;
		this.count = count;
	}

	public void run() {
		System.out.println("Thread Name ： " + Thread.currentThread().getName() + " , 目标对象 ：" + index);
		try {
			for (int i = 0; i < 1_000_000; i++) {
				this.count++;
				Thread.sleep(100);
				if (Thread.interrupted()) {
					System.out.println("线程内发现interrupted");
					throw new InterruptedException();

				}
			}
		} catch (InterruptedException e) {
			System.out.println("处理异常...");
		}
		
	}
	public int getCount(){
		return this.count;
	}
}
