package diningsim;


/**
 * Student
 * Represents a student in the dining hall.
 * Each student has a unique ID and a name.
 * The ID must be STU- followed by exactly 8 digits,
 * for example STU-12345678.
 * The window adds the STU- part.
 */

import java.util.Map;

public class Student {

    private String studentId;
    private String name;

    public Student(String studentId, String name){
        if( studentId == null){
            throw new IllegalArgumentException("Student ID is required");
        }
        if(!studentId.startsWith("STU-")){
            throw new IllegalArgumentException("Student Id must start with STU-");
        }

        String digits = studentId.substring(4);
        if(digits.length() != 8 || !digits.matches("[0-9]+")){
            throw new IllegalArgumentException("Student Id must be STU- and followed by 8 digits (0-9)");
        }

        this.studentId =studentId;
        this.name = name;
    }


    //a student can place an order
    public Order placeOrder(Map<MenuItem, Integer> items){
        String orderId = "ORD-" + studentId;
        return new Order(orderId,this, items);
    }


    public String getStudentId(){
        return studentId;
    }

    public String getName(){
        return name;
    }


}
