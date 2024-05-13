package io.github.elihuso.data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;

public class JavaResources {
    @Nullable
    public static InputStream getResource(@Nonnull String file) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader.getResourceAsStream(file);
    }
}
