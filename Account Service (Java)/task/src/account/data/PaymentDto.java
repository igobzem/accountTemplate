package account.data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PaymentDto (@NotEmpty String employee,
                          @NotEmpty String period,
                          @NotNull @Min(0) Long salary) {}

