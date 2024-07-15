package vn.unigap.api.service.statistic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.output.StatisticByDay;
import vn.unigap.api.dto.output.StatisticTheSystemDTO;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public StatisticTheSystemDTO generalIndexOfTheSystemByDay(String fromDate, String toDate) {
    String sql =
        "SELECT formatted_date, SUM(count_employer) AS total_count_employer, SUM(count_job) AS total_count_job, SUM(count_seeker) AS total_count_seeker, SUM(count_resume) AS total_count_resume "
            + "FROM ( "
            + "    SELECT DATE_FORMAT(created_at, '%Y-%m-%d') AS formatted_date, COUNT(*) AS count_employer, 0 AS count_job, 0 AS count_seeker, 0 AS count_resume "
            + "    FROM employer "
            + "    GROUP BY formatted_date "
            + "    UNION ALL "
            + "    SELECT DATE_FORMAT(created_at, '%Y-%m-%d') AS formatted_date, 0 AS count_employer, COUNT(*) AS count_job, 0 AS count_seeker, 0 AS count_resume "
            + "    FROM jobs "
            + "    GROUP BY formatted_date "
            + "    UNION ALL "
            + "    SELECT DATE_FORMAT(created_at, '%Y-%m-%d') AS formatted_date, 0 AS count_employer, 0 AS count_job, COUNT(*) AS count_seeker, 0 AS count_resume "
            + "    FROM seeker "
            + "    GROUP BY formatted_date "
            + "    UNION ALL "
            + "    SELECT DATE_FORMAT(created_at, '%Y-%m-%d') AS formatted_date, 0 AS count_employer, 0 AS count_job, 0 AS count_seeker, COUNT(*) AS count_resume "
            + "    FROM resume "
            + "    GROUP BY formatted_date "
            + ") AS combined_results "
            + "WHERE formatted_date >= :fromDate AND formatted_date <= :toDate "
            + "GROUP BY formatted_date";

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("fromDate", fromDate);
    paramMap.put("toDate", toDate);

    long totalCountEmployerSystem = 0;
    long totalCountJobSystem = 0;
    long totalCountSeekerSystem = 0;
    long totalCountResumeSystem = 0;

    // Execute the query
    List<Map<String, Object>> results = namedParameterJdbcTemplate.queryForList(sql, paramMap);

    List<StatisticByDay> chart = new ArrayList<>();

    // Process the results
    for (Map<String, Object> row : results) {
      String formattedDate = (String) row.get("formatted_date");
      BigDecimal totalCountEmployerBigDecimal = (BigDecimal) row.get("total_count_employer");
      BigDecimal totalCountJobBigDecimal = (BigDecimal) row.get("total_count_job");
      BigDecimal totalCountSeekerBigDecimal = (BigDecimal) row.get("total_count_seeker");
      BigDecimal totalCountResumeBigDecimal = (BigDecimal) row.get("total_count_resume");

      long totalCountEmployer = totalCountEmployerBigDecimal.longValue();
      long totalCountJob = totalCountJobBigDecimal.longValue();
      long totalCountSeeker = totalCountSeekerBigDecimal.longValue();
      long totalCountResume = totalCountResumeBigDecimal.longValue();

      totalCountEmployerSystem += totalCountEmployer;
      totalCountJobSystem += totalCountJob;
      totalCountSeekerSystem += totalCountSeeker;
      totalCountResumeSystem += totalCountResume;

      chart.add(
          new StatisticByDay(
              LocalDate.parse(formattedDate),
              totalCountEmployer,
              totalCountJob,
              totalCountSeeker,
              totalCountResume));
    }
    // Sort day
    chart.sort(Comparator.comparing(StatisticByDay::getDay));
    return new StatisticTheSystemDTO(
        totalCountEmployerSystem,
        totalCountJobSystem,
        totalCountSeekerSystem,
        totalCountResumeSystem,
        chart);
  }
}
