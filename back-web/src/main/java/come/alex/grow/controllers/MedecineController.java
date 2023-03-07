package come.alex.grow.controllers;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import come.alex.grow.entity.Medicines;
import come.alex.grow.repositories.MedicinesRepository;
import come.alex.grow.services.MedService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/medicines")
public class MedecineController {
    private String topMessageName = "get-message";
    private String topBoolName = "get-bool";
    private String subBoolName = "get-bool-sub";
    private String topIDName = "get-id";
    private PubSubTemplate pubSubTemplate;

    public MedecineController(PubSubTemplate pubSubTemplate) {
        this.pubSubTemplate = pubSubTemplate;
    }
    private static final Logger logger = LoggerFactory.getLogger(MedecineController.class);
    @Autowired
    private MedService medService;
    public List<Medicines> medic = new ArrayList<>();
    @Autowired
    private MedicinesRepository medicinesRepository;

    @GetMapping("/get-all")
    @ApiOperation(value = "Получение всех лекарств")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Лекарства найдены"),
            @ApiResponse(code = 404, message = "Лекарства не найдены")
    })
    public String getAllMedicines(){
        sendMessages("Получение всех лекарств");
        getMessages();
        logger.info("-----[GET ALL Medicines]-----");
        medic = medService.getMedicines();
        String s = "";
        for (Medicines m : medic) {
            s += m.getInfo() + "\n";
        }
        return "Get all medicine!\n" + s;
    }

    @PostMapping("/add")
    @ApiOperation(value = "Добавление лекарства")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Лекарство добавлено"),
            @ApiResponse(code = 400, message = "Вы не ввели данные для добавления лекарства"),
            @ApiResponse(code = 404, message = "Лекарство не добавлено")
    })
    public String createMedicine(@RequestParam String name, @RequestParam Date date, @RequestParam int doza){
        logger.info("-----[CREATE Medicine]-----");
        Medicines med = new Medicines(name, date, doza);
        Medicines m = medService.saveMedicine(med);
        String id = String.valueOf(med.getId_med());
        return "Successfully create! with id = " + id + "\n" + m.getInfo();
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Обновление лекарства")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Лекарство обновлено"),
            @ApiResponse(code = 400, message = "Вы не ввели данные для обновления лекарства"),
            @ApiResponse(code = 404, message = "Лекарства не обновлено")
    })
    public String putMedicine(@PathVariable int id, @RequestParam String name, @RequestParam Date date, @RequestParam int doza){
        logger.info("-----[UPDATE Medicine]-----");
        Medicines med = medService.findByMedId(id);
        med.setName(name);
        med.setDate(date);
        med.setDoza(doza);
        Medicines m = medService.saveMedicine(med);
        return "Successfully update!\n" + m.getInfo();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Удаление лекарства")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Лекарство удалено"),
            @ApiResponse(code = 400, message = "Вы не ввели id для удаления лекарства"),
            @ApiResponse(code = 404, message = "Лекарство не удалено")
    })
    public String deleteMedicine(@PathVariable int id){
        logger.info("-----[DELETE Medicine]-----");
        return medService.deleteMedicine(id);
    }

    @GetMapping("/get-one/{id}")
    @ApiOperation(value = "Получение одного лекарства")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Лекарство найдено"),
            @ApiResponse(code = 400, message = "Вы не ввели id для получения лекарства"),
            @ApiResponse(code = 404, message = "Лекарство не найдено")
    })
    public String getOneMedicine(@PathVariable int id){
        logger.info("-----[GET ONE Medicine]-----");
        Medicines med = medService.findByMedId(id);
        return "Get medicine!\n" + med.getInfo();
    }

    public void getMessages(){
        Subscriber messageSubscriber = this.pubSubTemplate.subscribe(subBoolName, (msg) -> {
            logger.info("Message received from [" + topBoolName + "] for [" + subBoolName + "] subscription: "
                    + msg.getPubsubMessage().getData().toStringUtf8());
            msg.ack();
        });
    }

    public void sendMessages(String message){
        ListenableFuture<String> messageIdFuture = this.pubSubTemplate.publish(topMessageName, message);
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
            this.pubSubTemplate.publish(topIDName, messageId);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
