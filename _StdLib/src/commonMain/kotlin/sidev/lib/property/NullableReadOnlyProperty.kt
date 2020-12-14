package sidev.lib.property

import kotlin.properties.ReadOnlyProperty

interface NullableReadOnlyProperty<in R, out T>: ReadOnlyProperty<R, T?>