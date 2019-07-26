package com.haidarvm.indekskepuasan.controllers;

import com.haidarvm.indekskepuasan.model.Score;
import com.haidarvm.indekskepuasan.repositories.ScoreRepository;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
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
    private Workbook workbook = new XSSFWorkbook();

//    CreationHelper createHelper = workbook.getCreationHelper();

    public ExcelController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
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

        byte[] documentContent = out.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"LaporanDepartmentPertanggal.xlsx\"");
        headers.setContentLength(documentContent.length);
        return new ResponseEntity<byte[]>(documentContent, headers, HttpStatus.OK);
    }

    private XSSFWorkbook createWorkbook(Long departmentId, String startDate, String endDate) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        XSSFSheet sheet = wb.createSheet("Laporan Per Tanggal");

        List<Score> scores = scoreRepository.generalReportByDepartmentIdAndByDate(departmentId, startDate, endDate);

        writeHeaders(wb, sheet);

        int rowIdx = 1;
        for (Score score : scores) {
            XSSFRow row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(String.valueOf(score.getDepartment().getName()));
            row.createCell(1).setCellValue(String.valueOf(score.getCreated().getDayOfWeek()));
            row.createCell(2).setCellValue(String.valueOf(score.getCreated()));
            row.createCell(3).setCellValue(String.valueOf(score.getSatisfy()));
            row.createCell(4).setCellValue(String.valueOf(score.getDissatisfy()));
            row.createCell(5).setCellValue(String.valueOf(score.getTotal()));
        }

        return wb;
    }

    private void writeHeaders(XSSFWorkbook workbook, XSSFSheet sheet) {
        String[] headerCol = {"Layanan", "Hari","Tanggal","Puas","Tidak Puas","Total"};
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


}
