package sidev.lib.reflex.common

enum class SiModifier(val id: Int) {
    OPEN(2),
    OVERRIDE(4),
    ABSTRACT(8),
    DYNAMIC(16),
    OPTIONAL(32),
    VARARG(64),
    ;

    companion object{
        fun hasModifier(unit: SiReflex, modifier: SiModifier): Boolean = (unit.descriptor.modifier and modifier.id) != 0
        fun setModifier(unit: SiReflex, modifier: SiModifier){
            (unit.descriptor as SiDescriptorImpl).modifier = unit.descriptor.modifier or modifier.id
        }
        fun isAbstract(unit: SiReflex): Boolean = hasModifier(unit, ABSTRACT)
        fun isOpen(unit: SiReflex): Boolean = hasModifier(unit, OPEN)
        fun isOverride(unit: SiReflex): Boolean = hasModifier(unit, OVERRIDE)
        fun isDynamic(unit: SiReflex): Boolean = hasModifier(unit, DYNAMIC)
        fun isOptional(unit: SiReflex): Boolean = hasModifier(unit, OPTIONAL)
        fun isVararg(unit: SiReflex): Boolean = hasModifier(unit, VARARG)
    }
}

