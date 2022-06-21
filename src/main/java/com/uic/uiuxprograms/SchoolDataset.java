package com.uic.uiuxprograms;

import javax.persistence.*;
import java.math.BigDecimal;

//entity is an annotation to let spring know its a model of a table
@Entity
//the table annotation lets spring know to map it to a specific table thats been set up
@Table(name = "uiuxprogram")
public class SchoolDataset {

//id lets spring know that the variable is used as an identifier which assists in db operations, for instance when when find by id it references record id
    @Id
//when you save it if theres no record id then it generates a new one
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //column lets spring know how to connect to variable ex. record id, schoolid , etc.
    @Column(name = "record_id")
    private Integer recordId;
    @Column(name = "schoolid")
    private int schoolId;
    @Column(name = "ranking")
    private int ranking;
    @Column(name = "school")
    private String school;
    @Column(name = "program")
    private String program;
    @Column(name = "lengthofprogram")
    private String lengthOfProgram;
    @Column(name = "totaltuition")
    private BigDecimal totalTuition;
    @Column(name = "location")
    private String location;
    @Column(name = "toefl")
    private int toefl;
    @Column(name = "gre")
    private String gre;
    @Column(name = "portfolio")
    private String portfolio;
    @Column(name = "deadline")
    private String deadline;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getLengthOfProgram() {
        return lengthOfProgram;
    }

    public void setLengthOfProgram(String lengthOfProgram) {
        this.lengthOfProgram = lengthOfProgram;
    }

    public BigDecimal getTotalTuition() {
        return totalTuition;
    }

    public void setTotalTuition(BigDecimal totalTuition) {
        this.totalTuition = totalTuition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getToefl() {
        return toefl;
    }

    public void setToefl(int toefl) {
        this.toefl = toefl;
    }

    public String getGre() {
        return gre;
    }

    public void setGre(String gre) {
        this.gre = gre;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}