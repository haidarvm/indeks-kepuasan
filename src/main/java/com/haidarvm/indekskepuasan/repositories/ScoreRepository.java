package com.haidarvm.indekskepuasan.repositories;

import com.haidarvm.indekskepuasan.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "score", path = "api/score")
public interface ScoreRepository extends JpaRepository<Score, Long> {


    List<Score> findByScore(Integer score);

    List<Score> findByScoreAndAndDepartment_Id(Integer score, Long department_id );

    List<Score> findByScoreAndAndDepartment_IdAndCreatedBetween(Integer score, Long department_id , LocalDateTime startCreated, LocalDateTime endCreated);

    Long countByScore(Integer score);

    Long countByScoreAndDepartment_Id(Integer score, Long department_id);

    Long countByScoreAndDepartment_IdAndCreatedBetween(Integer score, Long department_id, LocalDateTime startCreated, LocalDateTime endCreated);

    Long countAllByCreatedBetween(LocalDateTime startCreated, LocalDateTime endCreated);

    Long countByScoreAndCreatedBetween(Integer score, LocalDateTime startCreated, LocalDateTime endCreated);


    @Query(value = "SELECT MAX(score.id) as id,  SUM(score) as score, count(department_id) as \"total\",\n" +
            "SUM(CASE WHEN score = 1 THEN 1 ELSE 0 END) as \"satisfy\" ,\n" +
            "SUM(CASE WHEN score = -1 THEN 1 ELSE 0 END) as \"dissatisfy\" ,\n" +
            "MAX(created) as latest, " +
            "PARSEDATETIME(FORMATDATETIME(created, 'yyyy-MM-dd'), 'yyyy-MM-dd') as created, MAX(score.device_id) as \"device_id\", department_id, department.name FROM Score INNER JOIN department ON department.id = score.department_id WHERE created > CURRENT_DATE() GROUP BY department_id, created ORDER BY created DESC", nativeQuery = true)
    List<Score> generalReport();

    @Query(value = "SELECT MAX(score.id) as id,  SUM(score) as score, count(department_id) as total,\n" +
            "SUM(CASE WHEN score = 1 THEN 1 ELSE 0 END) as satisfy ,\n" +
            "SUM(CASE WHEN score = -1 THEN 1 ELSE 0 END) as dissatisfy,\n" +
            "MAX(created) as latest, " +
            "PARSEDATETIME(FORMATDATETIME(created, 'yyyy-MM-dd'), 'yyyy-MM-dd') as created, MAX(score.device_id) as device_id, department_id, department.name FROM Score INNER JOIN department ON department.id = score.department_id  WHERE PARSEDATETIME(FORMATDATETIME(created, 'yyyy-MM-dd'), 'yyyy-MM-dd') BETWEEN  ?1 AND ?2  GROUP BY department_id, created ORDER BY created DESC", nativeQuery = true)
    List<Score> generalReportByDate(String startDate, String endDate);

    @Query(value = "SELECT MAX(score.id) as id,  SUM(score) as score, count(department_id) as total,\n" +
            "SUM(CASE WHEN score = 1 THEN 1 ELSE 0 END) as satisfy ,\n" +
            "SUM(CASE WHEN score = -1 THEN 1 ELSE 0 END) as dissatisfy,\n" +
            "MAX(created) as latest, " +
            "PARSEDATETIME(FORMATDATETIME(created, 'yyyy-MM-dd'), 'yyyy-MM-dd') as created, MAX(score.device_id) as device_id, department_id, department.name FROM Score INNER JOIN department ON department.id = score.department_id  WHERE department_id = ?1 AND PARSEDATETIME(FORMATDATETIME(created, 'yyyy-MM-dd'), 'yyyy-MM-dd') BETWEEN  ?2 AND ?3 GROUP BY created ORDER BY created DESC", nativeQuery = true)
    List<Score> generalReportByDepartmentIdAndByDate(Long departmentId, String startDate, String endDate);


    @Query(value = "SELECT MAX(score.id) as id,  SUM(score) as score, count(department_id) as total,\n" +
            "SUM(CASE WHEN score = 1 THEN 1 ELSE 0 END) as satisfy ,\n" +
            "SUM(CASE WHEN score = -1 THEN 1 ELSE 0 END) as dissatisfy,\n" +
            "MAX(created) as latest, " +
            "PARSEDATETIME(FORMATDATETIME(created, 'yyyy-MM-dd'), 'yyyy-MM-dd') as created, MAX(score.device_id) as device_id, department_id, department.name FROM Score INNER JOIN department ON department.id = score.department_id  WHERE department_id = ?1 AND MONTH(created) = MONTH(current_date) AND YEAR(created) = YEAR(current_date) GROUP BY department_id, created ORDER BY created DESC",
            nativeQuery = true)
    List<Score> generalReportByDepartment(Long department_id);

    @Query(value = "SELECT MAX(score.id) as id,  SUM(score) as score, count(*) as total,\n" +
            "SUM(CASE WHEN score = 1 THEN 1 ELSE 0 END) as satisfy ,\n" +
            "SUM(CASE WHEN score = -1 THEN 1 ELSE 0 END) as dissatisfy,\n" +
            "MAX(created) as latest, " +
            "MAX(score.device_id) as device_id, department_id, MAX(created) as created FROM Score WHERE department_id = ?1 AND MONTH(created) = MONTH(current_date) AND YEAR(created) = YEAR(current_date) ", nativeQuery = true)
    List<Score> countReportByDepartment(Long department_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Score WHERE department_id = ?1 AND FORMATDATETIME(created, 'yyyy-MM-dd') = ?2")
    int deleteByIdAndStringCreated(Long departmentId, String created);

}
