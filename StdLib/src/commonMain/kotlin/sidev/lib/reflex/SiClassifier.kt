package sidev.lib.reflex

import kotlin.reflect.KClassifier

/**
 * Turunan dapat berupa:
 *   - [SiClass]
 *   - [SiTypeParameter]
 *   - [JsCallable]
 */
interface SiClassifier : SiDescriptorContainer, KClassifier
