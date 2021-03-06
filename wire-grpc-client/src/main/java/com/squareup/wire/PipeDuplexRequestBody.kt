package com.squareup.wire

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.Pipe
import okio.buffer

/**
 * A duplex request body that provides early writes via a pipe.
 */
class PipeDuplexRequestBody(
  private val contentType: MediaType?,
  pipeMaxBufferSize: Long
) : RequestBody() {
  private val pipe = Pipe(pipeMaxBufferSize)

  fun createSink() = pipe.sink.buffer()

  override fun contentType() = contentType

  override fun writeTo(sink: BufferedSink) {
    pipe.fold(sink)
  }

  override fun isDuplex() = true
}