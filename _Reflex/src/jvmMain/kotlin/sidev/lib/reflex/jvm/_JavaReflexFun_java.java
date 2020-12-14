package sidev.lib.reflex.jvm;

class _JavaReflexFun_java {
    static boolean isPrimitiveWrapper(Class<?> cls){
        return cls == Integer.class
                || cls == Long.class
                || cls == Float.class
                || cls == Double.class
                || cls == Character.class
                || cls == Short.class
                || cls == Boolean.class
                || cls == Byte.class;
    }
}
