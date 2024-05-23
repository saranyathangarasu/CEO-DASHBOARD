package application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByCategory(String category);
}
