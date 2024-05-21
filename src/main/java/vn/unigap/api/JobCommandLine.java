package vn.unigap.api;

import org.hibernate.Hibernate;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
//import vn.unigap.api.entity.Job;
//import vn.unigap.api.entity.JobField;
//import vn.unigap.api.entity.Province;
//import vn.unigap.api.entity.Resume;
//import vn.unigap.api.repository.JobFieldRepository;
//import vn.unigap.api.repository.JobRepository;
//import vn.unigap.api.repository.ProvinceRepository;
//import vn.unigap.api.repository.ResumeRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//@AllArgsConstructor
//public class JobCommandLine implements CommandLineRunner {
//
//    private final JobRepository jobRepository;
//    private final JobFieldRepository jobFieldRepository;
//    private final ProvinceRepository provinceRepository;
//    private final ResumeRepository resumeRepository;
//
//    private List<Integer> transferData(String data) {
//        System.out.println("Before data: " + data);
//        data = data.substring(1, data.length() - 1);
//        System.out.println("data: " + data);
//        String[] strArray = data.split("-"); // Split the string into substrings
//        for (String s : strArray) {
//            System.out.println(s);
//        }
//
//        List<Integer> intList = new ArrayList<>();
//        for (String s : strArray) {
//            intList.add(Integer.parseInt(s)); // Convert each substring to an integer
//        }
//
//
//        return intList;
//    }
//
//    @Override
//    @Transactional
//    public void run(String... args)  {
//        List<Job> jobs = jobRepository.findAll();
//
//        for (Job jobEntity : jobs) {
//            if (jobEntity.getDataFields() != null && jobEntity.getDataProvince() != null){
//                List<Integer> dataFields = transferData(jobEntity.getDataFields());
//                List<Integer> dataProvinces = transferData(jobEntity.getDataProvince());
//                System.out.println("id: " + jobEntity.getId());
//                if (!dataFields.isEmpty()) {
//                    System.out.println("Data Fields: " + dataFields);
//                    for (Integer dataField : dataFields) {
//                        Optional<JobField> jobFieldOptional = jobFieldRepository.findById(dataField);
//                        jobFieldOptional.ifPresent(jobEntity::addJobField);
//                    }
//                }
//
//                if (!dataProvinces.isEmpty()) {
//                    System.out.println("Data Provinces: " + dataProvinces);
//                    for (Integer dataProvince : dataProvinces) {
//                        Optional<Province> provinceOptional = provinceRepository.findById(dataProvince);
//                        provinceOptional.ifPresent(jobEntity::addProvince);
//                    }
//                }
//
//                jobRepository.save(jobEntity);
//            }
//        }
//
//        List<Resume> resumes = resumeRepository.findAll();
//
//        for (Resume resumeEntity : resumes) {
//            if (resumeEntity.getDataFields() != null && resumeEntity.getDataProvince() != null){
//                List<Integer> dataFields = transferData(resumeEntity.getDataFields());
//                List<Integer> dataProvinces = transferData(resumeEntity.getDataProvince());
//                if (!dataFields.isEmpty()) {
//                    System.out.println("Data Fields: " + dataFields);
//                    for (Integer dataField : dataFields) {
//                        Optional<JobField> jobFieldOptional = jobFieldRepository.findById(dataField);
//                        jobFieldOptional.ifPresent(resumeEntity::addJobField);
//                    }
//                }
//
//                if (!dataProvinces.isEmpty()) {
//                    System.out.println("Data Provinces: " + dataProvinces);
//                    for (Integer dataProvince : dataProvinces) {
//                        Optional<Province> provinceOptional = provinceRepository.findById(dataProvince);
//                        provinceOptional.ifPresent(resumeEntity::addProvince);
//                    }
//                }
//            }
//            resumeRepository.save(resumeEntity);
//        }
//    }
//}
