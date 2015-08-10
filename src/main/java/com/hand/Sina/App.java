package com.hand.Sina;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.JsonObject;

public class App {

	public static void main(String[] args) {
		new Get().start();

	}
static	class Get extends Thread{
		HttpClient client = HttpClients.createDefault();
		@Override
		public void run() {
			HttpGet get = new HttpGet("http://hq.sinajs.cn/list=sz300170");
			
			try {
				
				
			HttpResponse response =	client.execute(get);
			HttpEntity entity =	response.getEntity();
			String result =	EntityUtils.toString(entity, "UTF-8");
			System.out.println(result);
			
			//生成XML文件
			//Dom
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			Document document = builder.newDocument();
			Element root = document.createElement("xml");
			//root.setAttribute("cat", "it");
			
			Element lan1 = document.createElement("stock");
			//lan1.setAttribute("id", "1");
			Element name1 = document.createElement("name");
			name1.setTextContent("汉得");
			Element open = document.createElement("open");
			open.setTextContent("20.000");
			Element close = document.createElement("close");
			close.setTextContent("19.400");
			Element current = document.createElement("current");
			current.setTextContent("21.130");
			Element high = document.createElement("high");
			high.setTextContent("21.340");
			Element low = document.createElement("low");
			low.setTextContent("19.540");
			
			lan1.appendChild(name1);
			lan1.appendChild(open);
			lan1.appendChild(close);
			lan1.appendChild(current);
			lan1.appendChild(high);
			lan1.appendChild(low);
			
//			Element lan2 = document.createElement("lan");
//			lan2.setAttribute("id", "2");
//			Element name2 = document.createElement("name");
//			name2.setTextContent("Java");
//			Element ide2 = document.createElement("ide");
//			ide2.setTextContent("Eclipse");
//			lan2.appendChild(name2);
//			lan2.appendChild(ide2);
//			
//			Element lan3 = document.createElement("lan");
//			lan3.setAttribute("id", "3");
//			Element name3 = document.createElement("name");
//			name3.setTextContent("Java");
//			Element ide3 = document.createElement("ide");
//			ide3.setTextContent("Eclipse");
//			lan3.appendChild(name3);
//			lan3.appendChild(ide3);
			
			root.appendChild(lan1);
			//root.appendChild(lan2);
			//root.appendChild(lan3);
			document.appendChild(root);
			
			//...........
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(document), new StreamResult(writer));
			System.out.println(writer.toString());
			
			transformer.transform(new DOMSource(document), new StreamResult(new
					File("newxml.xml")));
				
			//输出JSON
			JsonObject object = new JsonObject();
			object.addProperty("name", "汉得");
			
			object.addProperty("open", 20.000);
			
			object.addProperty("close", 19.400);
			
			object.addProperty("current", 21.130);
			
			object.addProperty("high", 21.340);
			
			object.addProperty("low", 19.540);
			
//			JsonArray array = new JsonArray();
//			
//			JsonObject lan1 = new JsonObject();
//			lan1.addProperty("id", 1);
//			lan1.addProperty("name", "java");
//			lan1.addProperty("ide", "Eclipse");
//			array.add(lan1);
//			
//			JsonObject lan2 = new JsonObject();
//			lan2.addProperty("id", 2);
//			lan2.addProperty("name", "java");
//			lan2.addProperty("ide", "Eclipse");
//			array.add(lan2);
//			
//			JsonObject lan3 = new JsonObject();
//			lan3.addProperty("id", 3);
//			lan3.addProperty("name", "C#");
//			lan3.addProperty("ide", "Visual Studio");
//			array.add(lan3);
			
			//object.add("language", array);
			//object.addProperty("pop", true);
			transformer.transform(new DOMSource(document), new StreamResult(new
					File("newjson.json")));
			
			System.out.println(object.toString());
				
			} catch (ClientProtocolException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
			
				e.printStackTrace();
			} catch (TransformerConfigurationException e) {
			
				e.printStackTrace();
			} catch (TransformerException e) {
				
				e.printStackTrace();
			}
			
		}
	}

}
