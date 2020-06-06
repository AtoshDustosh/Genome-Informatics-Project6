package toolkit;

public class VcfRecord {
  private String chrom = "";
  private int pos = 0;
  private String id = "";
  private String ref = "";
  private String alt = "";
  private double qual = 0;
  private String filter = "";

  private String info = "";
  private String format = "";

  private String optionalFields = "";

  public VcfRecord(String chrom, int pos, String id, String ref, String alt,
      double qual, String filter, String info, String format,
      String optionalFields) {
    this.chrom = chrom;
    this.pos = pos;
    this.id = id;
    this.ref = ref;
    this.alt = alt;
    this.qual = qual;
    this.filter = filter;
    this.info = info;
    this.format = format;
    this.optionalFields = optionalFields;
  }

  @Override
  public String toString() {
    String str = "";
    str += this.chrom + "\t";
    str += this.pos + "\t";
    str += this.id + "\t";
    str += this.ref + "\t";
    str += this.alt + "\t";
    str += this.qual + "\t";
    str += this.filter + "\t";
    str += this.info + "\t";
    str += this.format + "\t";
    str += this.optionalFields + "\t";
    return str;
  }

  public String getChrom() {
    return this.chrom;
  }

  public int getPos() {
    return this.pos;
  }

  public String getId() {
    return this.id;
  }

  public String getRef() {
    return this.ref;
  }

  public String getAlt() {
    return this.alt;
  }

  public double getQual() {
    return this.qual;
  }

  public String getFilter() {
    return this.filter;
  }

  public String getInfo() {
    return this.info;
  }

  public String getFormat() {
    return this.format;
  }

  public String getOptionalFields() {
    return this.optionalFields;
  }

}
