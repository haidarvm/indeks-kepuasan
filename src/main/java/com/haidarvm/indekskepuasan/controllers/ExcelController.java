package com.haidarvm.indekskepuasan.controllers;

import com.haidarvm.indekskepuasan.model.Score;
import com.haidarvm.indekskepuasan.repositories.DepartmentRepository;
import com.haidarvm.indekskepuasan.repositories.ScoreRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/excel")
public class ExcelController {


    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    private final ScoreRepository scoreRepository;
    private final DepartmentRepository departmentRepository;
    private Workbook workbook = new XSSFWorkbook();


    public ExcelController(ScoreRepository scoreRepository, DepartmentRepository departmentRepository) {
        this.scoreRepository = scoreRepository;
        this.departmentRepository = departmentRepository;
    }

    @RequestMapping("")
    public String excelIndex() {
        logger.debug("Created file xlx {}", "mana ini excel nya");
        return "report/filter";
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/report_by/{departmentId}/{startDate}/{endDate}")
    public HttpEntity<byte[]> reportByDate(@PathVariable Long departmentId, @PathVariable String startDate, @PathVariable String endDate, HttpServletResponse response) throws IOException {

        logger.debug("Creating file xlx ");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook = createWorkbook(departmentId, startDate, endDate);
        workbook.write(out);
        String deptName = "All";
        if (departmentId != 0L) {
            deptName = departmentRepository.findById(departmentId).get().getName();
        }
        logger.debug("Dept Name adalah {}", deptName);
        byte[] documentContent = out.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"Laporan_" + deptName + "_Pertanggal_" + startDate + "_" + endDate + ".xlsx\"");
        headers.setContentLength(documentContent.length);
        return new ResponseEntity<byte[]>(documentContent, headers, HttpStatus.OK);
    }

    private XSSFWorkbook createWorkbook(Long departmentId, String startDate, String endDate) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();
        XSSFSheet sheet = wb.createSheet("Laporan Per Tanggal");
        List<Score> scores = null;
        if (departmentId == 0L) {
            scores = scoreRepository.generalReportByDate(startDate, endDate);
        } else {
            scores = scoreRepository.generalReportByDepartmentIdAndByDate(departmentId, startDate, endDate);
        }
        writeHeaders(wb, sheet);

        int rowIdx = 1;
        int totalCount = 0;
        int totalSatisfy = 0;
        int totalDissatisfy = 0;
        for (Score score : scores) {
            XSSFRow row = sheet.createRow(rowIdx++);
            int totalSatisfyScore = score.getSatisfy();
            int totalDiSatisfyScore = score.getDissatisfy();
            int totalScore = score.getTotal();
            row.createCell(0).setCellValue(String.valueOf(score.getDepartment().getName()));
            row.createCell(1).setCellValue(String.valueOf(score.getCreated().getDayOfWeek()));
            row.createCell(2).setCellValue(String.valueOf(score.getCreated()));
            row.createCell(3).setCellValue(totalSatisfyScore);
            row.createCell(4).setCellValue(totalDiSatisfyScore);
            row.createCell(5).setCellValue(totalScore);
            totalCount += totalScore;
            totalSatisfy += totalSatisfyScore;
            totalDissatisfy += totalDiSatisfyScore;
        }
        logger.debug("last row {}", rowIdx);
        String strFormula = "SUM(D2:D" + rowIdx + ")";
        XSSFRow totalRow = sheet.createRow(rowIdx + 1);
        totalRow.createCell(0).setCellValue("Jumlah Total");
        totalRow.createCell(3).setCellValue(totalSatisfy);
        totalRow.createCell(4).setCellValue(totalDissatisfy);
        totalRow.createCell(5).setCellValue(totalCount);

        //Percentage
        XSSFRow totalPercentageRow = sheet.createRow(rowIdx + 2);
        totalPercentageRow.createCell(0).setCellValue("Percentage %");
        totalPercentageRow.createCell(3).setCellValue(String.valueOf(calcPercentage(totalSatisfy, totalCount)));
        totalPercentageRow.createCell(4).setCellValue(String.valueOf(calcPercentage(totalDissatisfy, totalCount)));
        totalPercentageRow.createCell(5).setCellValue(String.valueOf(calcPercentage(totalCount, totalCount)));
        return wb;
    }

    private void writeHeaders(XSSFWorkbook workbook, XSSFSheet sheet) {
        String[] headerCol = {"Layanan", "Hari", "Tanggal", "Puas", "Tidak Puas", "Total"};
        XSSFRow headerRow = sheet.createRow(0);
        // Header
        for (int col = 0; col < headerCol.length; col++) {
            XSSFCell headerCell = headerRow.createCell(col);
            headerCell.setCellValue(headerCol[col]);
            headerCell.setCellStyle(createHeaderStyle(workbook));
        }

    }

    private XSSFCellStyle createHeaderStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());

        style.setRightBorderColor(IndexedColors.BLUE.getIndex());
        style.setBottomBorderColor(IndexedColors.BLUE.getIndex());
        style.setLeftBorderColor(IndexedColors.BLUE.getIndex());
        style.setTopBorderColor(IndexedColors.BLUE.getIndex());

        Font font = workbook.createFont();
        font.setFontName("Helvetica");
        font.setColor(IndexedColors.BLUE.getIndex());
        style.setFont(font);
        return style;
    }

    private String calcPercentage(Integer partialValue, Integer totalValue) {
        float per = (float) (100 * partialValue) / totalValue;
        String perString = String.format ("%.2f", per);
        logger.debug("Percentage get {} ", perString);
        return perString;
    }


}
