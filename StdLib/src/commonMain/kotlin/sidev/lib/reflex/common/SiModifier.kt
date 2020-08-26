package sidev.lib.reflex.common

enum class SiModifier(val id: Int) {
    OPEN(2),
    MUTABLE(4), //Untuk kasus field, khususnya pada Java dg flag `final`.
    OVERRIDE(8),
    ABSTRACT(16),
    SEALED(32),
    DYNAMIC(64),
    OPTIONAL(128),
    VARARG(256),
    ;

    companion object{
        fun hasModifier(unit: SiDescriptorContainer, modifier: SiModifier): Boolean = (unit.descriptor.modifier and modifier.id) != 0
        fun setModifier(unit: SiDescriptorContainer, modifier: SiModifier){
            (unit.descriptor as SiDescriptorImpl).modifier = unit.descriptor.modifier or modifier.id
        }
        fun isAbstract(unit: SiDescriptorContainer): Boolean = hasModifier(unit, ABSTRACT)
        fun isSealed(unit: SiDescriptorContainer): Boolean = hasModifier(unit, SEALED)
        fun isMutable(unit: SiDescriptorContainer): Boolean = hasModifier(unit, MUTABLE)
        fun isOpen(unit: SiDescriptorContainer): Boolean = hasModifier(unit, OPEN)
        fun isOverride(unit: SiDescriptorContainer): Boolean = hasModifier(unit, OVERRIDE)
        fun isDynamic(unit: SiDescriptorContainer): Boolean = hasModifier(unit, DYNAMIC)
        fun isOptional(unit: SiDescriptorContainer): Boolean = hasModifier(unit, OPTIONAL)
        fun isVararg(unit: SiDescriptorContainer): Boolean = hasModifier(unit, VARARG)
    }
}

