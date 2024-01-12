package poly.ex.pay0;

public class PayMain0 {

    public static void main(String[] args) {
        PayService payService = new PayService();

        // kakao
        String payOption = "kakao";
        int amount = 1000;
        payService.processPay(payOption, amount);

        // naver
        String payOption2 = "naver";
        int amount2 = 2000;
        payService.processPay(payOption2, amount2);

        // fail
        String payOption3 = "fail";
        int amount3 = 3000;
        payService.processPay(payOption3, amount3);
    }
}
