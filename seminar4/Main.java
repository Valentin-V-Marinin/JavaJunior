package seminar4;

import seminar4.logic.Course;
import seminar4.repository.DataBase;

import java.util.ArrayList;

/*
Создайте базу данных (например, SchoolDB).
В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
Настройте Hibernate для работы с вашей базой данных.
Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
Убедитесь, что каждая операция выполняется в отдельной транзакции.
*/

public class Main {
    public static void main(String[] args) {

        DataBase dataBase = new DataBase("hibernate.cfg.xml");
        try {
            // создаём программу обучения из 5 курсов
            int PROGRAM_NUMBER_COURSES = 5;
            ArrayList<Course> courses = new ArrayList<>();
            Course course = Course.create(courses);
            while (courses.size() < PROGRAM_NUMBER_COURSES) {
                courses.add(course);
                course = Course.create(courses);
            }

            // Заносим программу обучения в БД
            dataBase.add(courses.get(2));
            dataBase.addAll(courses);
            System.out.println(courses);

            // Обновляем 3-й курс из программы обучения
            dataBase.update(courses.get(2));
            System.out.println(courses);

            // Читаем 5-й курс из программы обучения
            System.out.println(dataBase.getById(4));

            // Удаляем 2-й курс из программы обучения
            dataBase.delete(courses.get(1));
            System.out.println(courses);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            dataBase.closeDBsession();
        }
    }
}
