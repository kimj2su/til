package poly.ex.pay1;

public class KakaoPay implements Pay {

    @Override
    public boolean pay(int amount) {
        System.out.println("카카오 페이 시스템과 연결합니다.");
        System.out.println("결제 금액은 " + amount + "원입니다.");
        return true;
    }
}
