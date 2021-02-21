package br.com.caelum.jms;


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
		
		
		Message message = session.createTextMessage("<pedido><id>"+ 112 + "</id></pedido>");
		producer.send(message);
		
		session.close();
		connection.close();
		context.close();
	}

}