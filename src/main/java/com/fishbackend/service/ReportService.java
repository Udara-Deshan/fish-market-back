package com.fishbackend.service;

import com.fishbackend.dto.ReportDTO;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 6/4/2022
 **/
public interface ReportService {
    public List<ReportDTO> getMoreThan3Days();
    public List<ReportDTO> getMoreThan7Days();
    public List<ReportDTO> getDailyStock();
    public List<ReportDTO> getDailyPaidStock();
}
