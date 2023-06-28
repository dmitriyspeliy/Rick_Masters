package com.rick.masters.Rest.Api.controller;


import com.rick.masters.Rest.Api.domain.record.VehicleRecord;
import com.rick.masters.Rest.Api.service.VehicleService;
import com.rick.masters.Rest.Api.utils.VehicleSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Контроллер для работы с транспортом
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicle")
@Validated
@Tag(name = "Транспорт")
public class VehicleController {
    private final VehicleService vehicleService;

    @Operation(summary = "Сохранение автомобиля")
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
    public void saveDriver(@Valid VehicleRecord vehicleRecord) {
        vehicleService.saveVehicle(vehicleRecord);
    }

    @Operation(summary = "Сортировка и пагинация всех автомобилей")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен, ответ в виде массива дто",
                    content = {
                            @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = VehicleRecord.class)))
                    }
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
    @GetMapping("get_all")
    public ResponseEntity<Collection<VehicleRecord>> getAllVehicle(
            @Min(value = 1, message = "Количество страниц не должен быть меньше 1")
            @Parameter(description = "Количество страниц",
                    example = "2")
            @NotNull
            @RequestParam(value = "offSet")
            Integer offSet,
            @Min(value = 1, message = "Количество элементов на странице не должно быть меньше 1")
            @Parameter(description = "Количество элементов на странице",
                    example = "2")
            @NotNull
            @RequestParam(value = "limit")
            Integer limit,
            @NotNull
            @Parameter(description = "По какому полю сортируем",
                    example = "2")
            @RequestParam(value = "sort")
            VehicleSort sortedBy) {
        return ResponseEntity.ok(vehicleService.getAllVehicle(offSet, limit, sortedBy));
    }

    @Operation(summary = "Установка владение автомобилем")
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
    @GetMapping("set_driver")
    public void setDriverToVehicle(
            @RequestParam(name = "driverId")
            @NotNull
            @Min(value = 1, message = "Идентификатор не должен быть меньше 1")
            @Parameter(description = "Идентификатор водителя",
                    example = "2")
            Long driverId,
            @RequestParam(name = "vehicleId")
            @NotNull
            @Min(value = 1, message = "Идентификатор не должен быть меньше 1")
            @Parameter(description = "Идентификатор транспортного средства",
                    example = "2")
            Long vehicleId
    ) {
        vehicleService.setDriverToVehicle(driverId, vehicleId);
    }
}
