import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) throws Exception {
    List<Num> entities = DataBase.getDataFromDB();

    FileUtil.cleanResourcesFiles();
    FileUtil.writeToFileDataFromDB(entities);
    List<Long> data = entities.stream().map(Num::getNum).collect(Collectors.toList());
    FileUtil.writeToFileNewXMLFormat(data);
    List<Long> list = FileUtil.getDataFromNewXmlFile();

    list.forEach(System.out::println);
    System.out.println("Result: " + list.stream().reduce(Long::sum).get());
  }

}
