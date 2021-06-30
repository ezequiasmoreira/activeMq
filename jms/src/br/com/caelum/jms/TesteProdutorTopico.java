package br.com.caelum.jms;


import java.io.StringWriter;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TesteProdutorTopico {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory)context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination topico = (Destination)context.lookup("loja");		
		
		MessageProducer producer = session.createProducer(topico);
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		
		//Enviar para o consumidor um XML
		/*StringWriter writer = new StringWriter();
		JAXB.marshal(pedido, writer);		
		String xml = writer.toString();
		System.out.println(xml);
		Message message = session.createTextMessage(xml);*/
		
		//Enviar para o consumidor um objeto
		Message message = session.createObjectMessage(pedido);
		//message.setBooleanProperty("ebook", false);
		producer.send(message);
		
		session.close();
		connection.close();
		context.close();
	}

}
