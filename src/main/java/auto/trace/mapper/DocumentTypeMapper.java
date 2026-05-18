package auto.trace.mapper;

import auto.trace.dto.response.DocumentTypeResponse;
import auto.trace.entity.DocumentType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentTypeMapper {

    List<DocumentTypeResponse> toResponseList(List<DocumentType> documentTypeList);
}
