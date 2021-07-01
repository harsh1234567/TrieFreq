package CustomGarbageCollector;

import java.util.HashSet;
import java.util.Set;

/**
 * @author harsh
 *
 */
public class GCTask implements Runnable {

	private Reference root;
	private Set<Integer> releaseObj;
	private RefQueue refQ;

	public GCTask(Reference root, Set<Integer> releaseObj, RefQueue referenceQueue) {
		this.root = root;
		this.releaseObj = releaseObj;
		this.refQ = referenceQueue;
	}

	@Override
	public void run() {
		System.out.println("Starting GC Task thread");
		Set<Integer> markSetBit = new HashSet<>();
		mark(root, markSetBit);
		sweepReference(root, markSetBit);
	}
	// remove the unused reference .

	private Reference sweepReference(Reference root, Set<Integer> markSet) {
		Object obj = root.getObject();

		int hashCode = obj.hashCode();

		Set<Reference> deleteReferences = new HashSet<>();
		for (Reference reference : root.getReferences()) {
			if (sweepReference(reference, markSet) == null)
				deleteReferences.add(reference);
		}

		addObjectToQueue(deleteReferences);

		root.getReferences().removeAll(deleteReferences);

		if (markSet.contains(hashCode))
			return root;
		return null;
	}

	private void addObjectToQueue(Set<Reference> deleteReferences) {
		for (Reference reference : deleteReferences) {
			try {
				if (reference.getObject().getClass().getDeclaredMethod("finalize") == null)
					continue;
				refQ.add(reference.getObject());
			} catch (NoSuchMethodException e) {
			}
		}
	}
  //mark the used reference 

	private void mark(Reference root, Set<Integer> markSet) {
		
		Object obj = root.getObject();
		int hashCode = obj.hashCode();
		if (releaseObj.contains(hashCode)) {
			return;
		} else if (!markSet.add(hashCode)) {
			return;
		}
		for (Reference reference : root.getReferences()) {
			mark(reference, markSet);
		}
	}
}