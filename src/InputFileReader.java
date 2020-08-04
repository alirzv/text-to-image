import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class InputFileReader {


    public List<List<String>> readExcelFiles(Path path) throws FileNotFoundException, IOException {

        List<List<String>> fileContentList = new ArrayList<>();
        FileInputStream fis = null;
        List<XSSFRow> rows = new ArrayList<>();

        fis = new FileInputStream(new File(path.toString()));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        spreadsheet.forEach((sheet) -> rows.add((XSSFRow) sheet));
        rows.forEach(row -> fileContentList.add(readEachRow(row)));
        fis.close();

        /*Iterator < Row >  rowIterator = spreadsheet.iterator();
        while (rowIterator.hasNext()) {
            row = (XSSFRow) rowIterator.next();
            readEachRow();
        }*/
        return fileContentList;
    }

    private List<String> readEachRow(XSSFRow row) {
        Iterator<Cell> cellIterator = row.cellIterator();
        List<String> cellList = new ArrayList<>();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();

            switch (cell.getCellType()) {
                case NUMERIC:
                    cellList.add(Double.toString(cell.getNumericCellValue()));
                    System.out.print(cell.getNumericCellValue() + " \t\t ");
                    break;
                case STRING:
                    cellList.add(cell.getStringCellValue());
                    System.out.print(
                            cell.getStringCellValue() + " \t\t ");
                    break;
                case BLANK:
                    cellList.add(cell.getRichStringCellValue().toString());
                    System.out.print(
                            cell.getRichStringCellValue() + " \t\t ");
                    break;
            }
        }
        System.out.println();
        return cellList;
    }


    public List<List<String>> readCsvFile(Path path) throws IOException {
        List<List<String>> fileContentList = new ArrayList<>();
        List<String> lineList = new ArrayList<>();
        try {
            BufferedReader br = Files.newBufferedReader(path);
            System.out.println(br.ready());
            String line = "";
            while ((line = br.readLine()) != null) {
                lineList = Arrays.asList(line.split(","));
                fileContentList.add(lineList);
                System.out.println(line);
            }
        } catch (InvalidPathException e) {
            System.out.println("Exception!!");
            e.printStackTrace();
        }
        return fileContentList;
    }
}
