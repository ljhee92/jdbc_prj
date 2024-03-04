package day0304;

/**
 * Singleton Pattern이 도입된 클래스
 */
public class Singleton {
	
	// 객체를 유지하기 위해서 선언한 클래스 변수
	private static Singleton singleton;
	
	// 1. 클래스 외부에서 객체 생성을 막기 위해 생성자를 private으로 설정 
	private Singleton() {
	}	// Singleton
	
	/**
	 * 클래스 외부에서 객체를 사용할 수 있도록 method 작성
	 * @return Singleton 객체
	 */
	public static Singleton getInstance() {
		// 객체를 하나로 유지하면서 생성하는 코드 작성 
		if(singleton == null) {
			singleton = new Singleton();
		}	// end if
		return singleton;
	}	// getInstance

}	// Singleton