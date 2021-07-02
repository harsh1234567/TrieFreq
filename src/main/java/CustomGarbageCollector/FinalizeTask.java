package CustomGarbageCollector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingQueue;

public class FinalizeTask<T> implements Runnable {

	private BlockingQueue<T> refQueue;

	public FinalizeTask(BlockingQueue<T> referenceQueue) {
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
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}