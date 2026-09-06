package com.wimdeblauwe.greeter;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A {@link ClassLoader} for tests that defines a selected set of classes itself (child-first),
 * while pretending that another set of classes is not on the classpath.
 * <p>
 * Because the classes given to {@link #defining(Class[])} are <em>defined</em> by this class loader,
 * reflection over their members resolves through this class loader as well. That makes it possible
 * to reproduce the {@code NoClassDefFoundError} that Spring runs into when it introspects a
 * configuration class that references a type from a missing optional dependency.
 */
public final class HidingClassLoader extends URLClassLoader {

  private final Set<String> locallyDefinedClassNames;
  private final Set<String> hiddenClassNames;

  private HidingClassLoader(URL[] urls,
                            ClassLoader parent,
                            Set<String> locallyDefinedClassNames,
                            Set<String> hiddenClassNames) {
    super(urls, parent);
    this.locallyDefinedClassNames = locallyDefinedClassNames;
    this.hiddenClassNames = hiddenClassNames;
  }

  public static Builder defining(Class<?>... classes) {
    return new Builder(classes);
  }

  @Override
  protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
    if (hiddenClassNames.contains(name)) {
      throw new ClassNotFoundException(name);
    }
    if (!shouldDefineLocally(name)) {
      return super.loadClass(name, resolve);
    }
    synchronized (getClassLoadingLock(name)) {
      Class<?> result = findLoadedClass(name);
      if (result == null) {
        result = findClass(name);
      }
      if (resolve) {
        resolveClass(result);
      }
      return result;
    }
  }

  private boolean shouldDefineLocally(String name) {
    return locallyDefinedClassNames.stream()
                                   .anyMatch(local -> name.equals(local)
                                       || name.startsWith(local + "$"));
  }

  public static final class Builder {

    private final Class<?>[] classes;

    private Builder(Class<?>[] classes) {
      this.classes = classes;
    }

    public HidingClassLoader hiding(Class<?>... classesToHide) {
      return hiding(Arrays.stream(classesToHide)
                          .map(Class::getName)
                          .collect(Collectors.toSet()));
    }

    public HidingClassLoader hiding(Set<String> classNamesToHide) {
      return new HidingClassLoader(codeSourceLocations(),
                                   HidingClassLoader.class.getClassLoader(),
                                   Arrays.stream(classes)
                                         .map(Class::getName)
                                         .collect(Collectors.toSet()),
                                   Set.copyOf(classNamesToHide));
    }

    private URL[] codeSourceLocations() {
      return Arrays.stream(classes)
                   .map(Builder::codeSourceLocation)
                   .distinct()
                   .toArray(URL[]::new);
    }

    private static URL codeSourceLocation(Class<?> clazz) {
      CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
      if (codeSource == null || codeSource.getLocation() == null) {
        throw new IllegalArgumentException(
            "Unable to determine the code source location of " + clazz.getName());
      }
      return codeSource.getLocation();
    }
  }
}
