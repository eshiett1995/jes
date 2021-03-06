import java.math.BigInteger

class DataFormatUtil {
    companion object{
        fun hexToDecimal(hex: String): Int{
            return hex.toInt(16)
        }

        fun hexToChar(hex: String): Char{
            return hex.toInt(16).toChar()
        }

        fun decimalToBinary(decimal: Int) : String{
            return Integer.toBinaryString(decimal)
        }

        fun decimalToBinary(decimal: String) : String{
            return BigInteger(decimal, 16).toString(2)
        }
        fun decimalToHex(decimal: Int) : String{
            var stringToReturn = Integer.toHexString(decimal)
            if(stringToReturn.length == 1){
               stringToReturn = "0$stringToReturn"
            }
            return stringToReturn
        }
    }
}