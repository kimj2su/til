package poly.ex.pay3;

public enum PayType {
    KAKAO("kakao", payType -> new KakaoPay()),
    NAVER("naver", payType -> new NaverPay());

    private String option;
    private PayFunction<String, Pay> payFunction;

    PayType(String option, PayFunction<String, Pay> payFunction) {
        this.option = option;
        this.payFunction = payFunction;
    }

    public String getOption() {
        return option;
    }

    public Pay getPay() {
        return payFunction.apply(this.option);
    }
}
