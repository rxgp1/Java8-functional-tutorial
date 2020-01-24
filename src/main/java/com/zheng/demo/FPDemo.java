package com.zheng.demo;

public class FPDemo {
	public static void main(String[] args) {
		GreetingFunction greeting = message -> System.out.println("Hello " + message + "!");
		greeting.speak("Tom");
		greeting.speak("Mary");

		caculateTwoNumbers(3, 4);
		caculateTwoNumbers(3, 0);
	}

	private static void caculateTwoNumbers(int x, int y) {
		IntegerCalculator add = (a, b) -> a + b;
		IntegerCalculator diff = (a, b) -> a - b;
		IntegerCalculator divide = (a, b) -> (b == 0 ? 0 : a / b);

		System.out.println(x + " + " + y + " = " + add.caculate(x, y));
		System.out.println(x + " - " + y + " = " + diff.caculate(x, y));
		System.out.println(x + " / " + y + " = " + divide.caculate(x, y));
	}
}
