package healli.foodie.category.controller;

import healli.foodie.category.domain.Category;
import healli.foodie.category.repository.CategoryRepository;
import healli.foodie.category.service.CategoryService;
import healli.foodie.owner.model.Owner;
import healli.foodie.owner.model.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeAll
    static void setupBeforeAll() {

        final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));
        mongoDBContainer.start();
    }

    @Test
    void givenDuplicateTitle_whenUpdateDetails_thenReturn400() throws Exception {

        Owner owner = ownerRepository.save(new Owner());
        this.categoryRepository.save(
                Category
                        .builder()
                        .title("title")
                        .description("description")
                        .isSaleActive(false)
                        .ownerId(owner.getId())
                        .build()
        );
        Category category = this.categoryRepository.save(
                Category
                        .builder()
                        .title("title 2")
                        .description("description")
                        .isSaleActive(false)
                        .ownerId(owner.getId())
                        .build()
        );
        category.setTitle("title");
        mockMvc
                .perform(
                        patch("/api/category/{id}", category.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(category))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenInvalidCategoryId_whenUpdateDetails_thenReturns400() throws Exception {

        Category category = Category
                .builder()
                .id("invalid id")
                .title("title 2")
                .description("description")
                .isSaleActive(false)
                .ownerId("owner id")
                .build();
        mockMvc
                .perform(
                        patch("/api/category/{id}", "invalid id")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(category))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenInvalidOwnerId_whenUpdateDetails_thenReturns400() throws Exception {

        Owner owner = ownerRepository.save(new Owner());
        Category category = this.categoryRepository.save(
                Category
                        .builder()
                        .title("title")
                        .description("description")
                        .isSaleActive(false)
                        .ownerId(owner.getId())
                        .build()
        );
        category.setOwnerId("invalid id");
        mockMvc
                .perform(
                        patch("/api/category/{id}", category.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(category))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenSameTitle_whenUpdateDetails_thenReturns200() throws Exception {

        Owner owner = ownerRepository.save(new Owner());
        Category category = this.categoryRepository.save(
                Category
                        .builder()
                        .title("title")
                        .description("description")
                        .isSaleActive(false)
                        .ownerId(owner.getId())
                        .build()
        );
        mockMvc
                .perform(
                        patch("/api/category/{id}", category.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(category))
                )
                .andExpect(status().isOk());
    }
}