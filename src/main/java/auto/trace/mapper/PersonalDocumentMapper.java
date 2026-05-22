package auto.trace.mapper;

import auto.trace.dto.request.DocumentPersonalRequest;
import auto.trace.dto.response.PersonalDocumentResponse;
import auto.trace.entity.PersonalDocument;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonalDocumentMapper {

    @Mapping(source = "documentType.id", target = "documentTypeId")
    @Mapping(source = "documentType.name", target = "documentTypeName")
    @Mapping(source = "documentType.iconName", target = "documentTypeIconName")
    @Mapping(target = "daysRemaining", expression = "java(calculateDaysRemaining(personalDocument.getExpiryDate()))")
    PersonalDocumentResponse toResponse(PersonalDocument personalDocument);

    @InheritConfiguration(name = "toResponse")
    List<PersonalDocumentResponse> toResponseList(List<PersonalDocument> personalDocumentList);

    PersonalDocument toEntity(DocumentPersonalRequest personalDocumentRequest);
    void updateEntityFromRequest(DocumentPersonalRequest personalDocumentRequest, @MappingTarget PersonalDocument personalDocument);

    default long calculateDaysRemaining(LocalDate expiryDate) {
        return ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
    }
}
