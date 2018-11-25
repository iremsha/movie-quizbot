//@FunctionalInterface
//interface TriFunction<A,B,C,R> {
//
//    R apply(A a, B b, C c);
//
//    default <V> TriFunction<A, B, C, V> andThen(
//            Function<? super R, ? extends V> after) {
//        Objects.requireNonNull(after);
//        return (A a, B b, C c) -> after.apply(apply(a, b, c));
//    }
//}
//
//public class Main {
//
//    public static void main(String[] args) {
//        BiFunction<Integer, Long, String> bi = (x,y) -> ""+x+","+y;
//        TriFunction<Boolean, Integer, Long, String> tri = (x,y,z) -> ""+x+","+y+","+z;
//
//
//        System.out.println(bi.apply(1, 2L)); //1,2
//        System.out.println(tri.apply(false, 1, 2L)); //false,1,2
//
//        tri = tri.andThen(s -> "["+s+"]");
//        System.out.println(tri.apply(true,2,3L)); //[true,2,3]
//    }
//}