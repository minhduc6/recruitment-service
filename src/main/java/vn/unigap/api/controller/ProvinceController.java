package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.unigap.api.entity.Province;
import vn.unigap.api.model.ResponseWrapper;
import vn.unigap.api.service.province.ProvinceService;
import vn.unigap.api.service.statistic.StatisticService;

import java.util.List;

@RestController
@RequestMapping("/provinces")
@Tag(name = "Province API")
public class ProvinceController {
    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService, StatisticService statisticService) {
        this.provinceService = provinceService;
    }

    @GetMapping
    public ResponseWrapper<List<Province>> getAllProvince() {
        List<Province> data = provinceService.getAllProvince();
        return new ResponseWrapper<>(0, HttpStatus.CREATED.value(), "Success", data);
    }
}
