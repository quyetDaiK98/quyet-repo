package buoi3;

public class Philosopher implements Runnable {
	int id = 0;
    Resource r = null;
    public Philosopher(int initId, Resource initr) {
        id = initId;
        r = initr;
        new Thread(this).start();
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
