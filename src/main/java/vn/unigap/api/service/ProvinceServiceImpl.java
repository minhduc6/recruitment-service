package vn.unigap.api.service;

import org.springframework.stereotype.Service;
import vn.unigap.api.entity.Province;
import vn.unigap.api.repository.ProvinceRepository;

import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceRepository provinceRepository;

    public ProvinceServiceImpl(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Override
    public List<Province> getAllProvince() {
        return provinceRepository.findAll();
    }
}
