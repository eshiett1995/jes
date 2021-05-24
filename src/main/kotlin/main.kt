fun main(args: Array<String>) {
    val result = JES("this is where it all starts", "thisissparta1234").encrypt()
    println(result)
}


fun hexToBinary(hex: String): String? {
    val i = hex.toInt(16)
    return Integer.toBinaryString(i)
}