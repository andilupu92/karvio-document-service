package auto.trace.service;

import auto.trace.dto.response.ExpenseCategoryResponse;
import auto.trace.mapper.ExpenseCategoryMapper;
import auto.trace.repository.ExpenseCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseCategoryService {

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpenseCategoryMapper expenseCategoryMapper;

    public ExpenseCategoryService(ExpenseCategoryRepository expenseCategoryRepository, ExpenseCategoryMapper expenseCategoryMapper) {
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.expenseCategoryMapper = expenseCategoryMapper;
    }

    public List<ExpenseCategoryResponse> getExpenseCategories() {
        return expenseCategoryMapper.toResponseList(expenseCategoryRepository.findAll());
    }
}
