package com.deblauwe.examples.shoelacethymeleafalpine;

import io.github.wimdeblauwe.hsbt.mvc.HxRequest;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

@Controller
@RequestMapping("/")
public class HomeController {

    private static final RandomGenerator RANDOM_GENERATOR = RandomGeneratorFactory.getDefault().create();

    private final MessageSource messageSource;

    public HomeController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping("/purchase")
    @HxRequest
    public String purchase(Model model,
                           Locale locale) {

        model.addAttribute("itemName", messageSource.getMessage("item.black.tee", null, locale));

        int randomInt = RANDOM_GENERATOR.nextInt(3);
        return switch (randomInt) {
            case 0 -> {
                // simulate a succesful purchase
                yield "fragments/toasts :: itemAdded";
            }
            case 1 -> {
                // simulate a succesful purchase where we see that the user is
                // close to enough purchases to avoid the shipping fee
                model.addAttribute("amountToSpend", "$" + RANDOM_GENERATOR.nextInt(10, 20));
                yield "fragments/toasts :: itemAddedNearShipping";
            }
            default -> {
                // simulate an error happened. In a real application this would probably in a catch block.
                yield "fragments/toasts :: error";
            }
        };
    }
}
