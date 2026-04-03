package auto.trace.mapper;

import auto.trace.dto.DocumentCategoryResponse;
import auto.trace.entity.DocumentCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentCategoryMapper {
    List<DocumentCategoryResponse> toResponseList(List<DocumentCategory> documentCategoryList);
}
