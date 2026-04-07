package auto.trace.mapper;

import auto.trace.dto.response.ExpenseCategoryResponse;
import auto.trace.entity.ExpenseCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseCategoryMapper {
    List<ExpenseCategoryResponse> toResponseList(List<ExpenseCategory> expenseCategoryList);
}
