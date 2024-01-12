package poly.ex.pay1;

public class PayService {


    // 변하지 않는 부분
    public void processPay(String option, int amount) {
        System.out.println("결제를 시작합니다: option= " + option + ", amount= " + amount);

        Pay pay = PayStore.findPay(option);
        boolean result = pay.pay(amount);

        if (result) {
            System.out.println("결제가 완료되었습니다.");
        } else {
            System.out.println("결제가 실패하였습니다.");
        }
    }
}
