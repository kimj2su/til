package poly.ex.pay3;

@FunctionalInterface
public interface PayFunction<String, Pay> {
    Pay apply(String payType);
}
