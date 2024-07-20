package healli.foodie.category.controller;

import healli.foodie.category.domain.Category;
import healli.foodie.category.dto.CreateAndUpdateCategoryRequest;
import healli.foodie.category.dto.UpdateSaleStatusRequest;
import healli.foodie.category.service.CategoryService;
import healli.foodie.common.http.SuccessfulResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController()
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    @ResponseBody
    public SuccessfulResponse create(@RequestBody @Valid CreateAndUpdateCategoryRequest request) {

        Category category = this.categoryService.create(request);
        SuccessfulResponse response = new SuccessfulResponse(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Category created successfully."
        );
        response.setProperty("Data", category);
        return response;
    }

    @DeleteMapping(value = "{id}")
    @ResponseBody
    public SuccessfulResponse delete(@PathVariable("id") String id) {

        this.categoryService.delete(id);
        SuccessfulResponse response = new SuccessfulResponse(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Category deleted successfully."
        );
        return response;
    }

    @PatchMapping(value = "{id}/sale-status")
    @ResponseBody
    public SuccessfulResponse updateSaleStatus(@PathVariable("id") String id, @RequestBody UpdateSaleStatusRequest request) {

        request.setId(id);
        Category category = this.categoryService.updateSaleStatus(request);
        SuccessfulResponse response = new SuccessfulResponse(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Category updated successfully."
        );
        response.setProperty("Data", category);
        return response;
    }
}