package seminar4.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import seminar4.logic.Course;

import java.util.List;

public class DataBase implements  Repository<Course, Integer>{

    private final SessionFactory sessionFactory;
    private Session session;

    public DataBase(String configFile) {
        sessionFactory = new Configuration()
                .configure(configFile)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();
        createCourseTable();
    }

    /**
     * Добавление курса в тпблицу курсов
     * @param item - курс для добавления
     */
    @Override
    public void add(Course item) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(item);
        System.out.println("Object course save successfully");
        session.getTransaction().commit();
    }

    /**
     * Обновление курса
     * @param item - курс для обновления
     */
    @Override
    public void update(Course item) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Course retrievedCourse = session.get(Course.class, item.getId());
        retrievedCourse.updateDuration();;
        retrievedCourse.updateTitle();
        session.update(retrievedCourse);
        System.out.println("Object course update successfully");
        session.getTransaction().commit();
    }


    /**
     * Удаление курса
     * @param item - курс для удаления
     */
    @Override
    public void delete(Course item) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(session.get(Course.class, item.getId()));
        System.out.println("Object course delete successfully");
        session.getTransaction().commit();
    }

    /**
     * Чтение из таблицы
     * @param integer - id курса
     * @return - найденный по id курс
     */
    @Override
    public Course getById(Integer integer) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Course course = session.get(Course.class, integer);
        session.getTransaction().commit();
        return course;
    }

    @Override
    public void addAll(List<Course> list) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        for (Course item: list) {
            session.save(item);
        }
        System.out.println("List of 'Object course' save successfully");
        session.getTransaction().commit();
    }

    public void createCourseTable() {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String sql = "CREATE TABLE IF NOT EXISTS schoolDB.courses " +
                        "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                        "title VARCHAR(50) NOT NULL, " +
                        "duration INT NOT NULL)";

        Query query = session.createSQLQuery(sql).addEntity(Course.class);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    public void closeDBsession(){
        session.close();
        sessionFactory.close();
    }
}
