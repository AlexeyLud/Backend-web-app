package come.alex.grow.controllers;

import come.alex.grow.entity.Patients;
import come.alex.grow.services.PatService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatService patService;
    public List<Patients> patients = new ArrayList<>();

    @GetMapping("/get-all")
    @ApiOperation(value = "Получение всех пациентов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пациенты найдены"),
            @ApiResponse(code = 404, message = "Пациенты не найдены")
    })
    public String getAllPatients(){
        logger.info("-----[GET ALL Patients]-----");
        patients = patService.getPatients();
        String s = "";
        for(Patients p : patients){
            s += p.getInfo() + "\n";
        }
        return "Get all patients!\n" + s;
    }

    @PostMapping("/add")
    @ApiOperation(value = "Добавление пациента")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пациент добавлен"),
            @ApiResponse(code = 400, message = "Вы не ввели данные для добавления пациента"),
            @ApiResponse(code = 404, message = "Пациент не добавлен")
    })
    public String createPatient(@RequestParam String name, @RequestParam String surname, @RequestParam int age){
        logger.info("-----[CREATE Patient]-----");
        Patients pat = new Patients(name, surname, age);
        Patients p = patService.savePatient(pat);
        String id = String.valueOf(pat.getId_pat());
        return "Successfully create! with id = " + id + "\n" + p.getInfo();
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Обновление пациента")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пациент обновлен"),
            @ApiResponse(code = 400, message = "Вы не ввели данные для обновления пациента"),
            @ApiResponse(code = 404, message = "Пациент не обновлен")
    })
    public String putPatient(@PathVariable int id, @RequestParam String name, @RequestParam String surname, @RequestParam int age){
        logger.info("-----[UPDATE Patient]-----");
        Patients pat = patService.findByPatId(id);
        pat.setName(name);
        pat.setSurname(surname);
        pat.setAge(age);
        Patients p = patService.savePatient(pat);
        return "Successfully update!\n" + p.getInfo();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Удаление пациента")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пациент удален"),
            @ApiResponse(code = 400, message = "Вы не ввели id для удаления пациента"),
            @ApiResponse(code = 404, message = "Пациент не удален")
    })
    public String deletePatient(@PathVariable int id){
        logger.info("-----[DELETE Patient]-----");
        return patService.deletePatient(id);
    }

    @GetMapping("/get-one/{id}")
    @ApiOperation(value = "Получение одного пациента")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пациент найден"),
            @ApiResponse(code = 400, message = "Вы не ввели id для получения пациента"),
            @ApiResponse(code = 404, message = "Пациент не найден")
    })
    public String getOnePatient(@PathVariable int id){
        logger.info("-----[GET ONE Patient]-----");
        Patients pat = patService.findByPatId(id);
        return "Get patient!\n" + pat.getInfo();
    }
}
