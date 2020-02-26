package config;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public class AdvancedXMLHandler extends DefaultHandler {
  private List<Long> list;

  public AdvancedXMLHandler(List<Long> list) {
    this.list = list;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    if (qName.equals("entry")) {
      Long element = Long.valueOf(attributes.getValue("field"));
      list.add(element);
    }
  }
}
