package com.simizu;


public class Solver {
	private Field[] fields;
	
	public Solver(Field[] fields) {
		this.fields = fields;
	}
	
	public void solve() {
		fieldsLoop : for (int i = 0; i < fields.length; i++) {
			if (!fields[i].getEditability()) continue;
			while (fields[i].tryNextNum()) {
				if (fields[i].isConflicting()) {
					continue;
				} else {
					continue fieldsLoop;
				}
			}
			i-=2;
			while (!fields[i+1].getEditability()) i--;
		}
	}
	
	
}
