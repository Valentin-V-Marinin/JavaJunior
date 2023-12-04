package seminar2.task1;


public abstract class Animal {

    protected String name;
    protected int age;


    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void makeSound(){
        System.out.println("Some specific sound");
    };

    @Override
    public String toString() {
        return "Animal{" +
                "animal kind: " + this.getClass().getSimpleName() +
                ",  name: '" + name + '\'' +
                ",  age: " + age +
                '}';
    }
}
