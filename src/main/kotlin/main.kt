
import java.math.BigInteger


fun main(args: Array<String>) {
    //JES("this is where it all starts", "charles").encrypt()
    println(40 xor 160)
}


fun hexToBinary(hex: String): String? {
    val i = hex.toInt(16)
    return Integer.toBinaryString(i)
}