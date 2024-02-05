package com.kyunashi.gameshow.controller;

import com.kyunashi.gameshow.service.RallyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/rally")
public class RallyController {

    private RallyService rallyService;


    @GetMapping("/start")
    public void startRally(){

    }


}
