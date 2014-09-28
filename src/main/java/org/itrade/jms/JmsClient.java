package org.itrade.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
public class JmsClient {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("jmsTemplate")
    private JmsTemplate jmsTemplate;

    public void sendMessageTo(Message message, String destination) {
        logger.debug("Send message to '{}': {}", destination, message);
        try {
            jmsTemplate.send(destination, session -> message);
        } catch (JmsException e) {
            logger.error("Error when sending message: " + message, e);
        }
    }
}
