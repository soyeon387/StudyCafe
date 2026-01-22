import java.time.*;

public class Customer {
    private String id;
    private String pw;
    private int remainingMinutes = 0;
    private LocalDateTime checkInTime;
    private boolean isCheckedIn = false;
    private boolean isOut = false;
    private LocalDateTime outTime;
    private final int ALLOWED_OUT_SECONDS = 60; 

    public Customer(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public boolean checkPW(String inputPW) {
        return this.pw.equals(inputPW);
    }

    public boolean checkIn() {
        if (remainingMinutes <= 0) {
            System.out.println("이용 가능한 시간이 없습니다. 이용권을 구매해주세요.");
            return false;
        }

        if (isOut) {
            Duration outDuration = Duration.between(outTime, LocalDateTime.now());
            long outSeconds = outDuration.getSeconds();

            if (outSeconds <= ALLOWED_OUT_SECONDS) {
                isOut = false;
                checkInTime = LocalDateTime.now(); 
                isCheckedIn = true;
                System.out.println("재입실 처리되었습니다.");
                return true;
            } else {
                isCheckedIn = false;
                isOut = false;
                checkInTime = null;
                System.out.println("외출 시간이 초과되어 자동 퇴실되었습니다.");
                return false;
            }

        } else if (!isCheckedIn) {
            isCheckedIn = true;
            checkInTime = LocalDateTime.now();
            System.out.println(id + " 님이 입실하셨습니다.");
            return true;
        } else {
            System.out.println("이미 입실 상태입니다.");
            return false;
        }
    }

    public void checkOut() {
        if (isCheckedIn && checkInTime != null) {
            Duration used = Duration.between(checkInTime, LocalDateTime.now());
            long usedSeconds = used.getSeconds(); 

            long allowedSeconds = remainingMinutes * 60; 

            if (usedSeconds > allowedSeconds) { 
                long overtimeSeconds = usedSeconds - allowedSeconds;
                System.out.println(overtimeSeconds + "초가 초과되었습니다. 초과한 시간을 결제해주세요.");

                int pricePerMinute = 100;
                int extraFee = (int) Math.ceil(overtimeSeconds / 60.0) * pricePerMinute; 
                System.out.println("추가 요금: " + extraFee + "원");
            } else {
                System.out.println("남은 시간은 퇴실 시 자동 저장됩니다.");
            }

            remainingMinutes = Math.max(remainingMinutes - (int) (usedSeconds / 60), 0);
        }

        isCheckedIn = false;
        isOut = false;
        checkInTime = null;

        System.out.println("퇴실 처리되었습니다.");
    }

    public void goOut() {
        if (isCheckedIn && !isOut) {
            isOut = true;
            outTime = LocalDateTime.now();
            System.out.println("외출시간은 1분입니다. \n시간 초과 시 자동으로 퇴실됩니다.");
        } else {
            System.out.println("외출할 수 없습니다.");
        }
    }

    public void addMinutes(int minutes) {
        remainingMinutes += minutes;
    }

    public int getEffectiveRemainingMinutes() {
        if (isCheckedIn && checkInTime != null) {
            Duration used = Duration.between(checkInTime, LocalDateTime.now());
            long usedSeconds = used.getSeconds();
            return Math.max(remainingMinutes - (int) (usedSeconds / 60), 0);
        } else {
            return remainingMinutes;
        }
    }

    public void showRemainingTime() {
        int effective = getEffectiveRemainingMinutes();

        if (isCheckedIn) {
            System.out.println("현재 남은 시간: " + effective + "분");
        }
    }

    public int getRemainingMinutes() {
        return remainingMinutes;
    }

    public String getId() {
        return id;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public boolean isOut() {
        return isOut;
    }
}
