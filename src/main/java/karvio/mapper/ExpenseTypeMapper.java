package karvio.mapper;

import karvio.dto.response.ExpenseTypeResponse;
import karvio.entity.ExpenseType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseTypeMapper {
    List<ExpenseTypeResponse> toResponseList(List<ExpenseType> expenseCategoryList);
}
