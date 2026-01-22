import java.util.Scanner;

public class Admin {
    private String id;
    private String pw;

    public Admin(Scanner scanner) {
    	System.out.println("-------------------");
		System.out.println("[POS Start!]");
		System.out.println("-------------------");
		System.out.println("관리자를 생성합니다.");
        System.out.print("새로운 id를 설정하세요(숫자, 영문_대소문자 생성 가능): ");
        this.id = scanner.nextLine();
        System.out.print("새로운 pw를 설정하세요(숫자, 영문_대소문자 생성 가능): ");
        this.pw = scanner.nextLine();
        System.out.print("새로운 관리자 id: " + this.id + "\t pw: ");
		   for (int i = 0; i < pw.length(); i++) {
		       System.out.print("*");
		   }
		   System.out.println();
    }

    public boolean checkAdmin(String inputId, String inputPw) {
        return this.id.equals(inputId) && this.pw.equals(inputPw);
    }
}

