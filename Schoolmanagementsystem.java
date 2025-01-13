import java.util.*;

class Person {
    String name;
    int age;
    String id;

    public Person(String name, int age, String id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", ID: " + id;
    }
}

class Student extends Person {
    int attendance; // percentage
    List<String> grades;
    String assignedClass;

    public Student(String name, int age, String id, int attendance, String assignedClass) {
        super(name, age, id);
        this.attendance = attendance;
        this.grades = new ArrayList<>();
        this.assignedClass = assignedClass;
    }

    public boolean isEligibleForExam() {
        return attendance >= 75;
    }

    public void addGrade(String grade) {
        grades.add(grade);
    }

    @Override
    public String toString() {
        return super.toString() + ", Assigned Class: " + assignedClass + ", Attendance: " + attendance + "%";
    }
}

class Teacher extends Person {
    List<String> subjects;
    List<String> assignedClasses;

    public Teacher(String name, int age, String id) {
        super(name, age, id);
        this.subjects = new ArrayList<>();
        this.assignedClasses = new ArrayList<>();
    }

    public boolean assignClass(String className) {
        if (assignedClasses.size() < 3) {
            assignedClasses.add(className);
            return true;
        } else {
            System.out.println(name + " cannot be assigned more than 3 classes.");
            return false;
        }
    }

    public void addSubject(String subject) {
        subjects.add(subject);
    }

    @Override
    public String toString() {
        return super.toString() + ", Subjects: " + subjects + ", Assigned Classes: " + assignedClasses;
    }
}

class School {
    List<Student> students;
    List<Teacher> teachers;
    Map<String, List<Student>> classes;

    public School() {
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        classes = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.add(student);
        classes.computeIfAbsent(student.assignedClass, k -> new ArrayList<>()).add(student);
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public boolean assignTeacherToClass(Teacher teacher, String className) {
        if (classes.containsKey(className) && teacher.assignClass(className)) {
            return true;
        }
        return false;
    }

    public void generateStudentReport(String studentId) {
        Student student = getStudentById(studentId);
        if (student != null) {
            System.out.println("Progress Report for " + student.name + ":");
            System.out.println("Grades: " + student.grades);
            System.out.println("Attendance: " + student.attendance + "%");
            System.out.println("Eligible for Exam: " + (student.isEligibleForExam() ? "Yes" : "No"));
        } else {
            System.out.println("Student not found.");
        }
    }

    public Student getStudentById(String id) {            
        
        for (Student student : students) {
            if (student.id.equals(id)) {
                return student;
            }
        }
        return null;
    }

    public Teacher getTeacherById(String id) {
        for (Teacher teacher : teachers) {
            if (teacher.id.equals(id)) {
                return teacher;
            }
        }
        return null;
    }

    public boolean addStudentToClass(String studentId, String className) {
        Student student = getStudentById(studentId);
        if (student != null) {
            if (classes.containsKey(className) && classes.get(className).size() < 40) {
                student.assignedClass = className;
                classes.get(className).add(student);
                return true;
            } else {
                System.out.println("Class is full or does not exist.");
                return false;
            }
        }
        return false;
    }

    public void showAllStudents() {
        System.out.println("All Students:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void showAllTeachers() {
        System.out.println("All Teachers:");
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    public void showClassDetails(String className) {
        if (classes.containsKey(className)) {
            System.out.println("Class: " + className);
            for (Student student : classes.get(className)) {
                System.out.println(student);
            }
        } else {
            System.out.println("Class does not exist.");
        }
    }
}

class SchoolManagementSystem {
    public static void main(String[] args) {
        School school = new School();

        // Creating Students
        Student student1 = new Student("Alice", 16, "S1001", 80, "Class A");
        Student student2 = new Student("Bob", 17, "S1002", 70, "Class A");
        Student student3 = new Student("Charlie", 16, "S1003", 90, "Class B");

        // Adding Students to School
        school.addStudent(student1);
        school.addStudent(student2);
        school.addStudent(student3);

        // Creating Teachers
        Teacher teacher1 = new Teacher("Mr. Smith", 35, "T1001");
        teacher1.addSubject("Math");
        teacher1.assignClass("Class A");

        Teacher teacher2 = new Teacher("Ms. Johnson", 40, "T1002");
        teacher2.addSubject("English");
        teacher2.assignClass("Class B");

        // Adding Teachers to School
        school.addTeacher(teacher1);
        school.addTeacher(teacher2);

        // Adding grades to students
        student1.addGrade("A");
        student1.addGrade("B");
        student2.addGrade("C");
        student2.addGrade("B");
        student3.addGrade("A");
        student3.addGrade("A");

        // Generate Progress Report for a student
        school.generateStudentReport("S1001");
        school.generateStudentReport("S1002");

        // Show all students and teachers
        school.showAllStudents();
        school.showAllTeachers();

        // Show class details
        school.showClassDetails("Class A");

        // Assign new student to a class
        school.addStudentToClass("S1002", "Class B");

        // Show class details after adding student to class
        school.showClassDetails("Class B");
    }
}
