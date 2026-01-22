import java.util.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CafePOS implements MachineInterface{
    private static Scanner sc = new Scanner(System.in);
    private static Map<String, Customer> customers = new HashMap<>();
    private static Admin admin;
    private static List<TimeProduct> timeProducts = new ArrayList<>();
    private static int totalRevenue = 0;
    
    public void start() {
    	admin = new Admin(sc);
    	
        while (true) {
        	System.out.println("[전체 메뉴]");
            System.out.println("1. 고객 로그인");
            System.out.println("2. 고객 회원가입");
            System.out.println("3. 관리자 로그인");
            System.out.println("4. 종료");
            System.out.print("--> ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": customerLogin(); break;
                case "2": registerCustomer(); break;
                case "3": adminLogin(); break;
                case "4": System.out.println("프로그램 종료"); return;
                default: System.out.println("잘못된 선택입니다.");
            }
        }
    }

    private  void customerLogin() {
        System.out.print("id: ");
        String id = sc.nextLine();
        System.out.print("pw: ");
        String pw = sc.nextLine();

        if (customers.containsKey(id) && customers.get(id).checkPW(pw)) {
            System.out.println("로그인 완료");
            runCustomerMenu(customers.get(id));
        } else {
            System.out.println("정보가 일치하지 않습니다.");
        }
    }

    private static void registerCustomer() {
        String id;
        while (true) {
            System.out.print("id 생성: ");
            id = sc.nextLine();
            if (customers.containsKey(id)) {
                System.out.println("이미 존재하는 id입니다. 다시 입력해주세요.");
            } else {
                break;
            }
        }
        System.out.print("pw 설정: ");
        String pw = sc.nextLine();
        customers.put(id, new Customer(id, pw));
        System.out.println("회원가입이 정상처리되었습니다.");
    }

    private static void runCustomerMenu(Customer customer) {
        while (true) {
        	System.out.println("[고객 메뉴]");
            System.out.println("1. 입실");
            System.out.println("2. 이용권구매");
            System.out.println("3. 남은시간 조회");
            System.out.println("4. 퇴실 및 외출");
            System.out.println("5. 로그아웃");
            System.out.print("--> ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": customer.checkIn(); break;
                case "2":
                    if (timeProducts.isEmpty()) {
                        System.out.println("등록된 이용권이 없습니다. 관리자에게 문의하세요.");
                        break;
                    }

                    System.out.println("\n[이용권 목록]");
                    for (int i = 0; i < timeProducts.size(); i++) {
                        TimeProduct tp = timeProducts.get(i);
                        System.out.println((i + 1) + ". " + tp.getName() + ": " + tp.getMinutes() + "분 -> " + tp.getPrice() + "원");
                    }
                    int selected;
                    while (true) {
                        selected = getIntInput("구매할 이용권 번호 선택: ");
                        selected = selected - 1;
                        if (selected < 0 || selected >= timeProducts.size()) {
                            System.out.println("잘못된 번호입니다. 다시 선택해주세요.");
                        } else {
                            break;
                        }
                    }

                    TimeProduct selectedProduct = timeProducts.get(selected);
                    customer.addMinutes(selectedProduct.getMinutes());
                    totalRevenue += selectedProduct.getPrice();

                    System.out.println("'" + selectedProduct.getName() + "'을 선택하셨습니다.");
                    System.out.println("지불할 금액은 "+ selectedProduct.getPrice() + "원입니다.");
                    break;

                case "3":
                    customer.showRemainingTime();
                    break;

                case "4":
                	System.out.println("1. 퇴실\n2. 외출");
                    System.out.print("--> ");
                	String subChoice = sc.nextLine();
                	if (subChoice.equals("1")) {
                	    customer.checkOut();
                	} else if (subChoice.equals("2")) {
                	    customer.goOut();
                	} else {
                	    System.out.println("잘못된 선택입니다. 1 또는 2를 입력해주세요.");
                	}
                    break;
                case "5": System.out.println("로그아웃되었습니다."); return;
                default: System.out.println("잘못된 선택입니다.");
            }
        }
    }

    private static void adminLogin() {
    	System.out.println("관리자 권한이 있어야 하는 메뉴입니다.");
    	System.out.print("관리자 id: ");
    	String inputId = sc.nextLine();
    	System.out.print("관리자 pw: ");
    	String inputPw = sc.nextLine();

    	if (admin.checkAdmin(inputId, inputPw)) {
    	    System.out.println("로그인 완료");
    	    runAdminMenu();
    	}
    	else {
            System.out.println("관리자 인증 실패.");
        }
    }

    private static void runAdminMenu() {
        while (true) {
        	System.out.println("[관리자 메뉴]");
            System.out.println("1. 이용권 등록");
            System.out.println("2. 회원조회");
            System.out.println("3. 매출집계");
            System.out.println("4. 이전메뉴");
            System.out.print("--> ");
            String choice = sc.nextLine();

            switch (choice) {
            case "1":
                System.out.println("[이용권 등록 방법 선택]");
                System.out.println("1. 파일 입력");
                System.out.println("2. 직접 입력");
                int inputType = getIntInput("--> ");

                if (inputType == 1) {
                	
                    System.out.print("파일 경로 입력: ");
                    String filePath = sc.nextLine();

                    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] parts = line.split(",");
                            if (parts.length != 3) continue;

                            String name = parts[0].trim();
                            int minutes = Integer.parseInt(parts[1].trim());
                            int price = Integer.parseInt(parts[2].trim());

                            timeProducts.add(new TimeProduct(name, minutes, price));
                            System.out.println("'" + name + "' 이용권이 등록되었습니다.");
                        }
                    } catch (IOException | NumberFormatException e) {
                        System.out.println("파일을 읽는 도중 오류가 발생했습니다: " + e.getMessage());
                    }

                } else if (inputType == 2) {
                    int count = getIntInput("등록할 상품 개수 입력: ");
                    for (int i = 1; i <= count; i++) {
                        System.out.println("[" + i + "번째 상품]");
                        System.out.print("상품 이름 입력: ");
                        String name = sc.nextLine();
                        int minutes = getIntInput("제공 시간(분) 입력: ");
                        int price = getIntInput("가격 입력: ");
                        timeProducts.add(new TimeProduct(name, minutes, price));
                        System.out.println(" '" + name + "' 등록 완료\n");
                    }
                } else {
                    System.out.println("잘못된 선택입니다.");
                }
                break;


                case "2":
                    System.out.println("\n[전체 고객 목록]");
                    for (Customer c : customers.values()) {
                        System.out.println("ID: " + c.getId() + " | 입실: " + c.isCheckedIn() + " | 외출: " + c.isOut() + " | 남은시간: " + c.getRemainingMinutes() + "분");
                    }
                    break;
                case "3":
                    System.out.println("총 매출: " + totalRevenue + "원");
                    break;
                case "4":
                    System.out.println("전체 메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }
}



