
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class AnnotationScanner {

    public static List<Class<?>> getClassesWithAnnotation(String packageName, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packageName);
        return new ArrayList<>(reflections.getTypesAnnotatedWith(annotation));
    }

    public static void main(String[] args) {
        List<Class<?>> classes = getClassesWithAnnotation("your.package.name", SortingRegister.class);
        for (Class<?> clazz : classes) {
            System.out.println(clazz.getName());
        }
    }
}
