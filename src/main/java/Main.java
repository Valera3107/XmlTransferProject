import model.Num;
import service.DataBase;
import util.FileUtil;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) throws Exception {

    Scanner sc = new Scanner(System.in);
    System.out.println("Input number: ");
    Long num = sc.nextLong();
    List<Num> entities = DataBase.getDataFromDB(num);

    FileUtil.cleanResourcesFiles();
    FileUtil.writeToFileDataFromDB(entities);
    List<Long> data = entities.stream().map(Num::getNum).collect(Collectors.toList());
    FileUtil.writeToFileNewXMLFormat(data);
    List<Long> list = FileUtil.getDataFromNewXmlFile();

    list.forEach(System.out::println);
    System.out.println("Result: " + list.stream().reduce(Long::sum).get());
  }

}
