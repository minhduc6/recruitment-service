package vn.unigap.api.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
  @Query("SELECT j FROM Job j LEFT JOIN FETCH j.employer WHERE j.id = :id")
  Optional<Job> findByIdCheck(@Param("id") Integer id);

  Page<Job> findByEmployerId(Integer employerId, Pageable pageable);
}
