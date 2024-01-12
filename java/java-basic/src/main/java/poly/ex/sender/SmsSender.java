package poly.ex.sender;

public class SmsSender implements Sender {
    @Override
    public void send(String message) {
        System.out.println("SMS를 발송합니다: " + message);
    }
}
