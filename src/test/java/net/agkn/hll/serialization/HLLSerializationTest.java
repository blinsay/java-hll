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
    // NOTE: log2m<=16 was chosen as the max log2m parameter so that the test
    //       completes in a reasonable amount of time. Not much is gained by
    //       testing larger values - there are no more known serialization
    //       related edge cases that appear as log2m gets even larger.
    // NOTE: This test completed successfully with log2m<=MAXIMUM_LOG2M_PARAM
    //       on 2014-01-30.
    @Test
    public void serializationSmokeTest() {
        for(int log2m=MINIMUM_LOG2M_PARAM; log2m<=16; log2m++) {
            for(int regw=MINIMUM_REGWIDTH_PARAM; regw<=MAXIMUM_REGWIDTH_PARAM; regw++) {
                for(int expthr=MINIMUM_EXPTHRESH_PARAM; expthr<=MAXIMUM_EXPTHRESH_PARAM; expthr++ ) {
                    for(final HLLType hllType: HLLType.values()) {
                        for(final boolean sparse: new boolean[]{true, false}) {
                            // HLLType.values() includes UNDEFINED, which can't
                            // be instantiated.
                            if(hllType.equals(HLLType.UNDEFINED))
                                continue;

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
