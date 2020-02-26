package util;

import config.AdvancedXMLHandler;
import lombok.extern.java.Log;
import model.Entry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
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
import java.util.logging.Level;

@Log
public class FileUtil {
  private static String fileToWriteFromDB = "src/main/resources/1.xml";
  private static String fileToWriteNewFormatXml = "src/main/resources/2.xml";
  private static String templateForNewXmlFormat = "src/main/resources/transform.xsl";

  private FileUtil() {
  }

  public static void writeToFileDataFromDB(List<Entry> entities) {
    try {
      DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder icBuilder;
      icBuilder = icFactory.newDocumentBuilder();
      Document doc = icBuilder.newDocument();
      Element mainRootElement = doc.createElementNS(null, "entries");
      doc.appendChild(mainRootElement);

      for (Entry val : entities) {
        mainRootElement.appendChild(getEntity(doc, val.getField()));
      }

      cleanResourcesFiles();

      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource source = new DOMSource(doc);
      StreamResult file = new StreamResult(new File(fileToWriteFromDB));
      transformer.transform(source, file);
      log.info("Wrote data from database to xml file;");
    } catch (Exception ex) {
      log.log(Level.WARNING, "Failed to write into the file;");
    }
  }

  public static void cleanResourcesFiles() {
    try (FileWriter fstream1 = new FileWriter(fileToWriteFromDB);
         BufferedWriter out1 = new BufferedWriter(fstream1);
         FileWriter fstream2 = new FileWriter(fileToWriteNewFormatXml);
         BufferedWriter out2 = new BufferedWriter(fstream2)) {
      out1.write("");
      out1.close();
      out2.write("");
      out2.close();
      log.info("Cleaned xml files with data;");
    } catch (IOException ex) {
      log.log(Level.WARNING, "Failed of file cleaning;");
    }

  }

  public static void writeToFileNewXMLFormat() {
    try {
      TransformerFactory factoryTransform = TransformerFactory.newInstance();
      Source xslt = new StreamSource(new File(templateForNewXmlFormat));
      Transformer transformer = factoryTransform.newTransformer(xslt);
      Source xml = new StreamSource(new File(fileToWriteFromDB));
      transformer.transform(xml, new StreamResult(new File(fileToWriteNewFormatXml)));
      log.info("Wrote data to the new xml file;");
    } catch (Exception ex) {
      log.log(Level.WARNING, "Failed to write to the new xml file;");
    }
  }

  public static List<Long> getDataFromNewXmlFile() {
    List<Long> list = new ArrayList<>();

    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser parser = factory.newSAXParser();
      AdvancedXMLHandler handler = new AdvancedXMLHandler(list);
      parser.parse(new File(fileToWriteNewFormatXml), handler);
      log.info("Got data from the new xml file;");
    } catch (Exception ex) {
      log.log(Level.WARNING, "Failed to read from the new XML file;");
    }
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
