package net.agkn.hll.serialization;

import net.agkn.hll.HLL;
import net.agkn.hll.HLLType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static net.agkn.hll.HLL.MAXIMUM_EXPTHRESH_PARAM;
import static net.agkn.hll.HLL.MAXIMUM_LOG2M_PARAM;
import static net.agkn.hll.HLL.MAXIMUM_REGWIDTH_PARAM;
import static net.agkn.hll.HLL.MINIMUM_EXPTHRESH_PARAM;
import static net.agkn.hll.HLL.MINIMUM_LOG2M_PARAM;
import static net.agkn.hll.HLL.MINIMUM_REGWIDTH_PARAM;
import static org.testng.Assert.assertEquals;

/**
 * Serialization smoke-tests.
 *
 * @author yerenkow
 * @author benl
 */
public class HLLSerializationTest {

    /**
     * A smoke-test that covers serialization/deserialization of an empty HLL
     * under all possible parameters.
     */
    @Test
    public void serializationSmokeTest() {
        for(int log2m=MINIMUM_LOG2M_PARAM; log2m <= MAXIMUM_LOG2M_PARAM; log2m++) {
            for(int regw=MINIMUM_REGWIDTH_PARAM; regw<=MAXIMUM_REGWIDTH_PARAM; regw++) {
                for(int expthr=MINIMUM_EXPTHRESH_PARAM; expthr<=MAXIMUM_EXPTHRESH_PARAM; expthr++ ) {
                    for(final HLLType hllType: HLLType.values()) {
                        for(final boolean sparse: new boolean[]{true, false}) {
                            HLL hll = new HLL(log2m, regw, expthr, sparse, hllType);
                            HLL copy = HLL.fromBytes(hll.toBytes());
                            assertEquals(copy.cardinality(), hll.cardinality());
                        }
                    }
                }
            }
        }
    }
}
