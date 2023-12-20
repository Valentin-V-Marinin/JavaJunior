package seminar4.logic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


@Entity
@Table(name = "courses")

public class Course {

    private static final String[] titles = new String[] { "JavaJunior", "JavaDevelopmentKit", "JavaBasics", "ObjectOrientedProgramming", "DataBase&SQL", "Containers&Docker", "OperatingSystems", "ProgramSoftwareArchitecture"};
    private static final Random random = new Random();

    private static final int MAX_NUMBER_COURSES = 12;
    private static final int MIN_NUMBER_COURSES = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;  // название курса

    @Column(name = "duration")
    private int duration;  // количество лекции на курсе

    public Course() {

    }

    public Course(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public static Course create(ArrayList<Course> courses){

        HashSet<String> coursesTitle = new HashSet<>();
        for (Course item: courses) {
            coursesTitle.add(item.title);
        }

        Course newCourse = new Course(titles[random.nextInt(titles.length)], random.nextInt(MIN_NUMBER_COURSES, MAX_NUMBER_COURSES));
        while (coursesTitle.contains(newCourse.title)){
           newCourse = new Course(titles[random.nextInt(titles.length)], random.nextInt(MIN_NUMBER_COURSES, MAX_NUMBER_COURSES));
        }

        return newCourse;
    }



    public void updateDuration(){
        duration = random.nextInt(MIN_NUMBER_COURSES, MAX_NUMBER_COURSES);
    }

    public void updateTitle(){
        title = titles[random.nextInt(titles.length)];
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Курс {" +
                "id=" + id +
                ", название курса='" + title + '\'' +
                ", количество лекций=" + duration +
                '}';
    }
}
