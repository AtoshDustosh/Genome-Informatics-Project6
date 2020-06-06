package toolkit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exceptions.FormatInvalidException;

public class SamManager {
  private List<String> headers = new ArrayList<>();
  private List<SamRecord> records = new ArrayList<>();

  public SamManager(String filePath) {
    this.loadSamFile(filePath);
  }

  public int headerLineCnt() {
    return this.headers.size();
  }

  public int samRecordCnt() {
    return this.records.size();
  }

  public String getHeader(int index) {
    return this.headers.get(index);
  }

  public SamRecord getRecord(int index) {
    return this.records.get(index);
  }

  public void print() {
    System.out.println(this.toString());
  }

  @Override
  public String toString() {
    String str = "";
    for (int i = 0; i < this.headers.size(); i++) {
      str += this.headers.get(i) + "\n";
    }
    str += "QNAME\tFLAG\tRNAME\tPOS\tMAPQ\tCIGAR\t" +
        "MRNM\tMPOS\tISIZE\tSEQ\tQUAL\t" + "Optional fields\n";
    for (int i = 0; i < this.records.size(); i++) {
      str += this.records.get(i).toString() + "\n";
    }
    return str;
  }

  private void loadSamFile(String filePath) {
    try {
      Scanner scanner = new Scanner(new FileInputStream(filePath));
      String line = "";
      while (scanner.hasNextLine()) {
        line = scanner.nextLine();
        if (line.charAt(0) == '@') {
          this.headers.add(line);
        } else {
          try {
            SamRecord record = new SamRecord(line);
            this.records.add(record);
          } catch (FormatInvalidException e) {
            System.out.println("invalid sam line: " + line);
            e.printStackTrace();
          }
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }
}
