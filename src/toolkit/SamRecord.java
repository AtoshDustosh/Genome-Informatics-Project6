package toolkit;

import java.util.ArrayList;
import java.util.List;

import exceptions.FormatInvalidException;

public class SamRecord {
  private String qname = "";
  private int flag = 0;
  private String rname = "";
  private int pos = 0;
  private int mapq = 0;
  private String cigar = "";
  private String mrnm = "";
  private int mpos = 0;
  private int isize = 0;
  private String seq = "";
  private String qual = "";

  private List<String> optionalFields = new ArrayList<>();

  public SamRecord(String samLine) throws FormatInvalidException {
    this.parseSamLine(samLine);
  }

  public String getQNAME() {
    return this.qname;
  }

  public int getFLAG() {
    return this.flag;
  }

  public String getRNAME() {
    return this.rname;
  }

  public int getPOS() {
    return this.pos;
  }

  public int getMAPQ() {
    return this.mapq;
  }

  public String getCIGAR() {
    return this.cigar;
  }

  public String getMRNM() {
    return this.mrnm;
  }

  public int getMPOS() {
    return this.mpos;
  }

  public int getISIZE() {
    return this.isize;
  }

  public String getSEQ() {
    return this.seq;
  }

  public String getQUAL() {
    return this.qual;
  }

  @Override
  public String toString() {
    String str = "";
    str += this.qname + "\t";
    str += this.flag + "\t";
    str += this.rname + "\t";
    str += this.pos + "\t";
    str += this.mapq + "\t";
    str += this.cigar + "\t";
    str += this.mrnm + "\t";
    str += this.mpos + "\t";
    str += this.isize + "\t";
    str += this.seq + "\t";
    str += this.qual + "\t";
    for (int i = 0; i < this.optionalFields.size(); i++) {
      str += this.optionalFields.get(i) + "\t";
    }
    return str;
  }

  private void parseSamLine(String samLine) throws FormatInvalidException {
    try {
      String[] fields = samLine.split("\t");
      int findex = 0; // field index

      this.qname = fields[findex++];
      this.flag = Integer.valueOf(fields[findex++]);
      this.rname = fields[findex++];
      this.pos = Integer.valueOf(fields[findex++]);
      this.mapq = Integer.valueOf(fields[findex++]);
      this.cigar = fields[findex++];
      this.mrnm = fields[findex++];
      this.mpos = Integer.valueOf(fields[findex++]);
      this.isize = Integer.valueOf(fields[findex++]);
      this.seq = fields[findex++];
      this.qual = fields[findex++];

      if (fields.length > findex) {
        // has optional fields
        while (findex < fields.length) {
          this.optionalFields.add(fields[findex++]);
        }
      }
    } catch (Exception e) {
      throw new FormatInvalidException(e.getMessage());
    }
  }

}
