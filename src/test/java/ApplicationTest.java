import model.Entry;
import org.junit.Test;
import service.DataBase;
import util.FileUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {

  @Test
  public void cleanFiles() {
    FileUtil.cleanResourcesFiles();
    assertEquals(0, countLines("src/main/resources/1.xml"));
    assertEquals(0, countLines("src/main/resources/2.xml"));
  }

  @Test
  public void writeToXmlFileFromDB() {
    List<Entry> dataFromDB = DataBase.getDataFromDB(1L);
    FileUtil.writeToFileDataFromDB(dataFromDB);
    assertEquals(6, countLines("src/main/resources/1.xml"));
  }

  @Test
  public void writeToNewXmlFileDataFromAnotherFile() {
    FileUtil.writeToFileNewXMLFormat();
    assertEquals(3, countLines("src/main/resources/2.xml"));
  }

  @Test
  public void checkDataFromNewXmlFile() {
    int size = FileUtil.getDataFromNewXmlFile().size();
    assertEquals(0, size);
  }

  private int countLines(String path) {
    try {
      File myFile = new File(path);
      FileReader fileReader = new FileReader(myFile);
      LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
      int lineNumber = 0;
      while (lineNumberReader.readLine() != null) {
        lineNumber++;
      }
      System.out.println(lineNumber);
      lineNumberReader.close();

      return lineNumber;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

}
