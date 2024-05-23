package application.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import application.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByUsernameAndEmail(String username, String email);
    @Override 
    <S extends User> S save(S entity);
    @Query("SELECT COUNT(u) FROM User u WHERE u.department = 'Student'")
    int countStudents();
    @Query("SELECT COUNT(u) FROM User u")
    int countAllStudents();
    @Query("SELECT COUNT(u) FROM User u WHERE u.department = ?1")
    int countByDepartment(String department);
    @Query("SELECT COUNT(u) FROM User u WHERE u.department = :department")
    int countWorkersByDepartment(@Param("department") String department);  
    @Query("SELECT COUNT(u) FROM User u WHERE u.department = 'Financial Metrics'")
    int countFinance();
    @Query("SELECT COUNT(u) FROM User u WHERE u.department = 'Sales and Marketing'")
    int countSalesAndMarketing();
    @Query("SELECT COUNT(u) FROM User u WHERE u.department = 'Operational Metrics'")
    int countOperational();
}