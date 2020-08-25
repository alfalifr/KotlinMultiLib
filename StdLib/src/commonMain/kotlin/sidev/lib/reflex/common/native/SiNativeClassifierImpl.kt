package sidev.lib.reflex.common.native

import sidev.lib.reflex.common.SiClassifier
import sidev.lib.reflex.common.SiDescriptorContainerImpl
import kotlin.reflect.KClassifier


interface SiKClassifier: SiNativeWrapper, SiClassifier, KClassifier
internal abstract class SiKClassifierImpl(override val implementation: KClassifier)
    : SiDescriptorContainerImpl(), SiKClassifier, KClassifier by implementation