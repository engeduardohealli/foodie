package healli.foodie.category.service;

import healli.foodie.category.domain.Category;
import healli.foodie.category.dto.CreateRequest;
import healli.foodie.category.dto.UpdateDetailsRequest;
import healli.foodie.category.dto.UpdateSaleStatusRequest;
import healli.foodie.category.event.CategoryCreatedEvent;
import healli.foodie.category.event.CategoryDeletedEvent;
import healli.foodie.category.event.CategoryDetailsUpdatedEvent;
import healli.foodie.category.event.CategorySaleStatusUpdatedEvent;
import healli.foodie.category.repository.CategoryRepository;
import healli.foodie.common.validation.EntityAttributeError;
import healli.foodie.common.validation.EntityAttributeNotValidException;
import healli.foodie.owner.model.repository.OwnerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    public Category create(CreateRequest request) {

        List<EntityAttributeError> errors = new ArrayList<>();

        String title = request.getTitle();
        Example<Category> categoryExample = Example.of(Category.builder().title(title).build());
        if (this.categoryRepository.exists(categoryExample)) {
            errors.add(
                    new EntityAttributeError(
                            "Category",
                            "title",
                            title,
                            "Category with the given name already exists."
                    )
            );
        }

        String ownerId = request.getOwnerId();
        if (!this.ownerRepository.existsById(ownerId)) {
            errors.add(
                    new EntityAttributeError(
                            "Category",
                            "ownerId",
                            ownerId,
                            "Invalid owner id."
                    )
            );
        }

        if (!errors.isEmpty()) {
            throw new EntityAttributeNotValidException(errors);
        }

        Category category = this.categoryRepository.save(
                Category
                        .builder()
                        .title(title)
                        .description(request.getDescription())
                        .isSaleActive(request.getIsSaleActive())
                        .ownerId(ownerId)
                        .build());

        this.eventPublisher.publishEvent(
                new CategoryCreatedEvent(category.getId())
        );
        return category;
    }


    public void delete(String id) {

        Optional<Category> optionalCategory = this.categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new EntityAttributeNotValidException(
                    new EntityAttributeError("Category", "id", id, "Invalid category id.")
            );
        }
        this.categoryRepository.delete(optionalCategory.get());
        this.eventPublisher.publishEvent(
                new CategoryDeletedEvent(id)
        );
    }

    public Category updateDetails(UpdateDetailsRequest request) {

        List<EntityAttributeError> errors = new ArrayList<>();

        String id = request.getId();
        Optional<Category> optionalCategory = this.categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new EntityAttributeNotValidException(
                    new EntityAttributeError(
                            "Category",
                            "id",
                            id,
                            "Invalid category id."
                    )
            );
        }

        Category category = optionalCategory.get();
        if (!category.getTitle().equals(request.getTitle())) {
            if (
                    this.categoryRepository.exists(
                            Example.of(
                                    Category.builder().title(request.getTitle()).build()
                            )
                    )
            ) {
                errors.add(
                        new EntityAttributeError(
                                "Category",
                                "title",
                                request.getTitle(),
                                "Category with the given name already exists."
                        )
                );
            }
        }

        if (!this.ownerRepository.existsById(request.getOwnerId())) {
            errors.add(
                    new EntityAttributeError(
                            "Category",
                            "ownerId",
                            request.getOwnerId(),
                            "Invalid owner id."
                    )
            );
        }

        if (!errors.isEmpty()) {
            throw new EntityAttributeNotValidException(errors);
        }

        BeanUtils.copyProperties(request, category);
        this.eventPublisher.publishEvent(
                new CategoryDetailsUpdatedEvent(id)
        );
        return this.categoryRepository.save(category);
    }

    public Category updateSaleStatus(UpdateSaleStatusRequest request) {

        String id = request.getId();
        Optional<Category> optionalCategory = this.categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new EntityAttributeNotValidException(
                    new EntityAttributeError("Category", "id", id, "Invalid category id.")
            );
        }

        Category category = optionalCategory.get();
        category.setIsSaleActive(request.getIsSaleActive());
        this.eventPublisher.publishEvent(
                new CategorySaleStatusUpdatedEvent(category.getId())
        );
        return this.categoryRepository.save(category);
    }
}
