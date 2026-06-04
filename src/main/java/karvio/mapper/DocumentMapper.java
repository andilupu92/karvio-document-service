package karvio.mapper;

import karvio.dto.request.DocumentRequest;
import karvio.dto.response.DocumentResponse;
import karvio.entity.Document;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "documentType.id", target = "documentTypeId")
    @Mapping(source = "documentType.name", target = "documentTypeName")
    @Mapping(source = "documentType.iconName", target = "documentTypeIconName")
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
