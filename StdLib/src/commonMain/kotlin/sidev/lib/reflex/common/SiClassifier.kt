package sidev.lib.reflex.common

import kotlin.reflect.KClassifier

/**
 * Turunan dapat berupa:
 *   - [SiClass]
 *   - [SiTypeParameter]
 *   - [JsCallable]
 */
interface SiClassifier : SiReflex, KClassifier
