package poly.ex.pay3;


public class NaverPay implements Pay {

    @Override
    public boolean pay(int amount) {
        System.out.println("네이버 페이 시스템과 연결합니다.");
        System.out.println("결제 금액은 " + amount + "원입니다.");
        return true;
    }
}
