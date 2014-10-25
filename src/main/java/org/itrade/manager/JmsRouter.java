package org.itrade.manager;

import org.itrade.jms.JmsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jms.Message;
import java.util.HashMap;

@Service
public class JmsRouter {
    Logger logger = LoggerFactory.getLogger(getClass());

    private static HashMap<String, String> mapDestination = new HashMap<>();

    @Autowired
    private JmsClient jmsClient;

    @PostConstruct
    private void init() {
        mapDestination.put("INJECTION", "SEMANTIC_IN");
    }

    public void routeJmsMessage(Message message, String fromQueue) {
        String toQueue = mapDestination.get(fromQueue);
        if (toQueue == null) {
            logger.error("No path found for queue '{}'", fromQueue);
            throw new RouterException("No path found for queue " + fromQueue);
        }
        logger.debug("Destination resolved: {} -> {} for message {}", fromQueue, toQueue, message);

        jmsClient.sendMessageTo(message, toQueue);
    }

}
