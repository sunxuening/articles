package com.pivovarit.hamming.domain.encode

import com.pivovarit.hamming.domain.BinaryString

internal class StatelessBitIndexCalculator {

    internal fun codewordSize(msgLength: Int) = generateSequence(2) { it + 1 }
      .first { r -> msgLength + r + 1 <= (1 shl r) } + msgLength

    internal fun getHammingCodewordIndices(msgLength: Int) = generateSequence(0) { it + 1 }
      .take(codewordSize(msgLength))

    internal fun getParityBit(codeWordIndex: Int, msg: BinaryString) =
      parityIndicesSequence(codeWordIndex, codewordSize(msg.value.length))
        .map { getDataBit(it, msg).toInt() }
        .reduce { a, b -> a xor b }
        .toString()

    internal fun getDataBit(ind: Int, input: BinaryString) = input
      .value[ind - Integer.toBinaryString(ind).length].toString()

    internal fun parityIndicesSequence(startIndex: Int, endExclusive: Int) = generateSequence(startIndex) { it + 1 }
      .take(endExclusive - startIndex)
      .filterIndexed { i, _ -> i % ((2 * (startIndex + 1))) < startIndex + 1 }
      .drop(1)
}