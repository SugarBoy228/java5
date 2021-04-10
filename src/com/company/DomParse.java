package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DomParse {
    private final String filePath;
    DomParse(String filepath) {
        this.filePath = filepath;
    }

    public void getDOMNodes() {
        File xmlFile = new File(this.filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            // теперь XML полностью загружен в память
            //вернули корневой элемент документа и нормализовали
            document.getDocumentElement().normalize();
            System.out.println("Корневой элемент: " + document.getDocumentElement().getNodeName());
            // получаем узлы с именем Language
            NodeList nodeList = document.getElementsByTagName("Language");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                // если узел является элементом
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println("\nЭлемент "+element.getNodeName());

                    // получаем список дочерних
                    NodeList childNodesList = element.getChildNodes();
                    for (int j = 0; j < childNodesList.getLength(); j++) {
                        Node childNode = childNodesList.item(j);
                        // если узел является элементом
                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            System.out.println(childNode.getNodeName()+": "+ childNode.getTextContent());
                        }
                    }
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void setDomNodes() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();// создаем пустой объект Document
            // создаем корневой элемент
            Element rootElement = doc.createElement("Languages");
            // добавляем корневой элемент в объект Document
            doc.appendChild(rootElement);

            // добавляем первый дочерний элемент к корневому
            rootElement.appendChild(getLanguage(doc, "1", "Java", "21"));
            //добавляем второй дочерний элемент к корневому
            rootElement.appendChild(getLanguage(doc, "2", "C", "44"));

            doc.getDocumentElement().normalize();
            //создаем объект TransformerFactory для преобразования документа в файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // установка параметров форматирования для красивого вывода
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            //получение исходного кода готового документа
            DOMSource source = new DOMSource(doc);
            //создание объекта для записи - файл
            StreamResult file = new StreamResult(new File("D:\\~~~3курс 2 сем\\~лабы\\ИСИС\\5 лаба ИС\\file2.xml"));
            //запись данных
            transformer.transform(source, file);
            System.out.println("Создание XML файла закончено");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // метод для создания нового узла XML-файла
    private static Node getLanguage(Document doc, String id, String name, String age) {
        Element language = doc.createElement("Language");
        language.setAttribute("id", id); // устанавливаем атрибут id
        // создаем элементы name и age
        language.appendChild(getLanguageElements(doc, "name", name));
        language.appendChild(getLanguageElements(doc, "age", age));
        return language;
    }

    // метод для создания одного узла
    private static Node getLanguageElements(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

}
