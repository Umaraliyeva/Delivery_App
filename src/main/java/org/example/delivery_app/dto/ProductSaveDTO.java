package org.example.delivery_app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaveDTO {
    @NotNull(message = "Nomi bo‘sh bo‘lishi mumkin emas!")
    private String name;

    @Min(value = 0, message = "Narx manfiy bo‘lmasligi kerak!")
    private Integer price;

    @NotNull(message = "Kategoriya ID'si kiritilishi shart!")
    private Integer categoryId;

    @NotNull(message = "Rasm ID'si kiritilishi shart!")
    private Integer attachmentId;
}
