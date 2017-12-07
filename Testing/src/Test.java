
public class Test {
	int testing;
	public static void main(String args[]) {
		Test t = new Test();
		t.testing = 4;
		dosomething(t);
		System.out.print(t.testing);
	}
	private static void dosomething(Test t) {
		t.testing ++;
	}
}
