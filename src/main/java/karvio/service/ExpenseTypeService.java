package karvio.service;

import karvio.dto.response.ExpenseTypeResponse;
import karvio.mapper.ExpenseTypeMapper;
import karvio.repository.ExpenseTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseTypeService {

    private final ExpenseTypeRepository expenseTypeRepository;
    private final ExpenseTypeMapper expenseTypeMapper;

    public ExpenseTypeService(ExpenseTypeRepository expenseTypeRepository, ExpenseTypeMapper expenseTypeMapper) {
        this.expenseTypeRepository = expenseTypeRepository;
        this.expenseTypeMapper = expenseTypeMapper;
    }


    public List<ExpenseTypeResponse> getExpenseTypes() {
        return expenseTypeMapper.toResponseList(expenseTypeRepository.findAll());
    }
}
