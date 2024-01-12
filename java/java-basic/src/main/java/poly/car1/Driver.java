package poly.car1;

public class Driver {

    private Car car;

    public void setCar(Car car) {
        System.out.println("운전자가 " + car.getClass().getSimpleName() + "를(을) 선택합니다.");
        this.car = car;
    }

    public void drive() {
        System.out.println("운전을 시작합니다.");
        car.startEngine();
        car.pressAccelerator();
        car.offEngine();
    }
}
