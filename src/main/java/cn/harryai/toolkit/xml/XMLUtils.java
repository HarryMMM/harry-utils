package cn.harryai.toolkit.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Harry
 * @since 2019/8/12 18:43
 */
public final class XMLUtils {
    private static final SAXReader saxReader = new SAXReader();
    private Document document;

    private XMLUtils() {
    }

    public static XMLUtils load(String path) throws DocumentException {
        XMLUtils xmlUtils = new XMLUtils();
        xmlUtils.document = saxReader.read(path);
        return xmlUtils;
    }

    public Document getDocument() {
        return document;
    }

    public Element getRootElement() {
        return document.getRootElement();
    }
}
