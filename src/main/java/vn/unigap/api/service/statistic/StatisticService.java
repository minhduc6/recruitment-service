package vn.unigap.api.service.statistic;

import vn.unigap.api.dto.output.StatisticTheSystemDTO;

public interface StatisticService {
    StatisticTheSystemDTO generalIndexOfTheSystemByDay(String fromDate, String toDate);
}
