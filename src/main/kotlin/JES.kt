import java.lang.Exception
import kotlin.properties.Delegates

class JES(var data : String, var key : String) {

    init {
        if(key.length != 16){
            throw Exception("key must be 16 characters in length")
        }
        KeySchedule<String>(key).generate()
    }

    fun encrypt() : String{

        // this initially converts the string into its hex equivalent
        var initialHexArray = mutableListOf<String>()
        for (char in data){
            val hexString = Integer.toHexString(char.toInt())
            initialHexArray.add(hexString)
        }

        for(index in 1..10){
            val tempSubHexArray = mutableListOf<String>()
            val roundKeyArray = mutableListOf<String>()
            for (char in initialHexArray){
              val newHex = hexSub(char[0].toString(), char[1].toString())
                tempSubHexArray.add(newHex)
            }

            CollectionUtils<String>().shiftArray(tempSubHexArray)
            val schedule = KeySchedule.scheduler[index]
            val queue = CollectionUtils<String>().multiDimensionListToQueue(schedule!!)
            for(value in tempSubHexArray){
                val valueDecimal = DataFormatUtil.hexToDecimal(value)
                val poppedHex = queue.remove()!!
                val poppedHexDecimal = DataFormatUtil.hexToDecimal(poppedHex)
                val xorResult = valueDecimal xor poppedHexDecimal

                val convertedHex = DataFormatUtil.decimalToHex(xorResult)
                roundKeyArray.add(convertedHex)

                queue.add(poppedHex)
            }
            initialHexArray = roundKeyArray
        }

        return initialHexArray.joinToString ("")
    }

    fun decrypt() : String{
        return ""
    }

    fun hexSub(row : String, column : String) : String{
        var c = data;
        val indexOfColumn = Constants.hexMatrix[0].indexOf(column)
        var indexOfRow by Delegates.notNull<Int>()
        for ((index,array) in Constants.hexMatrix.withIndex()){
             if(array[0] == row){
                 indexOfRow = index
                 break
             }else{
                 continue
             }
        }
        return Constants.hexMatrix[indexOfRow][indexOfColumn]
    }
}