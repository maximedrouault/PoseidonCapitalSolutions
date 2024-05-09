package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * TradeController is a Spring MVC Controller that handles HTTP requests related to Trade.
 * It uses TradeRepository to interact with the database.
 */
@Controller
@RequiredArgsConstructor
public class TradeController {

    /**
     * TradeRepository instance for interacting with the database.
     */
    private final TradeRepository tradeRepository;


    /**
     * Handles the request to get the list of all Trades.
     * @param model the Model instance
     * @param principal the Principal instance
     * @return the view name
     */
    @RequestMapping("/trade/list")
    public String home(Model model, Principal principal)
    {
        model.addAttribute("username", principal.getName());
        model.addAttribute("trades", tradeRepository.findAll());

        return "trade/list";
    }

    /**
     * Handles the request to show the form for adding a new Trade.
     * @param trade the Trade instance
     * @return the view name
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }

    /**
     * Handles the request to validate and save a new Trade.
     * @param trade the Trade instance
     * @param result the BindingResult instance
     * @param model the Model instance
     * @return the view name
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            tradeRepository.save(trade);
            model.addAttribute("trades", tradeRepository.findAll());

            return "redirect:/trade/list";
        }

        return "trade/add";
    }

    /**
     * Handles the request to show the form for updating a Trade.
     * @param id the id of the Trade
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        model.addAttribute("trade", trade);

        return "trade/update";
    }

    /**
     * Handles the request to update a Trade.
     * @param id the id of the Trade
     * @param trade the Trade instance
     * @param result the BindingResult instance
     * @param model the Model instance
     * @return the view name
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }

        trade.setId(id);
        tradeRepository.save(trade);
        model.addAttribute("trades", tradeRepository.findAll());

        return "redirect:/trade/list";
    }

    /**
     * Handles the request to delete a Trade.
     * @param id the id of the Trade
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeRepository.delete(trade);
        model.addAttribute("trades", tradeRepository.findAll());

        return "redirect:/trade/list";
    }
}
