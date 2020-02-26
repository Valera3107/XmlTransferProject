import lombok.extern.java.Log;
import model.Entry;
import service.DataBase;
import util.FileUtil;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

@Log
public class Application {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    System.out.println("Input number (you have 3 attempts) : ");
    Long num = null;

    for (int i = 0; i < 3; i++) {
      try {
        num = sc.nextLong();
        log.info("Got number from console...");
        break;
      } catch (Exception ex) {
        log.log(Level.WARNING, "Bad credential!");
        if (i == 2) {
          log.log(Level.WARNING, "You have no attempts!");
        }
      }
      sc.next();
    }
    List<Entry> entities = DataBase.getDataFromDB(num);

    FileUtil.cleanResourcesFiles();
    FileUtil.writeToFileDataFromDB(entities);
    FileUtil.writeToFileNewXMLFormat();
    List<Long> list = FileUtil.getDataFromNewXmlFile();

    list.forEach(System.out::println);
    System.out.println("Result: " + list.stream().reduce(Long::sum).get());
  }

}
