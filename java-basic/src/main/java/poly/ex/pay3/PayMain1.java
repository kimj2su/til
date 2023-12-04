package poly.ex.pay3;


public class PayMain1 {

    public static void main(String[] args) {
        PayService payService = new PayService();

        // kakao
        PayType kakao = PayType.KAKAO;
        int amount = 1000;
        payService.processPay(kakao, amount);

        // naver
        PayType naver = PayType.NAVER;
        int amount2 = 2000;
        payService.processPay(naver, amount2);
    }
}
