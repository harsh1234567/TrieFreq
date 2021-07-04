package com.accolite.gc.model;

public class Department {
	private Employee e;

	public Department(Employee e) {
		this.e = e;
	}

	public void finalized() {
		System.out.println("destroy Department ");
	}
}
