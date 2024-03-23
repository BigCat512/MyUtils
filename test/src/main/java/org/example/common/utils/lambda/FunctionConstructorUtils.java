package org.example.common.utils.lambda;

import java.util.function.*;

/**
 * @author 粉帮-张重虎
 * @since 2023-07-18 14:29
 */
public class FunctionConstructorUtils {
    public static <T> void initialize(T t, Consumer<? super T> consumer) {
        consumer.accept(t);
    }

    public static <T> Consumer<? super T> initialize(Consumer<T> before, Consumer<? super T> after) {
        return before.andThen(after);
    }

    public static <T, U> void initialize(T t, U u, BiConsumer<? super T, ? super U> consumer) {
        consumer.accept(t, u);
    }

    public static <T, U> BiConsumer<? super T, ? super U> initialize(T t, U u, BiConsumer<T, U> before, BiConsumer<? super T, ? super U> after) {
        return before.andThen(after);
    }

    public static <T> T initialize(Supplier<? extends T> supplier) {
        return supplier.get();
    }

    public static <T> boolean initialize(T t, Predicate<? super T> predicate) {
        return predicate.test(t);
    }

    public static <T> Predicate<? super T> initialize(Boolean type, Predicate<T> before, Predicate<? super T> after) {
        if (type) {
            return before.and(after);
        }
        return before.or(after);
    }

    public static <T> Predicate<? super T> initialize(Predicate<? super T> predicate) {
        return predicate.negate();
    }

    public static <T> Predicate<? super T> initialize(T t) {
        return Predicate.isEqual(t);
    }

    public static <T, U> boolean initialize(T t, U u, BiPredicate<? super T, ? super U> predicate) {
        return predicate.test(t, u);
    }

    public static <T, U> BiPredicate<? super T, ? super U> initialize(Boolean type, BiPredicate<T, U> before, BiPredicate<? super T, ? super U> after) {
        if (type) {
            return before.and(after);
        }
        return before.or(after);
    }

    public static <T, U> BiPredicate<? super T, ? super U> initialize(BiPredicate<? super T, ? super U> predicate) {
        return predicate.negate();
    }

    public static <T, R> R initialize(T t, Function<? super T, ? extends R> function) {
        return function.apply(t);
    }

    public static <T, R, V> R initialize(V v, Function<? super T, ? extends R> before, Function<? super V, ? extends T> after) {
        return before.compose(after).apply(v);
    }

    public static <T, R, V> V initialize(Function<? super T, ? extends R> before, Function<? super R, ? extends V> after, T t) {
        return before.andThen(after).apply(t);
    }

    public static <T, U, R> R initialize(T t, U u, BiFunction<? super T, ? super U, ? extends R> function) {
        return function.apply(t, u);
    }

    public static <T, U, R, V> V initialize(BiFunction<? super T, ? super U, ? extends R> before, Function<? super R, ? extends V> after, T t, U u) {
        return before.andThen(after).apply(t, u);
    }

    public static void main(String[] args) {
        System.out.println(initialize(Object::new));
    }
}
