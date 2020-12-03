package buoi3;

import java.util.Random;

import buoi2.Util;

class Producer implements Runnable {
    BoundedBuffer b = null;
    public Producer(BoundedBuffer initb) {
        b = initb;
        new Thread(this).start();
    }
    public void run() {
        double item;
        Random r = new Random();
        while (true) {
            item = 10.0 + (50.0 - 10.0) * r.nextDouble();
            System.out.println("t1 " + item);
            b.deposit(item);
            Util.mySleep(2000);
        }
    }
}
class Consumer implements Runnable {
    BoundedBuffer b = null;
    public Consumer(BoundedBuffer initb) {
        b = initb;
        new Thread(this).start();
    }
    public void run() {
        double item;
        while (true) {
            item = b.fetch();
            System.out.println("t2 " + item*item);
            Util.mySleep(4000);
        }
    }
}
public class ProducerConsumer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BoundedBuffer buffer = new BoundedBuffer();
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
	}

}
