/**
 * {类简要说明}
 * <p>
 * {类详细说明}
 *
 * @author jiangjiaxiong
 * @version 7.0.0
 * @since 2022-05-19
 */

/**
 * @author jiangjiaxiong
 * @date 2022年05月19日
 */
public class Main {
    public static void main(String[] args) {
        String s1 = "ABCDEa123abc";
        String s2 = "ABCDFB123abc";

        System.out.println(s1.hashCode());  // 165374702
        System.out.println(s2.hashCode()); //  165374702
    }
}
