package com.pubsub.laba.controller;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.pubsub.laba.DeciderMsApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/pubsub")
public class PubSubController {
    private String topMessageName = "get-message";
    private String subMessageName = "get-message-sub";
    private String topBoolName = "get-bool";
    private String subBoolName = "get-bool-sub";
    private String topIDName = "get-id";
    private String subIDName = "get-id-sub";
    private static final Logger logger = LoggerFactory.getLogger(PubSubController.class);
    private PubSubTemplate pubSubTemplate;
    public PubSubController(PubSubTemplate pubSubTemplate) {
        this.pubSubTemplate = pubSubTemplate;
    }

    // http://localhost:8081/pubsub/publish

    @GetMapping("/publish")
    public String publish() {
        getMsg();
        double x = Math.random() * 1;
        if(x > 0.5){
            sendMsg("true");
        }
        else{
            sendMsg("false");
        }
        return "Message published successfully";
    }

    public void getMsg(){
        Subscriber messageSubscriber = pubSubTemplate.subscribe(subMessageName, (msg) -> {
            logger.info("Message received from [" + topMessageName + "] for [" + subMessageName + "] subscription: "
                    + msg.getPubsubMessage().getData().toStringUtf8());
            msg.ack();
        });
    }

    public void sendMsg(String message){
        ListenableFuture<String> messageIdFuture = pubSubTemplate.publish(topBoolName, message);
        messageIdFuture.addCallback(new SuccessCallback<String>() {
            @Override
            public void onSuccess(String messageId) {
                logger.info("published with message id: " + messageId);
            }
        }, new FailureCallback() {
            @Override
            public void onFailure(Throwable t) {
                logger.error("failed to publish: " + t);
            }
        });

        try {
            String messageId = messageIdFuture.get();
            pubSubTemplate.publish(topIDName, messageId);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
