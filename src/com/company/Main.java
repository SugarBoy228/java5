package com.company;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Main {

    private static String setValue() {
        while(true) {
            Scanner sc = new Scanner(System.in);
            String str = sc.next();
            if(str.length() > 0) {
                return str;
            } else {
                System.out.println("Необходимо ввести значение!");
            }
        }


    }
    private static Student setNewStudent(int size) {
        String[] strings = new String[6];
        System.out.println("Введите значения для ученика:");
        System.out.println("Введите имя:");
        strings[0] = setValue();
        System.out.println("Введите фамилию:");
        strings[1] = setValue();
        System.out.println("Введите отчество:");
        strings[2] = setValue();
        System.out.println("Введите школу:");
        strings[3] = setValue();
        System.out.println("Введите класс:");
        strings[4] = setValue();
        System.out.println("Введите возраст:");
        strings[5] = setValue();
        return new Student(size,strings[0],strings[1],strings[2],
                strings[3],strings[4], strings[5]);

    }

    public static int getNum() {
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()) {
            System.out.println("Введенно некоректное значение!");
            System.out.print("Введите значение повторно: ");
            sc.next();
        }
        return sc.nextInt();
    }

    public static int choiceWithWork() {
        System.out.println("Выберите с чем работать: \n" +
                "1: XML\n" +
                "2: БД\n" +
                "3: Конвертировать данные из XML в БД\n" +
                "4: Конвертировать данные из БД в XML\n");
        int type = 0;
        while (true) {
            type = getNum();
            if (type >= 1 && type <= 4) {
                return type;
            }
            System.out.println("Можно ввести только 1,2,3,4");
        }
    }

    public static void start() {
        String filePath = "D:\\~~~3курс 2 сем\\~лабы\\ИСИС\\5 лаба ИС\\file.xml";
        int type = choiceWithWork();
        while (true) {
            System.out.println("Выберите действие: \n" +
                    "1: Вывести всё содержимое\n" +
                    "2: Найти содержимое по параметру\n" +
                    "3: Добавить новую запись\n" +
                    "4: Изменить запись\n" +
                    "5: Удалить запись\n" +
                    "9: Выбрать заново с чем работать\n" +
                    "0: Завершить работу\n");
            var choice = getNum();
            switch (choice) {
                case 1: {
                    if (type == 1) {
                        var sax = new SAXParse();
                        var students =  sax.readerSaxDocument(filePath);
                        for (Student student : students) {
                            System.out.println(student.toString());
                        }
                    }
                }break;
                case 2: {
                    //пределать в конце
                    var sax = new SAXParse();
                    int typeSearch = 0;
                    String content = "";
                    while (true) {
                        System.out.println("Выберите по какому параметру искать:\n" +
                                "1: По тегу\n" +
                                "2: По id\n");
                        typeSearch = getNum();
                        System.out.print("Выберите содержимое поиска:\n");
                        Scanner scanner = new Scanner(System.in);
                        content = scanner.nextLine();
                        if (typeSearch >= 1 && typeSearch <= 2) {
                            sax.searchSaxDocument(filePath, typeSearch, content);
                            break;
                        } else {
                            System.out.println("Можно ввести только 1,2");
                        }

                    }
                }break;
                case 3: {
                    var sax = new SAXParse();
                    var students =  sax.readerSaxDocument(filePath);
                    var newStudent = setNewStudent(students.size());
                    var dom = new DomParse(filePath);
                    dom.setDomNodes(filePath, students, newStudent);

                }break;
                case 4: {

                }break;
                case 5: {

                }break;
                case 9: {
                    start();
                }break;
                case 0: {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {

        //DOM XML
//        var domTest = new DomParse("D:\\~~~3курс 2 сем\\~лабы\\ИСИС\\5 лаба ИС\\file.xml");
//        domTest.getDOMNodes();
//        domTest.setDomNodes();

        //SAX XML
        //"D:\\~~~3курс 2 сем\\~лабы\\ИСИС\\5 лаба ИС\\saxfile1.xml"
        //    var saxTest = new SAXParse();
        //saxTest.readerSaxDocument("D:\\~~~3курс 2 сем\\~лабы\\ИСИС\\5 лаба ИС\\saxfile1.xml");
//        saxTest.writeSaxDocument("D:\\~~~3курс 2 сем\\~лабы\\ИСИС\\5 лаба ИС\\saxfile2.xml");
//        PropertiesParse propertiesParse = new PropertiesParse();
//        propertiesParse.readSettings("D:\\~~~3курс 2 сем\\~лабы\\ИСИС\\5 лаба ИС\\settings.properties");

//        Работа с БД
//        var base = new MySqlParse();
//        base.workDataBase();
        start();
    }
}
