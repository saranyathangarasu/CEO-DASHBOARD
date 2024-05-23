package application.Dto;

import jakarta.validation.constraints.NotNull;

public class CategoryDto {

    private Long id;

    @NotNull
    private String category;

    public CategoryDto() {
        super();
    }

    public CategoryDto(Long id, @NotNull String category) {
        super();
        this.id = id;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
