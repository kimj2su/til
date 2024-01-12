package poly.ex2;

public class AnimalPolyMain1 {

    public static void main(String[] args) {
        Animal dog = new Dog();
        Animal cat = new Cat();
        Animal cow = new Cow();

        soundAnimal(dog);
        soundAnimal(cat);
        soundAnimal(cow);
    }

    private static void soundAnimal(Animal ani) {
        System.out.println("동물 소리 테스트 시작");
        ani.sound();
        System.out.println("동물 소리 테스트 종료");
    }
}
