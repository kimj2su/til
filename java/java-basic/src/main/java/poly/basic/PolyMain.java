package poly.basic;

public class PolyMain {

    public static void main(String[] args) {
        Parent parent = new Parent();
        parent.parentMethod();

        Child child = new Child();
        child.childMethod();

        // 부모 타입은 자식 객체를 가리킬 수 있다.
        Parent parent1 = new Child();
        parent1.parentMethod();
    }
}
