package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
 * BidListController is a Spring MVC Controller that handles HTTP requests related to BidList.
 * It uses BidListRepository to interact with the database.
 */
@Controller
@RequiredArgsConstructor
public class BidListController {

    /**
     * BidListRepository instance for interacting with the database.
     */
    private final BidListRepository bidListRepository;


    /**
     * Handles the request to get the list of all BidLists.
     * @param model the Model instance
     * @param principal the Principal instance
     * @return the view name
     */
    @RequestMapping("/bidList/list")
    public String home(Model model, Principal principal)
    {
        model.addAttribute("username", principal.getName());
        model.addAttribute("bidLists", bidListRepository.findAll());

        return "bidList/list";
    }

    /**
     * Handles the request to show the form for adding a new BidList.
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidList", BidList.builder().build());

        return "bidList/add";
    }

    /**
     * Handles the request to validate and save a new BidList.
     * @param bid the BidList instance
     * @param result the BindingResult instance
     * @param model the Model instance
     * @return the view name
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListRepository.save(bid);
            model.addAttribute("bidLists", bidListRepository.findAll());

            return "redirect:/bidList/list";
        }

        return "bidList/add";
    }

    /**
     * Handles the request to show the form for updating a BidList.
     * @param id the id of the BidList
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        model.addAttribute("bidList", bidList);

        return "bidList/update";
    }

    /**
     * Handles the request to update a BidList.
     * @param id the id of the BidList
     * @param bidList the BidList instance
     * @param result the BindingResult instance
     * @param model the Model instance
     * @return the view name
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }

        bidList.setId(id);
        bidListRepository.save(bidList);
        model.addAttribute("bidLists", bidListRepository.findAll());

        return "redirect:/bidList/list";
    }

    /**
     * Handles the request to delete a BidList.
     * @param id the id of the BidList
     * @param model the Model instance
     * @return the view name
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        bidListRepository.delete(bidList);
        model.addAttribute("bidLists", bidListRepository.findAll());

        return "redirect:/bidList/list";
    }
}
