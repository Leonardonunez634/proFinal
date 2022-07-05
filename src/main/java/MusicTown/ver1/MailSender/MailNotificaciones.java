package MusicTown.ver1.MailSender;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailNotificaciones {
	
	@Autowired
	private JavaMailSender jms;
	
	@Async //indica que la operación se va a realizar de manera independiente o asincrónica de todos los procesos
	public void mailsender(String mail,String titulo,String descripcion) {
		SimpleMailMessage mensaje = new SimpleMailMessage();
		
		mensaje.setTo(mail);;
		//mensaje.setFrom("@noreply");
		mensaje.setSubject(titulo);
		mensaje.setText(descripcion);
		mensaje.setSentDate(new Date());
		
		jms.send(mensaje);
		
	}
	
}
