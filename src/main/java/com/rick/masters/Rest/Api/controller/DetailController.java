package com.rick.masters.Rest.Api.controller;

import com.rick.masters.Rest.Api.domain.record.DetailRecord;
import com.rick.masters.Rest.Api.service.DetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Контроллер для работы с деталями
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/detail")
@Validated
@Tag(name = "Деталь")
public class DetailController {

    private final DetailService detailService;

    @Operation(summary = "Сохранение деталей")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны (например, база данных недоступна)"
            )
    })
    @PostMapping
    public void saveDetail(@Valid @RequestBody DetailRecord detailRecord) {
        detailService.saveDetail(detailRecord);
    }

    @Operation(summary = "Установка детали в автомобиль по VIN")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны (например, база данных недоступна)"
            )
    })
    @PostMapping("/set_detail_vin")
    public void setDetailToVehicleByVIN(
            @Valid @RequestBody DetailRecord detailRecord,
            @Parameter(description = "VIN автомобиля")
            @RequestParam(value = "vin")
            @NotNull(message = "Не может быть null")
            @Pattern(regexp = "^[A-Z0-9]{17}", message = "Идентификационный номер транспортного средства") String VIN) {
        detailService.saveDetailByVIN(detailRecord, VIN);
    }

    @Operation(summary = "Установка детали в автомобиль по Id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны (например, база данных недоступна)"
            )
    })
    @PostMapping("/{id}/set_detail")
    public void setDetailToVehicleByVIN(
            @PathVariable(name = "id")
            @Min(value = 1, message = "Идентификатор не должен быть меньше 1")
            @Parameter(description = "Идентификатор водителя",
                    example = "2")
            Long id,
            @Valid @RequestBody DetailRecord detailRecord) {
        detailService.saveDetailByVehicleId(detailRecord, id);
    }

    @Operation(summary = "Замена детали в машине")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны (например, база данных недоступна)"
            )
    })
    @GetMapping("change_detail")
    public void changeDetail(
            @Parameter(description = "Серийный старой детали детали")
            @RequestParam(value = "detailOld")
            @NotNull(message = "Не может быть null")
            @Size(min = 10, max = 10, message = "Серийный номер, например 2LDW90PWQV")
            @Pattern(regexp = "^[A-Z0-9]{10}", message = "Неправильный номер, например 2LDW90PWQV")
            String serialNumberDetailOld,
            @Parameter(description = "Серийный детали на замену детали")
            @RequestParam(value = "detailNew")
            @NotNull(message = "Не может быть null")
            @Size(min = 10, max = 10, message = "Серийный номер, например 2LDW90PWQV")
            @Pattern(regexp = "^[A-Z0-9]{10}", message = "Неправильный номер, например 2LDW90PWQV")
            String serialNumberDetailNew) {
        detailService.changeDetail(serialNumberDetailOld, serialNumberDetailNew);
    }
}
