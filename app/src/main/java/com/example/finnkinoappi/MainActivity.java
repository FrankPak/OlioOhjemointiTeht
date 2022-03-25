package com.example.finnkinoappi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    private teatteriteko tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Spinner spinner = findViewById((R.id.spinnerTeatteri));

        tt = teatteriteko.getInstance();

        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tt.teatteriarray()));
    }

    public static class teatteriteko {
        ArrayList<Teatteri> teattrit;

        static teatteriteko tt = new teatteriteko();

        private teatteriteko(){
            teattrit = new ArrayList<>();
            lisaaTeatteri();
        }

        public static teatteriteko getInstance(){
            return tt;
        }


        private void lisaaTeatteri(){
            String Url = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = null;

            try {
                DocumentBuilder dbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                doc = dbuilder.parse(Url);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } finally {
                System.out.println("##########done#########");
            }

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");
            for(int i=0;i<nList.getLength();i++){
                Node node =nList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    int id = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                    String name = element.getElementsByTagName("Name").item(0).getTextContent();
                    this.teattrit.add(new Teatteri(id, name));
                }
            }
        }

        public String[] teatteriarray(){

            String[] payload = new String[this.teattrit.size()];

            for(int i =0; i<this.teattrit.size(); i++){
                payload[i]=this.teattrit.get(i).GetName();
            }

            return payload;
        }

    }

}

