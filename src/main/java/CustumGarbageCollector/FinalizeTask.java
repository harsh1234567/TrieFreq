package CustumGarbageCollector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FinalizeTask<T> implements Runnable {

	private RefQueue<T> refQueue;

	public FinalizeTask(RefQueue<T> referenceQueue) {
		this.refQueue = referenceQueue;
	}

	@Override
	public void run() {
		System.out.println("Inside Finalize Task");

		while (true) {
			T object = refQueue.remove();
			try {
				Method finalize = object.getClass().getDeclaredMethod("finalize");

				if (finalize == null)
					continue;

				finalize.invoke(object);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}