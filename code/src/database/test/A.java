package database.test;

import org.junit.Test;
interface B{
	int a=100;
	void add();
}

interface C{
	int a=200;
	void add();
}
public class A implements B,C {

	@Test
	public void testString(){
		B a = new A();
		a.add();
		System.out.println(a.a);
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		System.out.println("aa");
	}
	
}
