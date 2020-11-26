package sidev.lib.math.arithmetic

// arithmetic

/*
GD:
==========

-Sistem persamaan / pertidak-samaan disebut `Equation`.
-`Equation` dibagi menjadi bbrp `Block` yang dipisahkan oleh tanda persamaan / pertidak-samaan.
-1 `Block` dapat terdiri dari bbrp `SubBlock` yg dipisahkan dg tanda kurung.
-1 `SubBlock` terdiri dari bbrp `Variable` yg dipisahkan oleh `Operation`.
Equation:
(block 1) = (block 2 (nested block 1) + (nested block 2)) > (block 3)
 */