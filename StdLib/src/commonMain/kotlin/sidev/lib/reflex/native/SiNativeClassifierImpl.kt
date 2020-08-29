package sidev.lib.reflex.native

import sidev.lib.reflex.SiClassifier
import sidev.lib.reflex.SiDescriptorContainerImpl
import kotlin.reflect.KClassifier


interface SiKClassifier: SiNative, SiClassifier, KClassifier
internal abstract class SiKClassifierImpl(override val implementation: KClassifier)
    : SiDescriptorContainerImpl(), SiKClassifier, KClassifier by implementation