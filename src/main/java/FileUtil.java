import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
  private static String fileToWriteFromDB = "src/main/resources/1.xml";
  private static String fileToWriteNewFormatXml = "src/main/resources/2.xml";
  private static String templateForNewXmlFormat = "src/main/resources/transform.xsl";

  public static void writeToFileDataFromDB(List<Num> entities) throws Exception {
    DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder icBuilder;
    icBuilder = icFactory.newDocumentBuilder();
    Document doc = icBuilder.newDocument();
    Element mainRootElement = doc.createElementNS(null, "entries");
    doc.appendChild(mainRootElement);

    for (Num val : entities) {
      mainRootElement.appendChild(getEntity(doc, val.getNum()));
    }

    cleanResourcesFiles();

    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    DOMSource source = new DOMSource(doc);
    StreamResult file = new StreamResult(new File(fileToWriteFromDB));
    transformer.transform(source, file);
  }

  public static void cleanResourcesFiles() throws IOException {
    FileWriter fstream1 = new FileWriter(fileToWriteFromDB);
    BufferedWriter out1 = new BufferedWriter(fstream1);
    out1.write("");
    out1.close();
    FileWriter fstream2 = new FileWriter(fileToWriteNewFormatXml);
    BufferedWriter out2 = new BufferedWriter(fstream2);
    out2.write("");
    out2.close();
  }

  public static void writeToFileNewXMLFormat(List<Long> list) throws Exception {
    TransformerFactory factoryTransform = TransformerFactory.newInstance();
    Source xslt = new StreamSource(new File(templateForNewXmlFormat));
    Transformer transformer = factoryTransform.newTransformer(xslt);
    Source xml = new StreamSource(new File(fileToWriteFromDB));
    transformer.transform(xml, new StreamResult(new File(fileToWriteNewFormatXml)));
  }

  public static List<Long> getDataFromNewXmlFile() throws Exception {
    List<Long> list = new ArrayList<>();

    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();

    AdvancedXMLHandler handler = new AdvancedXMLHandler(list);
    parser.parse(new File(fileToWriteNewFormatXml), handler);
    return list;
  }

  private static Node getEntity(Document doc, Long value) {
    Element entry = doc.createElement("entry");
    entry.appendChild(getEntityElements(doc, value));
    return entry;
  }

  private static Node getEntityElements(Document doc, Long value) {
    Element node = doc.createElement("field");
    node.appendChild(doc.createTextNode(String.valueOf(value)));
    return node;
  }

}
