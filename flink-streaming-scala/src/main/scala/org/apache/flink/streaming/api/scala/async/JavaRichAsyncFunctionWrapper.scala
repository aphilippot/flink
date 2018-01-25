package org.apache.flink.streaming.api.scala.async

import org.apache.flink.api.common.functions.RuntimeContext
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.async
import org.apache.flink.streaming.api.functions.async.{ RichAsyncFunction => JavaRichAsyncFunction }

final class JavaRichAsyncFunctionWrapper[IN,OUT](
  private[this] val scalaRichAsyncFunction: RichAsyncFunction[IN,OUT])
  extends JavaRichAsyncFunction[IN,OUT] {

  override def asyncInvoke(input: IN, resultFuture: async.ResultFuture[OUT]) = {
    scalaRichAsyncFunction.asyncInvoke(input, new JavaResultFutureWrapper[OUT](resultFuture))
  }

  override def setRuntimeContext(runtimeContext: RuntimeContext): Unit = {
    super.setRuntimeContext(runtimeContext)
  }

  override def open(parameters: Configuration): Unit = {
    super.open(parameters)
    scalaRichAsyncFunction.open(parameters)
  }

  override def close(): Unit = {
    super.close()
    scalaRichAsyncFunction.close()
  }
}
