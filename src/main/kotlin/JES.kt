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

    fun decrypt() : String {
        val stringToHexList: MutableList<String> = mutableListOf()
        for (index in 0 until data.length / 2) {
            val multiplier: Int = 2 * index;
            stringToHexList.add("${data[multiplier]}${data[multiplier + 1]}")
        }

        var final = mutableListOf<String>()
        for (index in 1..10) {
            val internalFinal = mutableListOf<String>()
            val tempSubHexArray = stringToHexList
            val roundKeyArray = mutableListOf<String>()
            val schedule = KeySchedule.scheduler[index]
            val queue = CollectionUtils<String>().multiDimensionListToQueue(schedule!!)
            for (value in tempSubHexArray) {
                val valueDecimal = DataFormatUtil.hexToDecimal(value)
                val poppedHex = queue.remove()!!
                val poppedHexDecimal = DataFormatUtil.hexToDecimal(poppedHex)
                val xorResult = valueDecimal xor poppedHexDecimal

                val convertedHex = DataFormatUtil.decimalToHex(xorResult)
                roundKeyArray.add(convertedHex)

                queue.add(poppedHex)
            }

            CollectionUtils<String>().shiftArrayBackward(roundKeyArray)

            for (char in stringToHexList) {
                val newHex = reSubHex(char[0].toString(), char[1].toString())
                internalFinal.add(newHex)
            }
            final.clear();
            final = internalFinal
        }

        return convertHexListToString(final)
    }

    private fun hexSub(row : String, column : String) : String{
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


    fun reSubHex(row : String, column : String) : String{
        var foundRowIndex = 0
        var foundColumnIndex = 0
        for ((rowIndex,array) in Constants.hexMatrix.withIndex()){

            for ((columnIndex, columnValue) in Constants.hexMatrix[rowIndex].withIndex()){
                if(row+column == columnValue){
                    foundRowIndex = rowIndex
                    foundColumnIndex = columnIndex
                    break
                }
            }
        }
        return Constants.hexMatrix[0][foundRowIndex] + Constants.hexMatrix[0][foundColumnIndex]
    }

    fun convertHexListToString(list: MutableList<String>) : String{
        var finalString = ""

        for (hex in list){
            finalString = finalString.plus(DataFormatUtil.hexToChar(hex))
        }

        return finalString;
    }
}