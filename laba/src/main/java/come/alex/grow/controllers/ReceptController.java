package come.alex.grow.controllers;

import come.alex.grow.entity.Medicines;
import come.alex.grow.entity.Patients;
import come.alex.grow.entity.Recepts;
import come.alex.grow.services.RecService;
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
@RequestMapping("/recepts")
public class ReceptController {

    private static final Logger logger = LoggerFactory.getLogger(ReceptController.class);

    @Autowired
    private RecService recService;
    public List<Recepts> recep = new ArrayList<>();

    @GetMapping("/get-all")
    @ApiOperation(value = "Получение всех рецептов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Рецепты найдены"),
            @ApiResponse(code = 404, message = "Рецепты не найдены")
    })
    public String getAllRecepts(){
        logger.info("-----[GET ALL Recepts]-----");
        recep = recService.getRecepts();
        String s = "";
        for(Recepts r : recep){
            s += r.getInfo() + "\n";
        }
        return "Get all recepts!\n" + s;
    }

    @PostMapping("/add")
    @ApiOperation(value = "Добавление рецепта")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Рецепт добавлен"),
            @ApiResponse(code = 400, message = "Вы не ввели данные для добавления рецепта"),
            @ApiResponse(code = 404, message = "Рецепт не добавлен")
    })
    public String createRecept(@RequestParam Medicines med_id, @RequestParam Patients pat_id, @RequestParam String sign){
        logger.info("-----[CREATE Recept]-----");
        Recepts rec = new Recepts(med_id, pat_id, sign);
        Recepts r = recService.saveRecept(rec);
        String id = String.valueOf(rec.getId());
        return "Successfully create! with id = " + id + "\n" + r.getInfo();
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Обновление рецепта")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Рецепт обновлен"),
            @ApiResponse(code = 400, message = "Вы не ввели данные для обновления рецепта"),
            @ApiResponse(code = 404, message = "Рецепт не обновлен")
    })
    public String putRecept(@PathVariable int id, @RequestParam Medicines med_id, @RequestParam Patients pat_id, @RequestParam String sign){
        logger.info("-----[UPDATE Recept]-----");
        Recepts rec = recService.findByRecId(id);
        rec.setMedicines(med_id);
        rec.setPatients(pat_id);
        rec.setSignature(sign);
        Recepts r = recService.saveRecept(rec);
        return "Successfully update!\n" + r.getInfo();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Удаление рецепта")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Рецепт удален"),
            @ApiResponse(code = 400, message = "Вы не ввели id для удаления рецепта"),
            @ApiResponse(code = 404, message = "Рецепт не удален")
    })
    public String deleteRecept(@PathVariable int id){
        logger.info("-----[DELETE Recept]-----");
        return recService.deleteRecept(id);
    }

    @GetMapping("/get-one/{id}")
    @ApiOperation(value = "Получение одного рецепта")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Рецепт найден"),
            @ApiResponse(code = 400, message = "Вы не ввели id для получения рецепта"),
            @ApiResponse(code = 404, message = "Рецепт не найден")
    })
    public String getOneRecept(@PathVariable int id){
        logger.info("-----[GET ONE Recept]-----");
        Recepts rec = recService.findByRecId(id);
        return "Get recepts!\n" + rec.getInfo();
    }
}
