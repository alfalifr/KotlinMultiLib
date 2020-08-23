package sidev.lib.reflex.common

enum class SiModifier(val id: Int) {
    OPEN(2),
    MUTABLE(4), //Untuk kasus field, khususnya pada Java dg flag `final`.
    OVERRIDE(8),
    ABSTRACT(16),
    DYNAMIC(32),
    OPTIONAL(64),
    VARARG(128),
    ;

    companion object{
        fun hasModifier(unit: SiReflex, modifier: SiModifier): Boolean = (unit.descriptor.modifier and modifier.id) != 0
        fun setModifier(unit: SiReflex, modifier: SiModifier){
            (unit.descriptor as SiDescriptorImpl).modifier = unit.descriptor.modifier or modifier.id
        }
        fun isAbstract(unit: SiReflex): Boolean = hasModifier(unit, ABSTRACT)
        fun isMutable(unit: SiReflex): Boolean = hasModifier(unit, MUTABLE)
        fun isOpen(unit: SiReflex): Boolean = hasModifier(unit, OPEN)
        fun isOverride(unit: SiReflex): Boolean = hasModifier(unit, OVERRIDE)
        fun isDynamic(unit: SiReflex): Boolean = hasModifier(unit, DYNAMIC)
        fun isOptional(unit: SiReflex): Boolean = hasModifier(unit, OPTIONAL)
        fun isVararg(unit: SiReflex): Boolean = hasModifier(unit, VARARG)
    }
}

