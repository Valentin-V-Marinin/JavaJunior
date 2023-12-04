package seminar2.task1;

/*
* Задача 1:
Создайте абстрактный класс "Animal" с полями "name" и "age".
Реализуйте два класса-наследника от "Animal" (например, "Dog" и "Cat") с уникальными полями и методами.
Создайте массив объектов типа "Animal" и с использованием Reflection API выполните следующие действия:
Выведите на экран информацию о каждом объекте.
Вызовите метод "makeSound()" у каждого объекта, если такой метод присутствует.

Дополнительная задача:

Доработайте метод генерации запроса на удаление объекта из таблицы БД (DELETE FROM <Table> WHERE ID = '<id>')
В классе QueryBuilder который мы разработали на семинаре.
* */


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        //Создаём массив объектов типа "Animal"
        Animal[] animals = new Animal[7];
        animals[0] = new Cat("Bonifatsyi", 3, "Piter Neva");
        animals[1] = new Cat("Rokky", 4, "Siamese cat");
        animals[2] = new Cat("Archee", 2, "Siamese cat");
        animals[3] = new Dog("Tsezar", 5, 20.5);
        animals[4] = new Cat("Richard", 10, "Siamese cat");
        animals[5] = new Dog("Zhuchka", 3, 7);
        animals[6] = new Cat("Garry", 9, "Siberia cat");


        // Выводим на экран информацию о каждом объекте с
        // использованием Reflection API
        // и вызываем метод makeSound()

        for (int i = 0; i < animals.length; i++) {
            try {
                classDescriptor(animals[i]);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }

    /**
     * вывод информации о классе
     */
    public static <T> void classDescriptor(T obj) throws IllegalAccessException, InvocationTargetException {

        Class<?> objClass = obj.getClass();
        Class<?> objSuperClass = objClass.getSuperclass(); //obj.getClass().getSuperclass();

        Constructor[] constructors = objClass.getDeclaredConstructors();
        System.out.println("Конструкторы класса " + objClass.getSimpleName() + ":");
        for (Constructor item : constructors) {
            System.out.println(" - " + item.getName());
            System.out.println("     параметры конструктора:");
            System.out.println("        " + Arrays.toString(item.getParameters()));
        }

        System.out.println();
        System.out.println("Поля класса " + objClass.getSimpleName() + " со значениями объекта:");
        System.out.println(" - собственные:");
        Field[] fields = objClass.getDeclaredFields();
        for (Field itemField : fields) {
            itemField.setAccessible(true);
            System.out.printf("     %s:  %s\n", itemField.getName(), itemField.get(obj));
        }

        System.out.println(" - унаследованные от " + objSuperClass.getSimpleName() + ":");
        Field[] superfields = objSuperClass.getDeclaredFields();
        for (Field itemField : superfields) {
            itemField.setAccessible(true);
            System.out.printf("     %s:  %s\n", itemField.getName(), itemField.get(obj));
        }

        System.out.println();
        System.out.println("Методы класса " + objClass.getSimpleName() + ":");
        Method[] methods = objClass.getDeclaredMethods();
        for (Method itemMethod : methods) {
            itemMethod.setAccessible(true);
            System.out.printf(" - %s\n", itemMethod.getName());
            System.out.print("      ");
            if (itemMethod.getReturnType() == void.class){
                itemMethod.invoke(obj);
            } else {
                System.out.println(itemMethod.invoke(obj));
            }
        }
    }
}
