package seminar3.task1;


/*

Задача №1
Разработайте класс Student с полями String name, int age, transient double
GPA (средний балл). Обеспечьте поддержку сериализации для этого класса.
Создайте объект класса Student и инициализируйте его данными. Сериализуйте
этот объект в файл. Десериализуйте объект обратно в программу из файла.
Выведите все поля объекта, включая GPA, и обсудите, почему значение GPA
не было сохранено/восстановлено.

Задача №1
Выполнить задачу 1 используя другие типы сериализаторов (в xml и json документы).
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();

    public static void main(String[] args) {

        Student student = new Student("Петров И.И.", 21, 4.75);
        Student studentDeserial;

        // сереализуем
        try(FileOutputStream fileOutputStream = new FileOutputStream("student_data.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);){
            objectOutputStream.writeObject(student);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // десериализуем (в новый объект класса Student)
        // поле GPA не сериализуется/десереализуется из-за инструкции 'transient'
        try(FileInputStream fileInputStream = new FileInputStream("student_data.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);){
            studentDeserial = (Student) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(studentDeserial);
        System.out.println("*************************** Задпча №2 ***************************************");

        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(studentDeserial);

        saveToFile("students.json", students);
        saveToFile("students.xml", students);

        System.out.println("------------------ JSON -------------");
        List<Student> studentsDes = loadFromFile("students.json");
        for (Student stud: studentsDes) {
            System.out.println(stud);
        }

        System.out.println("------------------ XML  -------------");
        List<Student> studentsDesserial = loadFromFile("students.xml");
        for (Student stud: studentsDesserial) {
            System.out.println(stud);
        }
    }

    public static void saveToFile(String fileName, List<Student> students) {
        try {
            if (fileName.endsWith(".json")) {
                objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                objectMapper.writeValue(new File(fileName), students);
            } else if (fileName.endsWith(".bin")) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                    oos.writeObject(students);
                }
            } else if (fileName.endsWith(".xml")) {
                xmlMapper.writeValue(new File(fileName), students);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Student> loadFromFile(String fileName) {
        List<Student> students = new ArrayList<>();

        File file = new File(fileName);
        if (file.exists()) {
            try {
                if (fileName.endsWith(".json")) {
                    students = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
                } else if (fileName.endsWith(".bin")) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                        students = (List<Student>) ois.readObject();
                    }
                } else if (fileName.endsWith(".xml")) {
                    students = xmlMapper.readValue(file, xmlMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return students;
    }

}
