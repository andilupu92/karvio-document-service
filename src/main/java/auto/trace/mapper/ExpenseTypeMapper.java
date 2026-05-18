package auto.trace.mapper;

import auto.trace.dto.response.ExpenseTypeResponse;
import auto.trace.entity.ExpenseType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseTypeMapper {
    List<ExpenseTypeResponse> toResponseList(List<ExpenseType> expenseCategoryList);
}
