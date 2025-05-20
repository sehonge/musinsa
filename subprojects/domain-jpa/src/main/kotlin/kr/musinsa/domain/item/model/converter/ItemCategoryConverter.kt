package kr.musinsa.domain.item.model.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.musinsa.domain.item.model.enums.ItemCategory

@Converter
class ItemCategoryConverter : AttributeConverter<ItemCategory, String> {
    override fun convertToDatabaseColumn(attribute: ItemCategory): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String?): ItemCategory {
        return ItemCategory.from(dbData) ?: throw EnumConstantNotPresentException(ItemCategory::class.java, dbData)
    }
}
