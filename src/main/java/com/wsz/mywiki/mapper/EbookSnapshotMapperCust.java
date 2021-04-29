package com.wsz.mywiki.mapper;

import com.wsz.mywiki.resp.StatisticResp;

import java.util.List;

public interface EbookSnapshotMapperCust {
    public void genSnapshot();

    List<StatisticResp> getStatistic();

    List<StatisticResp> get30Statistic();
}
