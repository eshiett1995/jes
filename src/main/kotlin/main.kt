fun main(args: Array<String>) {
    val result = JES("hello", "thisissparta1234").encrypt()
    println(result)

    val decryptedResult = JES(result, "thisissparta1234").decrypt()
    println(decryptedResult)
}