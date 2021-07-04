package com.accolite.gc;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

/**
 * @author harsh
 *
 */
public class GCTask implements Runnable {

	private Reference root;
	private Set<Integer> releaseObj;
	private BlockingQueue refQ;

	public GCTask(Reference root, Set<Integer> releaseObj, BlockingQueue referenceQueue) {
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

	private boolean sweepReferenceCheck(Reference reference, Set<Integer> markSet) {
		return sweepReference(reference, markSet) == null;
	}

	// remove the unused reference from reference graph .
	private Reference sweepReference(Reference root, Set<Integer> markSet) {
		Object obj = root.getObject();

		int hashCode = System.identityHashCode(obj);

		Set<Reference> deleteReferences = root.getReferences().stream()
				.filter(reference -> sweepReferenceCheck(reference, markSet)).collect(Collectors.toSet());

		addObjectToQueue(deleteReferences);

		root.getReferences().removeAll(deleteReferences);

		if (markSet.contains(hashCode))
			return root;
		return null;
	}

//add release reference to queue to finalize the object
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

	// mark the used reference
	private void mark(Reference root, Set<Integer> markSet) {

		Object obj = root.getObject();
		int hashCode = System.identityHashCode(obj);
		if (releaseObj.contains(hashCode)) {
			return;
		} else if (!markSet.add(hashCode)) {
			return;
		}
		root.getReferences().stream().forEach(reference -> mark(reference, markSet));

	}
}