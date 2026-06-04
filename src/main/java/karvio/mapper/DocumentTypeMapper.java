package karvio.mapper;

import karvio.dto.response.DocumentTypeResponse;
import karvio.entity.DocumentType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentTypeMapper {

    List<DocumentTypeResponse> toResponseList(List<DocumentType> documentTypeList);
}
