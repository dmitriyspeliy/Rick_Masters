package com.rick.masters.Rest.Api.controller;


import com.rick.masters.Rest.Api.domain.record.DriverRecord;
import com.rick.masters.Rest.Api.service.DriverService;
import com.rick.masters.Rest.Api.utils.DriverSort;
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
import javax.validation.constraints.Size;
import java.util.Collection;


/**
 * Контроллер для работы с водителями
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/driver")
@Validated
@Tag(name = "Водитель")
public class DriverController {

    private final DriverService driverService;

    @Operation(summary = "Сохранить водителя")
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
    public void saveDriver(@Valid @RequestBody DriverRecord driverRecord) {
        driverService.saveDriver(driverRecord);
    }

    @Operation(summary = "Сортировка и пагинация всех водителей")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен, ответ в виде массива дто",
                    content = {
                            @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = DriverRecord.class)))
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
    public ResponseEntity<Collection<DriverRecord>> getAllDrivers(
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
            DriverSort sortedBy) {
        return ResponseEntity.ok(driverService.getAllDrivers(offSet, limit, sortedBy));
    }

    @Operation(summary = "Поиск водителя по паспорту")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен",
                    content = {
                            @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = DriverRecord.class)))
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
    @GetMapping("by_passport_id")
    public ResponseEntity<DriverRecord> findDriverByPassportId(
            @Parameter(description = "Серия и номер паспорта")
            @RequestParam(value = "passportId")
            @NotNull(message = "Не может быть null")
            @Size(min = 10, max = 10, message = "Серия и номер паспорта, например 1234111111")
            String passportId
    ) {
        return ResponseEntity.ok(driverService.findDriverByPassportId(passportId));
    }


}
