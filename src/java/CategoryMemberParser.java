/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author User
 */
public class CategoryMemberParser extends DefaultHandler{

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
          throws SAXException {
 
          switch (qName) {
               // Create a new Employee.
               case "categorymembers": {
                   String temp = attributes.getValue("cmcontinue");
                   if(temp!=null)
                   { 
                       Tesstwiki.setCmContinue(temp);
                   }
                   break;
               }
          }
     }

}
