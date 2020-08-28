package sidev.lib.reflex.comp

import kotlin.reflect.KClassifier

/**
 * Turunan dapat berupa:
 *   - [SiClass]
 *   - [SiTypeParameter]
 *   - [JsCallable]
 */
interface SiClassifier : SiDescriptorContainer, KClassifier
