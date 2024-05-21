package vn.unigap.api.service;

import org.springframework.data.domain.Page;
import vn.unigap.api.dto.input.CreateSeekerRequest;
import vn.unigap.api.dto.input.UpdateSeekerRequest;
import vn.unigap.api.dto.output.SeekerDTO;

public interface SeekerService {
    Page<SeekerDTO> getAllSeekerByProvince(int pageNumber, int pageSize, Integer provinceId);
    SeekerDTO findById(Integer id);
    void createSeeker(CreateSeekerRequest createSeekerRequest);
    void updateSeeker(Integer id ,UpdateSeekerRequest updateSeekerRequest);
    void deleteSeeker(Integer id);

}
