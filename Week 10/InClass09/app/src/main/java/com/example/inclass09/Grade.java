package com.example.inclass09;

public class Grade {
    long id;
            String courseNumber;
            String courseName;
            String courseGrade;
            int courseCreditHours;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }

    public int getCourseCreditHours() {
        return courseCreditHours;
    }

    public void setCourseCreditHours(int courseCreditHours) {
        this.courseCreditHours = courseCreditHours;
    }

    public Grade() {
    }

    public Grade( String courseNumber, String courseName, String courseGrade, int courseCreditHours) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.courseGrade = courseGrade;
        this.courseCreditHours = courseCreditHours;
    }
    public Grade(long id, String courseNumber, String courseName, String courseGrade, int courseCreditHours) {
        this.id = id;
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.courseGrade = courseGrade;
        this.courseCreditHours = courseCreditHours;
    }


    public long getNumericGrade(){

        long numericNumber ;

        switch (this.courseGrade){
            case "A":
                numericNumber =  4;
                break;
            case "B":
                numericNumber = 3;
            break;
            case "C":
                numericNumber = 2;
            break;
            case "D":
                numericNumber = 1;
                break;
            case "F":
                numericNumber = 0;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.courseGrade);
        }

        return numericNumber;
    };


    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", courseNumber='" + courseNumber + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseGrade='" + courseGrade + '\'' +
                ", courseCreditHours=" + courseCreditHours +
                '}';
    }
}
