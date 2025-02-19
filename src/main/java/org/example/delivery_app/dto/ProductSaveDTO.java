package org.example.delivery_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaveDTO {
    private String name;
    private Integer price;
    private Integer attachmentId;
    private Integer categoryId;
}
