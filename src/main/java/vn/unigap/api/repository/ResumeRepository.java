package vn.unigap.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.Resume;

@Repository
public interface ResumeRepository  extends JpaRepository<Resume, Integer> {
    Page<Resume> findBySeekerId(int seekerId, Pageable pageable);
}
