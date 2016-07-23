package be.uantwerpen.sc.tools;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thomas on 22/07/2016.
 */
@Service
public class DevelopersList
{
    private static String DEVELOPERSLIST = "DevelopersList.xml";

    public List<String> getSupervisorDevelopers() throws Exception
    {
        List<String> supervisors = new ArrayList<String>();
        ClassLoader classLoader = getClass().getClassLoader();

        try
        {
            File developersFile = new File(classLoader.getResource(DEVELOPERSLIST).toURI());

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(developersFile);

            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("supervisors").item(0).getChildNodes();

            for(int i = 0; i < nodeList.getLength(); i++)
            {
                Node node = nodeList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    supervisors.add(((Element)node).getElementsByTagName("name").item(0).getTextContent());
                }
            }
        }
        catch(IOException e)
        {
            throw new Exception("Could not load developers list of supervisors! " + e.getMessage());
        }

        return supervisors;
    }

    public Map<String, List<String>> getStudentDevelopers() throws Exception
    {
        Map<String, List<String>> students = new TreeMap<String, List<String>>();
        ClassLoader classLoader = getClass().getClassLoader();

        try
        {
            File developersFile = new File(classLoader.getResource(DEVELOPERSLIST).toURI());

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(developersFile);

            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("students").item(0).getChildNodes();

            for(int i = 0; i < nodeList.getLength(); i++)
            {
                Node group = nodeList.item(i);

                if(group.getNodeType() == Node.ELEMENT_NODE)
                {
                    String groupYear = ((Element)group).getAttribute("year");
                    List<String> studentList = new ArrayList<String>();

                    NodeList studentGroup = group.getChildNodes();

                    for(int j = 0; j < studentGroup.getLength(); j++)
                    {
                        Node node = studentGroup.item(j);

                        if(node.getNodeType() == Node.ELEMENT_NODE)
                        {
                            studentList.add(((Element)node).getElementsByTagName("name").item(0).getTextContent());
                        }
                    }

                    students.put(groupYear, studentList);
                }
            }
        }
        catch(IOException e)
        {
            throw new Exception("Could not load developers list of students! " + e.getMessage());
        }

        return students;
    }
}
