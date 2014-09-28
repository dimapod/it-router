package org.itrade.jms;

import org.hornetq.jms.client.HornetQDestination;
import org.itrade.manager.JmsRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class JmsManagerListener implements SessionAwareMessageListener {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JmsRouter jmsRouter;

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        String destination = ((HornetQDestination) message.getJMSDestination()).getName();
        logger.debug("Message received from '{}': {}", destination, message);
        jmsRouter.routeJmsMessage(message, destination);
    }
}


