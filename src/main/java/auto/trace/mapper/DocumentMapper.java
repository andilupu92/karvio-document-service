package auto.trace.mapper;

import auto.trace.dto.request.DocumentRequest;
import auto.trace.dto.response.DocumentResponse;
import auto.trace.entity.Document;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "documentCategory.id", target = "documentCategoryId")
    @Mapping(source = "documentCategory.name", target = "documentCategoryName")
    DocumentResponse toResponse(Document document);

    @InheritConfiguration(name = "toResponse")
    List<DocumentResponse> toResponseList(List<Document> documentList);

    Document toEntity(DocumentRequest documentRequest);
    void updateEntityFromRequest(DocumentRequest documentRequest, @MappingTarget Document document);
}
