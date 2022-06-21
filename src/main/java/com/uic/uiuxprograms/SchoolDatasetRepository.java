package com.uic.uiuxprograms;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
//this contains all the queries that will be used on the database and it ties it together with a method call
//note: @param lets spring know to take that variable and tie it to the parameter in the query
public interface SchoolDatasetRepository extends CrudRepository<SchoolDataset, Integer> {

    //this query is going to display all schools
    @Query("SELECT s FROM SchoolDataset s")
    List<SchoolDataset> getAllSchools();


    //this query is going to get schools by GRE
    @Query("SELECT s FROM SchoolDataset s WHERE s.gre=:gre")
    List<SchoolDataset> getSchoolsByGRE(@Param("gre") String gre);
    // http://localhost:8080/getByGRE?required=Y
    // http://localhost:8081/getByGRE?required=N


    //this query is going to get schools by TOEFL
    //you add lower and upper because of the number ranges
    @Query("SELECT s FROM SchoolDataset s WHERE s.toefl BETWEEN :lower AND :upper ")
    List<SchoolDataset> getSchoolsByToefl(@Param("lower") int lower, @Param("upper") int upper);

    //this query is going to get schools by Program
    //you add <= because of the number
    @Query("SELECT s FROM SchoolDataset s WHERE LOWER(s.program) like LOWER(CONCAT('%',:program,'%'))")
    List<SchoolDataset> getSchoolsByProgram(@Param("program") String program);

    //this query is going to get schools by Portfolio
    @Query("SELECT s FROM SchoolDataset s WHERE s.portfolio=:portfolio")
    List<SchoolDataset> getSchoolsByPortfolio(@Param("portfolio") String portfolio);

    //this query is going to get schools by location
    @Query("SELECT s FROM SchoolDataset s WHERE LOWER(s.location) like LOWER(CONCAT('%',:location,'%'))")
    List<SchoolDataset> getSchoolsByLocation(@Param("location") String location);

    //this query is going to get schools by deadline
    @Query("SELECT s FROM SchoolDataset s WHERE LOWER(s.deadline) like LOWER(CONCAT('%',:deadline,'%'))")
    List<SchoolDataset> getSchoolsByDeadline(@Param("deadline") String deadline);


}