package karvio.mapper;

import karvio.dto.request.ExpenseRequest;
import karvio.dto.response.ExpenseResponse;
import karvio.dto.response.ExpenseToCarResponse;
import karvio.entity.Expense;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(source = "expenseType.id", target = "expenseTypeId")
    @Mapping(source = "expenseType.name", target = "expenseTypeName")
    @Mapping(source = "expenseType.iconName", target = "expenseTypeIconName")
    ExpenseResponse toResponse(Expense expense);

    @InheritConfiguration(name = "toResponse")
    List<ExpenseResponse> toResponseList(List<Expense> expenseList);

    List<ExpenseToCarResponse> toCarResponseList(List<Expense> expenseList);

    Expense toEntity(ExpenseRequest expenseRequest);

}
