package kr.musinsa.domain.item.model.enums

enum class ItemCategory(
    val value: String,
    val desc: String,
) {
    TOP("top", "상의"),
    OUTER("outer", "아우터"),
    BOTTOM("bottom", "바지"),
    SNEAKERS("sneakers", "스니커즈"),
    BAG("bag", "가방"),
    HAT("hat", "모자"),
    SOCKS("socks", "양말"),
    ACCESSORY("accessory", "악세서리");

    companion object {
        fun from(data: String?): ItemCategory? {
            return values().find { it.value == data }
        }
    }
}
