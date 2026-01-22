# ☕ StudyCafe Kiosk Project (스터디카페 키오스크)

Java Swing을 기반으로 구현한 스터디카페 무인 관리 키오스크 프로그램
관리자와 사용자 모드를 분리하여 좌석 구매, 시간 충전, 매출 관리 등의 기능을 제공

---

🛠 Tech Stack (기술 스택)
- Language: Java (JDK 21)
- GUI: Java Swing (AWT/Swing)
- Data Management: File I/O (Text file processing)
- Tool: Eclipse IDE, Git/GitHub

---

📂 Project Structure & Logic (주요 로직)

이 프로젝트는 객체 지향 원칙(OOP)을 적용하여 사용자와 관리자의 역할을 분리하고, 인터페이스를 통해 기능 구현

1. 주요 클래스 구성
* MainUI.java: 프로그램의 진입점. 초기 로그인 및 모드 선택(관리자/사용자) 화면을 담당
* CafePOS.java: 키오스크의 핵심 비즈니스 로직을 처리 (주문 처리, 시간 계산 등)
* MachineInterface.java: 키오스크의 공통 기능(로그인, 메뉴 조회 등)을 정의한 인터페이스
* Admin.java & Customer.java: 사용자 유형별 데이터를 관리하는 클래스
* TimeProduct.java: 시간권(상품) 정보를 객체화하여 관리

2. 데이터 처리
* product.txt: 별도의 데이터베이스 서버 없이 텍스트 파일을 이용해 상품 정보와 매출 내역을 영구 저장하고 불러옴

---

💡 Problem Solving (문제 해결 과정)

1. 데이터 영속성 문제 해결
* 문제: 프로그램 종료 시 메모리에 저장된 주문 내역이 사라지는 문제가 발생
* 해결: Java의 File I/O (FileReader/BufferedReader)를 사용하여 product.txt 파일에 데이터를 쓰고 읽는 기능을 구현, DB 없이도 데이터가 유지되도록 만들었습니다.

2. 사용자/관리자 기능 분리
* 문제: 한 소스 코드 내에서 권한 관리가 복잡해지는 문제가 있었습니다.
* 해결: Admin과 Customer 클래스로 역할을 명확히 분리하고, 로그인 시 ID에 따라 UI 흐름이 달라지도록 설계하여 유지보수성을 높였습니다.

3. 확장성을 고려한 설계
* 접근: MachineInterface를 도입하여 향후 기능이 추가되더라도 인터페이스 규격에 맞춰 구현하면 되도록 설계했습니다.

---

🏆 Achievements (성과 및 배운 점)
* Java GUI 프로그래밍 숙달: Swing 라이브러리를 활용하여 이벤트 리스너(Event Listener) 처리와 화면 전환 로직 구현
* 파일 입출력 실습: 텍스트 파싱(Parsing)을 통해 데이터를 저장하고 불러오는 로우 레벨의 데이터 관리 방식을 이해
* Git 활용: 이클립스와 깃허브를 연동하여 소스 코드의 버전 관리.
