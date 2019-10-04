package models;

import datasource.Database;
import services.StudentServices;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {

    private String firstName;
    private String middleName;
    private String lastName;
    private String LRN;
    private Integer age;
    private String section;
    private String gradeLevel;
    private String email;
    private String password;
    private String birthday;
    private String username;

    private Database source = new Database();
    private StudentServices studentServices = new StudentServices();

    /*
    Constructor
     */


    public Student(String firstName, String middleName, String lastName, String LRN, String birthday, String email){
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.LRN = LRN;
        this.birthday = birthday;
        this.email = email;
    }

    public Student(){
        System.out.println();
    }


    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName){
        this.middleName = middleName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setLRN(String LRN) {
        this.LRN = LRN;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getMiddleName(){
        return this.middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getLRN(){
        return this.LRN;
    }


    public void setPassword(String inputPassword) {

        this.password = password;
    }



    //CREATE
    public boolean addStudentToDB(){
        try {
            if(source.addStudentToDB(
                    this.firstName,
                    this.lastName,
                    this.LRN,
                    this.birthday,
                    this.section,
                    this.gradeLevel,
                    this.username,
                    this.email,
                    this.password
            )) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Adding student to database");
        }
        return false;
    }

    //RETRIEVE

    public HashMap<String,String> retrieveStudent(){
        return studentServices.GetStudent(this.LRN);
    }

    public ArrayList<ArrayList<String>> retrieveAllStudents(){
        return source.retrieveAllStudents();
    }

    public String getLRNDB(){
        return source.getLRN(
                this.firstName,
                this.middleName,
                this.lastName
        );
    }

    //get count of student per gradelevel. GRADE LEVEL is input
    public int getCountOfStudentPerGradeLevel(){
        return source.numOfStudentPerGradeLevel(this.gradeLevel);
    }

    public int getCountOfStudents(){
        return source.getCountOfStudents();
    }

}
