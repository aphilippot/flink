package org.apache.flink.streaming.api.scala.async

import org.apache.flink.annotation.{ Public, PublicEvolving }
import org.apache.flink.api.common.functions.{ AbstractRichFunction, RichFunction }
import org.apache.flink.streaming.api.functions.async
import org.apache.flink.streaming.api.functions.async.{ RichAsyncFunction => JavaRichAsyncFunction }

/**
  * Rich variant of the {@link AsyncFunction}. As a {@link RichFunction}, it gives access to the
  * {@link RuntimeContext} and provides setup and teardown methods:
  * {@link RichFunction#open(org.apache.flink.configuration.Configuration)} and
  * {@link RichFunction#close()}.
  *
  * <p>State related apis in {@link RuntimeContext} are not supported yet because the key may get
  * changed while accessing states in the working thread.
  *
  * <p>{@link IterationRuntimeContext#getIterationAggregator(String)} is not supported since the
  * aggregator may be modified by multiple threads.
  *
  * @tparam IN The type of the input element
  * @tparam OUT The type of the output elements
  */
@PublicEvolving
abstract class RichAsyncFunction[IN,OUT] extends AbstractRichFunction[IN,OUT] with AsyncFunction[IN,OUT] {

  abstract def asyncInvoke(input: IN, resultFuture: ResultFuture[OUT]): Unit

}
