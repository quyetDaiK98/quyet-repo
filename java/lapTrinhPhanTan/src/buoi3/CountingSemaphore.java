package buoi3;

import buoi2.Util;

public class CountingSemaphore {
	 int value;
	    public CountingSemaphore(int initValue) {
	        value = initValue;
	    }
	    public synchronized void P() {
	        value--;
	        if (value < 0) Util.myWait(this);
	    }
	    public synchronized void V() {
	        value++;
	        if (value <= 0) notify();
	    }
}
