package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.unigap.api.dto.output.StatisticTheSystemDTO;
import vn.unigap.api.exceptions.BadRequestException;
import vn.unigap.api.model.ResponseWrapper;
import vn.unigap.api.service.statistic.StatisticService;

@RestController
@RequestMapping("/statistic")
@Tag(name = "Statistic API")
@AllArgsConstructor
public class StatisticController {
  private static final Logger logger = LoggerFactory.getLogger(StatisticController.class);
  private final StatisticService statisticService;

  @GetMapping("/theSystemByDay")
  public ResponseWrapper<StatisticTheSystemDTO> getStatisticTheSystemByDay(
      @RequestParam @NotNull String fromDate, @RequestParam @NotNull String toDate) {
    try {
      logger.info("Received request GET /theSystemByDay?fromDate={}, toDate={}", fromDate, toDate);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate parsedFromDate = LocalDate.parse(fromDate, formatter);
      LocalDate parsedToDate = LocalDate.parse(toDate, formatter);

      // Ensure fromDate is not after toDate
      if (parsedFromDate.isAfter(parsedToDate)) {
        throw new BadRequestException("fromDate should not be after toDate");
      }
      StatisticTheSystemDTO result =
          statisticService.generalIndexOfTheSystemByDay(fromDate, toDate);
      logger.info(
          "Returning response GET /theSystemByDay?fromDate={}, toDate={} {}",
          fromDate,
          toDate,
          result);
      return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Success", result);
    } catch (DateTimeParseException e) {
      throw new BadRequestException("Invalid date format, please use yyyy-MM-dd");
    }
  }
}
