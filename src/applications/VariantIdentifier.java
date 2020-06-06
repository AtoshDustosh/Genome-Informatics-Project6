package applications;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import args.FilePath;
import toolkit.FaManager;
import toolkit.SamManager;
import toolkit.SamRecord;
import toolkit.VcfRecord;

public class VariantIdentifier {
  public static final double VARIANT_IDNETIFIED_RATIO = 0.4;
  public static final int VARIANT_IDENTIFIED_CNT = 10;

  private FaManager faManager = null;
  private SamManager samManager = null;

  private Map<Integer, AlleleStatus> statistics = new HashMap<>();

  public VariantIdentifier(String faFile, String samFile) {
    this.faManager = new FaManager(faFile);
    this.samManager = new SamManager(samFile);
//    this.checkSam();
    this.collectStatistics();
  }

  public static void main(String[] args) {
    VariantIdentifier vi = new VariantIdentifier(FilePath.FA.path(),
        FilePath.SAM.path());
    List<VcfRecord> vcfRecords = vi.findVariances();
    try {
      PrintWriter printer = new PrintWriter(
          new FileOutputStream(FilePath.VCF.path()));
      for (int i = 0; i < vcfRecords.size(); i++) {
        printer.write(vcfRecords.get(i).toString() + "\n");
      }
      printer.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public List<VcfRecord> findVariances() {
    List<VcfRecord> vcfRecords = new ArrayList<>();
    String chrom = this.faManager.faHeader();
    int idNum = 0;
    for (int pos : this.statistics.keySet()) {
      AlleleStatus alS = this.statistics.get(pos);
      boolean debug = false;
      if (this.isVariant(pos, alS) || debug) {
        char refChar = this.faManager.charAt(pos - 1);
        String ref = "" + refChar;
        String id = "" + idNum;
        double possibility = alS.totalCnt();
        possibility = possibility - alS.cntBp(refChar);
        possibility = 1 - possibility / alS.totalCnt();
        double qual = -10 * Math.log10(possibility);
        if (qual > 100) {
          qual = 100;
        }
        VcfRecord vcfRecord = new VcfRecord(chrom, pos, id, ref,
            alS.toVcfString(), qual, "PASS", "", "", "");
        vcfRecords.add(vcfRecord);
//        System.out.println(vcfRecord);
        System.out.println(pos + " - " + refChar + " - " + alS.toString());
      }
      idNum++;
    }
    return vcfRecords;
  }

  private void collectStatistics() {
    int totalCharChecked = 0;
    int totalAlleleChecked = 0;
    for (int i = 0; i < this.samManager.samRecordCnt(); i++) {
      SamRecord record = this.samManager.getRecord(i);
      String seq = record.getSEQ();
      int pos = record.getPOS();
      int seqLength = seq.length();
      for (int j = 0; j < seqLength; j++) {
        totalCharChecked++;
        AlleleStatus alS = null;
        char newCh = seq.charAt(j);
        if (this.statistics.containsKey(pos + j)) {
          this.statistics.get(pos + j).addBp(newCh);
        } else {
          alS = new AlleleStatus();
          alS.addBp(newCh);
          this.statistics.put(pos + j, alS);
          totalAlleleChecked++;
        }
      }
    }
    System.out.println("total char checked - " + totalCharChecked);
    System.out.println("total allele checked - " + totalAlleleChecked);
  }

  private boolean isVariant(int pos, AlleleStatus alS) {
    int seqIndex = pos - 1;
    char refChar = this.faManager.charAt(seqIndex);
    int readsCnt = alS.totalCnt();
    double possibility = alS.totalCnt();
    possibility = possibility - alS.cntBp(refChar);
    possibility = possibility / alS.totalCnt();
    if (possibility >= VARIANT_IDNETIFIED_RATIO
        && readsCnt >= VARIANT_IDENTIFIED_CNT) {
      return true;
    } else {
      return false;
    }
  }

  @SuppressWarnings("unused")
  private void checkSam() {
    for (int i = 0; i < this.samManager.samRecordCnt(); i = i * 3 / 2 + 1) {
      SamRecord record = this.samManager.getRecord(i);
      String recSeq = record.getSEQ();
      int pos = record.getPOS();
      int seqLength = recSeq.length();
      String refSeq = this.faManager.subSequence(pos - 1, pos + seqLength - 1);
      System.out.println("(" + (i + 1) + ")rec: " + recSeq);
      System.out.println("(" + (i + 1) + ")ref: " + refSeq);
    }
  }

  class AlleleStatus {
    private int cntA = 0;
    private int cntC = 0;
    private int cntG = 0;
    private int cntT = 0;

    public void addBp(char bp) {
      switch (bp) {
        case 'a':
        case 'A':
          this.cntA++;
          break;
        case 'c':
        case 'C':
          this.cntC++;
          break;
        case 'g':
        case 'G':
          this.cntG++;
          break;
        case 't':
        case 'T':
          this.cntT++;
          break;
        default:
      }
    }

    public int cntBp(char bp) {
      switch (bp) {
        case 'a':
        case 'A':
          return this.cntA;
        case 'c':
        case 'C':
          return this.cntC;
        case 'g':
        case 'G':
          return this.cntG;
        case 't':
        case 'T':
          return this.cntT;
        default:
          return 0;
      }
    }

    public int totalCnt() {
      return this.cntA + this.cntC + this.cntG + this.cntT;
    }

    public String toVcfString() {
      String str = "";
      List<String> variants = new ArrayList<>();
      if (this.cntA > 0) {
        variants.add("A");
      }
      if (this.cntC > 0) {
        variants.add("C");
      }
      if (this.cntG > 0) {
        variants.add("G");
      }
      if (this.cntT > 0) {
        variants.add("T");
      }
      for (int i = 0; i < variants.size(); i++) {
        str += variants.get(i) + ",";
      }
      str = str.substring(0, str.length() - 1);
      return str;
    }

    @Override
    public String toString() {
      String str = "";
      str += "A:" + this.cntA + " ";
      str += "C:" + this.cntC + " ";
      str += "G:" + this.cntG + " ";
      str += "T:" + this.cntT + " ";
      return str;
    }

  }
}
