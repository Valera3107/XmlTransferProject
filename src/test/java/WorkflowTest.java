import model.Num;
import service.DataBase;
import util.FileUtil;

import java.util.List;
import java.util.stream.Collectors;

public class WorkflowTest {
  public void test() throws Exception {
    List<Num> entities = DataBase.getDataFromDB(5L);

    FileUtil.cleanResourcesFiles();
    FileUtil.writeToFileDataFromDB(entities);
    List<Long> data = entities.stream().map(Num::getNum).collect(Collectors.toList());
    FileUtil.writeToFileNewXMLFormat(data);
    List<Long> list = FileUtil.getDataFromNewXmlFile();

    System.out.println("Test result: " + list.stream().reduce(Long::sum).get().equals(15L));
  }
}
