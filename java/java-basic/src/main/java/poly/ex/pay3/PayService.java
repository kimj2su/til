package poly.ex.pay3;


public class PayService {


    // 변하지 않는 부분
    public void processPay(PayType payType, int amount) {
        System.out.println("결제를 시작합니다: option= " + payType.getOption() + ", amount= " + amount);

        Pay pay = payType.getPay();
        boolean result = pay.pay(amount);

        if (result) {
            System.out.println("결제가 완료되었습니다.");
        } else {
            System.out.println("결제가 실패하였습니다.");
        }
    }
}
