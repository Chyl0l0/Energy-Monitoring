package ro.tuc.ds2020.apps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    @Autowired
    public SimpMessagingTemplate  messagingTemplate;

    public void sendMessage( String message ) {

        messagingTemplate.convertAndSend( "/topics/all", message );
    }

}
