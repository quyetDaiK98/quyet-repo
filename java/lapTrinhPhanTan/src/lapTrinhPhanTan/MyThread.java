package lapTrinhPhanTan;

public class MyThread extends Thread {
	public static int[] mang;
	boolean isThread1;
	
	public MyThread(boolean isThread1) {
		// TODO Auto-generated constructor stub
		this.isThread1 = isThread1;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(isThread1) {
			for(int i = 0; i < mang.length/2; i++) {
				if (mang[i] % 5 == 0) {
					System.out.println("t1-chia-het-cho-5: " + mang[i]);
				}
			}
		}
		else {
			for(int i = mang.length/2; i < mang.length; i++) {
				if (mang[i] % 3 == 0) {
					System.out.println("t2-chia-het-cho-3: " + mang[i]);
				}
			}
		}
	}
	
	

}
