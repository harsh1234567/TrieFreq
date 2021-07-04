package com.accolite.gc;

import com.accolite.gc.model.C;
import com.accolite.gc.model.Department;
import com.accolite.gc.model.Employee;

public class MainGarbage {
	// test garbage collector release C object from reference (from memory )
	public static void testGarbageCollector() {
		try {
			C c1 = new C();
			GC.get(c1);
			GC.release(c1);
			GC.gc();
		} catch (Exception e) {
		}
	}

	// test garbage collector release department object with has-a relation ship
	// with Employee class.
	public static void testGarbageCollectorHierachyClass() {
		try {
			Employee e = new Employee();
			Department d = new Department(e);
			Department d1 = new Department(e);
			GC.get(d);
			GC.get(d1);
			GC.release(d1);
			GC.gc();
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		testGarbageCollector();
		System.out.println("create employee and department object then release from reference");
		testGarbageCollectorHierachyClass();
	}

}
