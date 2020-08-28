package sidev.lib.reflex

object SiModifierIntern {
    fun setModifier(unit: SiDescriptorContainer, modifier: SiModifier){
        (unit.descriptor as SiDescriptorImpl).modifier = unit.descriptor.modifier or modifier.id
    }
}