package lapTrinhPhanTan;

import java.util.Random;

public class test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyThread.mang = new int[new Random().nextInt((100 - 50) + 1) + 50];
		
		for (int i = 0; i < MyThread.mang.length; i++) {
			MyThread.mang[i] = new Random().nextInt(50);
		}
		
		MyThread t1 = new MyThread(true);
		MyThread t2 = new MyThread(false);
		
		t1.start();
		t2.start();
		
		try { t1.join();t2.join(); }
        catch (InterruptedException ie) { }
        
//		for(int i : MyThread.mang)
//			System.out.print(i + " ");
	}

}
