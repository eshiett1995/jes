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
            for (char in initialHexArray){
              val newHex = hexSub(char[0].toString(), char[1].toString())
                tempSubHexArray.add(newHex)
            }

            CollectionUtils<String>().shiftArray(tempSubHexArray)
            var cc = KeySchedule.scheduler
            initialHexArray = tempSubHexArray
            println(tempSubHexArray.joinToString ("-"))
        }

//       val subbedHexArray = mutableListOf<String>()
//       for (char in data){
//           val charArray = Integer.toHexString(char.toInt()).toCharArray()
//           val newHex = hexSub(charArray[0].toString(), charArray[1].toString())
//           subbedHexArray.add(newHex)
//       }
//       CollectionUtils<String>().shiftArray(subbedHexArray.toTypedArray())
           return ""
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