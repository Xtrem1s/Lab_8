package com.example.servingwebcontent;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@Controller
@RequestMapping("/table_games")
public class TableGameController {
    private final TableGameRepo tableGameRepo;

    public TableGameController(TableGameRepo tableGameRepo) {
        this.tableGameRepo = tableGameRepo;
    }


    @GetMapping()
    public String menu(){
        return "menu";
    }

    @GetMapping("/{id}")
    public String showGame(@PathVariable("id") int id, Model model){
        Optional<TableGame> tg = tableGameRepo.findById(id);
        if (tg.isPresent()) {
            model.addAttribute("game", tg.get());
            return "showGame";
        }

        return "redirect:/table_games";
    }

    @GetMapping("/show")
    public String showGames(Model model){
        model.addAttribute("games", tableGameRepo.findAll());
        return "show";
    }

    @GetMapping("/add")
    public String newGame(Model model){
        model.addAttribute("game", new TableGame());
        return "add";
    }

    @PostMapping("/add")
    public String addGame(@ModelAttribute("game") @Valid TableGame game,
                          BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "add";
        }

        tableGameRepo.save(game);
        return "redirect:/table_games";
    }

    @PostMapping("/{id}/buy")
    public String buyGame(@PathVariable("id") int id, TableGame game){
        return "redirect:/table_games/show";
    }


    @GetMapping("/{id}/edit")
    public String editGame(@PathVariable("id") int id, Model model){
        Optional<TableGame> tg = tableGameRepo.findById(id);
        if (tg.isPresent()){
            model.addAttribute("game", tg.get());
            return "edit";
        }

        return "redirect:/table_games";
    }

    @PostMapping("/{id}/edit")
    public String updateGame(@PathVariable("id") int id,
                             @ModelAttribute("game") @Valid TableGame game,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "edit";
        }
        tableGameRepo.save(game);
        return "redirect:/table_games";
    }

    @PostMapping("delete/{id}")
    public String deleteGame(@PathVariable("id") int id){
        TableGame game;

        Optional<TableGame> tg = tableGameRepo.findById(id);
        if (tg.isPresent()){
            game = tg.get();
            tableGameRepo.deleteById(id);
        }

        return "redirect:/table_games";
    }

    @GetMapping("search")
    public String findGame(@RequestParam("price") int maxPrice, Model model)
    {
        model.addAttribute("games", tableGameRepo.findAllByPriceIsLessThanEqual(maxPrice));

        return "show";
    }

    @GetMapping("find")
    public String showFoundedGames(){
        return "find";
    }
}
