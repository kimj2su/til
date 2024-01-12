package poly.ex6;

public class SoundFlyMain {

    public static void main(String[] args) {
        Dog dog = new Dog();
        Bird bird = new Bird();
        Chicken chicken = new Chicken();

        soundAnimal(dog);
        soundAnimal(bird);
        soundAnimal(chicken);

        flyAnimal(bird);
        flyAnimal(chicken);
    }

    // AbstractAnimal을 상속받은 클래스만 매개변수로 받을 수 있음
    private static void soundAnimal(AbstractAnimal animal) {
        System.out.println("동물 소리 테스트 시작");
        animal.sound();
        System.out.println("동물 소리 테스트 종료");
    }

    // Fly를 구현한 클래스만 매개변수로 받을 수 있음
    private static void flyAnimal(Fly animal) {
        System.out.println("동물 비행 테스트 시작");
        animal.fly();
        System.out.println("동물 비행 테스트 종료");
    }
}
