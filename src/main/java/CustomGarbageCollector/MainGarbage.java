package CustomGarbageCollector;

class C {

}

public class MainGarbage {

	public static void main(String[] args) {
		try {
			C c1 = new C();

			GC.get(c1);
			GC.release(c1);

			GC.gc();
		} catch (Exception e) {
	}
	}

}
