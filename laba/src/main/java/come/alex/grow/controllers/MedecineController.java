package come.alex.grow.controllers;

import come.alex.grow.entity.Medicines;
import come.alex.grow.services.MedService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/medicines")
public class MedecineController {
    private static final Logger logger = LoggerFactory.getLogger(MedecineController.class);
    @Autowired
    private MedService medService;
    public List<Medicines> medic = new ArrayList<>();

    @GetMapping("/get-all")
    @ApiOperation(value = "Получение всех лекарств")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Лекарства найдены"),
            @ApiResponse(code = 404, message = "Лекарства не найдены")
    })
    public String getAllMedicines(){
        logger.info("-----[GET ALL Medicines]-----");
        medic = medService.getMedicines();
        String s = "";
        for(Medicines m : medic){
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
        med.getName();
        med.getDate();
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
}
