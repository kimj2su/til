package poly.ex.sender;

public class SendMain {

    public static void main(String[] args) {
        Sender[] senders = {new SmsSender(), new EmailSender(), new FaceBookSender()};
        for (Sender sender : senders) {
            sender.send("환영 합니다!");
        }
    }
}
