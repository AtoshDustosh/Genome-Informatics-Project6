package args;

public enum FilePath {
  FA("src/data/chr22.hg19.fa"),
  SAM("src/data/SRR8244841.chr22.hg19.1k.sam"),
  VCF("src/data/chr22.hg19.1k.vcf"),
  ;

  private String filePath = "";

  FilePath(String filePath) {
    this.filePath = filePath;
  }

  public String path() {
    return this.filePath;
  }
}
