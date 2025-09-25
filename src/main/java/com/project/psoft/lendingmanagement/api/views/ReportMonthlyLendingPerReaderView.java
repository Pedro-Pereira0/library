package com.project.psoft.lendingmanagement.api.views;

import com.project.psoft.lendingmanagement.model.metrics.ReportMonthlyLending;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Schema(description = "Monthly Lendings Report")
@NoArgsConstructor
@AllArgsConstructor
public class ReportMonthlyLendingPerReaderView {

    private String readerNumber;//2024/1
    private List<ReportMonthlyLending> reports;

}
