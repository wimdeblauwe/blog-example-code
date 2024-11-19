package com.wimdeblauwe.examples.redirect_attributes.product.web;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRedirectView;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

  private final ProductRepository productRepository;

  public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping
  public String index(Model model) {
    model.addAttribute("products", productRepository.findAll());
    return "products/index";
  }

  @GetMapping("/new")
  public String newProduct(Model model) {
    model.addAttribute("formData", new CreateProductFormData());

    return "products/edit";
  }

  @PostMapping("/new")
  public String create(@ModelAttribute("formData") CreateProductFormData formData,
      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      return "products/edit";
    }

    productRepository.save(formData.toProduct());

    redirectAttributes.addFlashAttribute("message", "Product created");
    return "redirect:/products";
  }

  @HxRequest
  @DeleteMapping("/{id}")
  public String/*HtmxRedirectView*/ delete(@PathVariable("id") UUID id, RedirectAttributes redirectAttributes) {
    productRepository.deleteById(id);

    redirectAttributes.addFlashAttribute("message", "Product deleted");
    return "redirect:htmx:/products";
    //return new HtmxRedirectView("/products");
  }
}
