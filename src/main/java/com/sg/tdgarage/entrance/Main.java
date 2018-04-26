package com.sg.tdgarage.entrance;

import com.sg.tdgarage.configure.ParkingLotConfiguration;
import com.sg.tdgarage.core.Allocator;
import com.sg.tdgarage.configure.Constant;
import com.sg.tdgarage.event.EventDriver;
import com.sg.tdgarage.event.EventRecorder;
import com.sg.tdgarage.structure.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    private static List<BusLine> lineList;
    private static List<Bus> busList;

    public static void main(String[] args) throws IOException {
        ParkingLot lot = new ParkingLot(new ParkingLotConfiguration());
        loadBusLeaveSeq();
        Allocator allocator = new Allocator(lot, new LinkedBlockingQueue<>(busList), lineList);
        allocator.prepare();
        processEvent();
        EventRecorder.print();
    }

    private static void processEvent() {
        while(EventDriver.hasNext()){
            EventDriver.nextEvent().action();
        }
    }

    private static void loadBusLeaveSeq() throws IOException {
        busList = new LinkedList<>();
        XSSFWorkbook xmlFile = openExcelFile(Constant.TIME_SEQ_FILE_NAME);
        int sheetNum = xmlFile.getNumberOfSheets();
        double[] densities = new double[sheetNum];
        lineList = new ArrayList<>(sheetNum);
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < sheetNum; ++i) {
            XSSFSheet sheet = xmlFile.getSheetAt(i);
            BusLine busLine = new BusLine(sheet.getSheetName());
            lineList.add(busLine);
            TimeSpot firstDeparture = null, time = null;
            int count = 0;
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                calendar.setTime(cell.getDateCellValue());
                time = new TimeSpot(calendar);
                busList.add(new Bus(busLine, time));
                if (count == 0) {
                    firstDeparture = time;
                }
                ++count;
            }
            int interval = time.interval(firstDeparture);
            densities[i] = (double) count / interval;
        }
        double sumDensity = 0;
        for (double density : densities) {
            sumDensity += density;
        }

        for (int i = 0; i < sheetNum; ++i) {
            BusLine line = lineList.get(i);
            line.setPriority(densities[i] / sumDensity);
        }
        lineList.sort(BusLine.comp);
        busList.sort(Bus.comp);
    }

    private static XSSFWorkbook openExcelFile(String timeSeqFileName) throws IOException {
        System.out.println(Main.class.getClassLoader().getResource(timeSeqFileName));
        InputStream in = Main.class.getClassLoader().getResourceAsStream(timeSeqFileName);
        return new XSSFWorkbook(in);
    }
}
