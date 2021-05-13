import kotlin.properties.Delegates

class KeySchedule<T> (var key : String){
    private var scheduler : HashMap<Int, ArrayList<ArrayList<String>>> = HashMap()

    fun generate(){
        val hexList = swapForHexValue()
        val firstSchedule = arrangeToColumnAndPushToScheduler(hexList)
        scheduler[1] = firstSchedule
        makeNextSchedule()
    }

    private fun makeNextSchedule(){
        val lastScheduledArray = scheduler[scheduler.keys.last()]
        lastScheduledArray?.get(0)?.get(4)!!
        val lastColumn = arrayListOf(
            lastScheduledArray[0][3],
            lastScheduledArray[1][3],
            lastScheduledArray[2][3],
            lastScheduledArray[3][3]
        )
        val shiftedLastColumn = ArrayShifter<String>().shiftArray(lastColumn)
        val subbedHexArray = swapHexForHexValue(shiftedLastColumn)
        for (index in 0..3){
            //Todo this is when xor comes in.
            val item = lastScheduledArray[index][0]
            val itemDecimal = DataFormatUtil.hexToDecimal(item)

            val item2 = subbedHexArray[index]
            val item2Decimal = DataFormatUtil.hexToDecimal(item2)

            val xorDecimal = itemDecimal xor item2Decimal
        }
    }

    private fun swapHexForHexValue(array: ArrayList<String>) : MutableList<String>{
        val subbedHexArray = arrayListOf<String>()
        for (oldHex in array){
            val newHex = hexSub(oldHex[0].toString(), oldHex[1].toString())
            subbedHexArray.add(newHex)
        }
        return subbedHexArray
    }

    private fun swapForHexValue() : MutableList<String>{
        val subbedHexArray = mutableListOf<String>()
        for (char in key){
            val charArray = Integer.toHexString(char.toInt()).toCharArray()
            val newHex = hexSub(charArray[0].toString(), charArray[1].toString())
            subbedHexArray.add(newHex)
        }
        return subbedHexArray
    }

    private fun hexSub(row : String, column : String) : String{
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

    private fun arrangeToColumnAndPushToScheduler(hexList: List<String>) : ArrayList<ArrayList<String>>{
        val finalList = ArrayList<ArrayList<String>>()
        val rowOneList = ArrayList<String>()
        val rowTwoList = ArrayList<String>()
        val rowThreeList = ArrayList<String>()
        val rowFourList = ArrayList<String>()
        for((index,hex) in hexList.withIndex()){

            if(index == 0 || index == 4 || index == 8 || index == 12){
                rowOneList.add(hex)
            }
            if(index == 1 || index == 5 || index == 9 || index == 13){
                rowTwoList.add(hex)
            }
            if(index == 2 || index == 6 || index == 10 || index == 14){
                rowThreeList.add(hex)
            }
            if(index == 3 || index == 7 || index == 11 || index == 15){
                rowFourList.add(hex)
            }
        }

        finalList.add(rowOneList)
        finalList.add(rowTwoList)
        finalList.add(rowThreeList)
        finalList.add(rowFourList)
        return finalList
    }
}