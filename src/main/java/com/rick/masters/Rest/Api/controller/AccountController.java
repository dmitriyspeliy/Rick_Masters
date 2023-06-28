package com.rick.masters.Rest.Api.controller;

import com.rick.masters.Rest.Api.domain.record.AccountRecord;
import com.rick.masters.Rest.Api.service.AccountService;
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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Контроллер для работы с аккаунтом
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Validated
@Tag(name = "Аккаунт")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Положить деньги")
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
    @GetMapping("/{id}/put_money")
    public void putMoney(
            @PathVariable(name = "id")
            @NotNull
            @Min(value = 1, message = "Идентификатор не должен быть меньше 1")
            @Parameter(description = "Идентификатор водителя",
                    example = "2")
            Long id,
            @Parameter(description = "Цвет валюты",
                    example = "Красный, Зеленый, Голубой")
            @RequestParam(value = "color")
            @NotNull(message = "Обязательно нужно заполнить поле")
            @Size(min = 5, max = 10, message
                    = "Название должно быть от 5 до 10 символов") String color,
            @Parameter(description = "Количество валюты",
                    example = "20")
            @RequestParam(value = "value")
            @NotNull(message = "Обязательно нужно заполнить поле")
            @Min(value = 0, message = "меньше 0 значение не может быть")
            Long value
    ) {
        accountService.putMoney(color, value, id);
    }

    @Operation(summary = "Cписать деньги")
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
    @GetMapping("/{id}/write_off")
    public void writeOff(
            @PathVariable(name = "id")
            @NotNull
            @Min(value = 1, message = "Идентификатор не должен быть меньше 1")
            @Parameter(description = "Идентификатор водителя",
                    example = "2")
            Long id,
            @Parameter(description = "Цвет валюты",
                    example = "Красный, Зеленый, Голубой")
            @RequestParam(value = "color")
            @NotNull(message = "Обязательно нужно заполнить поле")
            @Size(min = 5, max = 10, message
                    = "Название должно быть от 5 до 10 символов") String color,
            @Parameter(description = "Количество валюты",
                    example = "20")
            @RequestParam(value = "value")
            @NotNull(message = "Обязательно нужно заполнить поле")
            @Min(value = 0, message = "меньше 0 значение не может быть")
            Long value
    ) {
        accountService.writeOff(color, value, id);
    }

    @Operation(summary = "Посмотреть счет")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен",
                    content = {
                            @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = AccountRecord.class)))
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
    @GetMapping("/{id}/show_account")
    public ResponseEntity<AccountRecord> showAccount(
            @PathVariable(name = "id")
            @NotNull
            @Min(value = 1, message = "Идентификатор не должен быть меньше 1")
            @Parameter(description = "Идентификатор водителя",
                    example = "2")
            Long id
    ) {
        return ResponseEntity.ok(accountService.showAccount(id));
    }

}
