package vn.unigap.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.unigap.api.entity.Province;
import vn.unigap.api.model.ResponseWrapper;
import vn.unigap.api.service.ProvinceService;

import java.util.List;

@RestController
@RequestMapping("/provinces")
public class ProvinceController {
    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping
    public ResponseWrapper<List<Province>> getAllProvince() {
        List<Province> data = provinceService.getAllProvince();
        return new ResponseWrapper<>(0, HttpStatus.CREATED.value(), "Success", data);
    }

}
