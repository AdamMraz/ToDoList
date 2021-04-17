package main.com.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
//@Builder
public class CaseDTO {
    private String id;
    private String value;
    private String deadLine;

//    public CaseDTO(String value, String deadLine) {
//        this.value = value;
//        this.deadLine = deadLine;
//    }

    //    public CaseDTO(String value, String deadLine) {
//        this.value = value;
//        this.deadLine = deadLine;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
//
//    public String getDeadLine() {
//        return deadLine;
//    }
//
//    public void setDeadLine(String deadLine) {
//        this.deadLine = deadLine;
//    }
}
