package com.manh.debug;

public class RequestContext {
    private static final ThreadLocal<RequestEntity> threadLocal = new ThreadLocal<>();

    public static RequestEntity get() {
        System.out.println("VerboseResponseFilter executed." + threadLocal.get().isXverbose());

        return threadLocal.get();
    }

    public static void set(RequestEntity entity) {
        System.out.println("VerboseResponseFilter executed." + entity.isXverbose());

        threadLocal.set(entity);
    }

    public static void clear() {
        threadLocal.remove();
    }
}
