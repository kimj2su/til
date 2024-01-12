package poly.diamond;

public class Child implements InterfaceA, InterfaceB {
    @Override
    public void methodA() {
        System.out.println("methodA() in Child");
    }

    @Override
    public void methodB() {
        System.out.println("methodB() in Child");
    }

    @Override
    public void methodCommon() {
        System.out.println("methodCommon() in Child");
    }
}
