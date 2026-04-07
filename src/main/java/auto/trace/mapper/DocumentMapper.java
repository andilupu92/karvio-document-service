package auto.trace.mapper;

import auto.trace.dto.request.DocumentRequest;
import auto.trace.dto.response.DocumentResponse;
import auto.trace.entity.Document;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "documentCategory.id", target = "documentCategoryId")
    @Mapping(source = "documentCategory.name", target = "documentCategoryName")
    @Mapping(source = "documentCategory.iconName", target = "documentCategoryIconName")
    @Mapping(source = "documentCategory.iconLibrary", target = "documentCategoryIconLibrary")
    @Mapping(target = "daysRemaining", expression = "java(calculateDaysRemaining(document.getExpiryDate()))")
    DocumentResponse toResponse(Document document);

    @InheritConfiguration(name = "toResponse")
    List<DocumentResponse> toResponseList(List<Document> documentList);

    Document toEntity(DocumentRequest documentRequest);
    void updateEntityFromRequest(DocumentRequest documentRequest, @MappingTarget Document document);


    default long calculateDaysRemaining(LocalDate expiryDate) {
        return ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
    }
}
