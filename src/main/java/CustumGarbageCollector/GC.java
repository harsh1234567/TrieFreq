package CustumGarbageCollector;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class GC {

	private static Reference root = new Reference(new Object());
	private static Map<Integer, Reference> allReferences = new HashMap<>();
	private static ExecutorService gcExecutor = Executors.newSingleThreadExecutor();
	private static ExecutorService finalizeExecutor = Executors.newSingleThreadExecutor();
	private static RefQueue<Object> refObj = new RefQueue<>();
	private static Set<Integer> releaseObj = new HashSet<>();

	private GC() {
	}

	static {
		FinalizeTask<Object> finalizeTask = new FinalizeTask<>(refObj);
		finalizeExecutor.submit(finalizeTask);
	}

	public static Reference get(Object object) {
		return createReferences(object, 0);
	}

///create the reference in the graph 
	private static Reference createReferences(Object object, int count) {

		if (object == null)
			return null;

		int hashCode = object.hashCode();
		Reference reference = allReferences.get(hashCode);
		if (reference == null)
			reference = new Reference(object);

		if (count == 0)
			root.addReference(reference);

		for (Field field : object.getClass().getDeclaredFields()) {
			if (field instanceof Object) {
				try {
					field.setAccessible(true);
					reference.addReference(createReferences(field.get(object), ++count));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		return reference;
	}

//release  the reference from the graph 
	public static synchronized void release(Object object) {
	System.out.println("init release the object");
		if (object == null)
			return;
		releaseObj.add(object.hashCode());
	}

	// call gc for running the clean up process
	public static synchronized void gc() {
		System.out.println("gc call start ");
		gcExecutor.submit(new GCTask(root, new HashSet<Integer>(releaseObj), refObj));
		
		releaseObj.clear();
	   System.out.println("clean up process completed !!");
	}

}