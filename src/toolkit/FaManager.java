package toolkit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FaManager {
  private String header = "";
  private StringBuilder sequence = new StringBuilder();

  public FaManager(String filePath) {
    this.loadFaFile(filePath);
  }

  public String faHeader() {
    return this.header;
  }

  public String totalSequence() {
    return this.sequence.toString();
  }

  public int length() {
    return this.sequence.length();
  }

  public String subSequence(int start, int end) {
    return this.sequence.subSequence(start, end).toString();
  }

  public char charAt(int index) {
    return this.sequence.charAt(index);
  }

  private void loadFaFile(String filePath) {
    try {
      Scanner scanner = new Scanner(new FileInputStream(filePath));
      this.header = scanner.nextLine();
      this.header = this.header.replace(">", "");
      String line = "";
      while (scanner.hasNextLine()) {
        line = scanner.nextLine();
        this.sequence.append(line);
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

}
