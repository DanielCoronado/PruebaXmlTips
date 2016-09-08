package com.example.danie.pruebaxmltips;
/**
 * Created by matias on 29-08-2016.
 */
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button btnEscribirXml = null;
    private Button btnEscribirXmlPull = null;
    private Button btnLeerXmlDesdeMem = null;
    private Button btnLeerXmlDesdeXml = null;
    private Button btnLeerXmlDesdeRaw = null;
    private Button btnLeerXmlDesdeAssets = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEscribirXml = (Button)findViewById(R.id.btnEscribirXml);
        btnEscribirXmlPull = (Button)findViewById(R.id.btnEscribirXmlPull);
        btnLeerXmlDesdeMem = (Button)findViewById(R.id.btnLeerDesdeMemoria);
        btnLeerXmlDesdeXml = (Button)findViewById(R.id.btnLeerDesdeXml);
        btnLeerXmlDesdeRaw = (Button)findViewById(R.id.btnLeerDesdeRaw);
        btnLeerXmlDesdeAssets = (Button)findViewById(R.id.btnLeerDesdeAssets);

        btnEscribirXml.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try
                {
                    //Creamos un fichero en la memoria interna
                    OutputStreamWriter fout =
                            new OutputStreamWriter(
                                    openFileOutput("prueba.xml", Context.MODE_PRIVATE));

                    //clase que representa una cadena mutable
                    StringBuilder sb = new StringBuilder();

                    //Construimos el XML
                    //apend:transforma una subcadena
                    sb.append("<usuario>");
                    sb.append("<nombre>" + "Usuario1" + "</nombre>");
                    sb.append("<apellidos>" + "ApellidosUsuario1" + "</apellidos>");
                    sb.append("</usuario>");

                    //Escribimos el resultado a un fichero
                    fout.write(sb.toString());
                    fout.close();
                    //mostramos un mensaje si se ha creado
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fichero XML creado correctamente.", Toast.LENGTH_SHORT);

                    toast1.show();

                }
                catch (Exception ex)
                {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fichero XML no ha sido creado correctamente.", Toast.LENGTH_SHORT);

                    toast1.show();
                }
            }
        });

        btnEscribirXmlPull.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                try {
                    //esta clase serializa y desserializa el xml
                    XmlSerializer ser = Xml.newSerializer();

                    //Creamos un fichero en la memoria interna
                    OutputStreamWriter fout =
                            new OutputStreamWriter(
                                    openFileOutput("prueba_pull.xml",
                                            Context.MODE_PRIVATE));

                    //Asignamos el resultado del serializer al fichero
                    ser.setOutput(fout);

                    //Construimos el XML
                    ser.startTag("", "usuario");//comienza la etiqueta con espacio y despues el nombre

                    ser.startTag("", "nombre");
                    ser.text("Usuario1"); //escribe etiqueta que el startag no puede
                    ser.endTag("", "nombre");

                    ser.startTag("", "apellidos");
                    ser.text("ApellidosUsuario1");
                    ser.endTag("", "apellidos");

                    ser.endTag("", "usuario");

                    ser.endDocument();//finaliza la escritura

                    fout.close();

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fichero XML creado correctamente.", Toast.LENGTH_SHORT);

                    toast1.show();
                }
                catch (Exception e)
                {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fichero XML no ha sido creado correctamente.", Toast.LENGTH_SHORT);

                    toast1.show();
                }
            }
        });

        btnLeerXmlDesdeMem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try
                {
                    //obtenemos el fichero xml
                    FileInputStream fil = openFileInput("prueba.xml");

                    //DOM (Por ejemplo)
                    //instaciamos la fabrica del DOM
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();//creamos un nuevo documento
                    Document dom = builder.parse(fil);//parseamos el fichero

                    //A partir de aquí se trataría el árbol DOM como siempre.
                    //Por ejemplo:
                    Element root = dom.getDocumentElement();//obtenemos los documentos del xml

                    //...

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fichero XML leido correctamente.", Toast.LENGTH_SHORT);

                    toast1.show();
                }
                catch (Exception ex)
                {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fichero XML no ha sido leido correctamente.", Toast.LENGTH_SHORT);

                    toast1.show();
                }
            }
        });

        btnLeerXmlDesdeXml.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try
                {
                    //parseamos el documento obtenido
                    XmlResourceParser xrp = getResources().getXml(R.xml.prueba);

                    //A partir de aquí utilizamos la variable 'xrp' como
                    //cualquier otro parser de tipo XmlPullParser. Por ejemplo:

                    int evento = xrp.getEventType();//obtenemos el tipo del evento

                    if(evento == XmlPullParser.START_DOCUMENT) {//verificamos si el elemento esta y puede ser leido
                        //...
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "Fichero XML leido correctamente.", Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                }
                catch (Exception ex)
                {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fichero XML no ha sido leido correctamente.", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        });

        btnLeerXmlDesdeRaw.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try
                {

                    InputStream is = getResources().openRawResource(R.raw.prueba);

                    //DOM (Por ejemplo)
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document dom = builder.parse(is);

                    //A partir de aquí se trataría el árbol DOM como siempre.
                    //Por ejemplo:
                    Element root = dom.getDocumentElement();

                    //...

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fichero XML leido correctamente.", Toast.LENGTH_SHORT);

                    toast1.show();
                }
                catch (Exception ex)
                {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fichero XML no ha sido leido correctamente.", Toast.LENGTH_SHORT);

                    toast1.show();
                }
            }
        });

        btnLeerXmlDesdeAssets.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    AssetManager assetMan = getAssets();
                    InputStream is = assetMan.open("prueba_asset.xml");

                    //DOM (Por ejemplo)
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document dom = builder.parse(is);

                    //A partir de aquí se trataría el árbol DOM como siempre.
                    //Por ejemplo:
                    Element root = dom.getDocumentElement();

                    //...

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fichero XML leido correctamente.", Toast.LENGTH_SHORT);

                    toast1.show();
                }
                catch(Exception ex)
                {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fichero XML no ha sido leido correctamente.", Toast.LENGTH_SHORT);

                    toast1.show();
                }
            }
        });
    }
}
