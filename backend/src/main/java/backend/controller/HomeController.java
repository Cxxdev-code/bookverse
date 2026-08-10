package backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.Response.HomeResponse;
import backend.service.HomeService;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/home")
@AllArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping
    public HomeResponse carregarHome() {
        return homeService.carregarHome();
    }
}
