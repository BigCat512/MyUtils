package org.example.demo.lambda;

import java.math.BigDecimal;
import java.util.function.Supplier;

/**
 * <p>
 * Supplier
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/5/30
 */
public class SupplierTest {
    public void booking(Supplier<BigDecimal> supplier) {
        // 库存操作

        // 会员积分

        // 会员打骨折，气穷逼不打折
        BigDecimal discount = supplier.get();
        // balabalababababa
        System.out.println(discount);

    }

    public static void main(String[] args) {
        Supplier<BigDecimal> vip = () -> new BigDecimal("0.5");
        Supplier<BigDecimal> pauper = () -> new BigDecimal("0.9");
        new SupplierTest().booking(vip);
        new SupplierTest().booking(pauper);
    }
}
