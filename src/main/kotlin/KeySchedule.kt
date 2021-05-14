import Constants.Rcon
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
        // gets the last key in the schedule - the schedule is a 4*4 array
        val lastScheduledArray = scheduler[scheduler.keys.last()]
        lastScheduledArray?.get(0)?.get(3)!!

        // get the last column in the schedule
        val lastColumn = arrayListOf(
            lastScheduledArray[0][3],
            lastScheduledArray[1][3],
            lastScheduledArray[2][3],
            lastScheduledArray[3][3]
        )
        // the values in the column gotten in shifted one place
        val shiftedLastColumn = ArrayShifter<String>().shiftArray(lastColumn)
        // then the column is hexed
        val subbedHexArray = swapHexForHexValue(shiftedLastColumn)

        val newScheduler = arrayListOf<ArrayList<String>>()
        val tempArray = arrayListOf<String>()

        for (index in 0..3){
            val item = lastScheduledArray[index][0]
            val itemDecimal = DataFormatUtil.hexToDecimal(item)

            val item2 = subbedHexArray[index]
            val item2Decimal = DataFormatUtil.hexToDecimal(item2)

            val rConItem = Rcon[index][0]
            val rConItemDecimal = DataFormatUtil.hexToDecimal(rConItem)

            val xorDecimal = itemDecimal xor item2Decimal xor rConItemDecimal

            val convertedHex = DataFormatUtil.decimalToHex(xorDecimal)
            tempArray.add(convertedHex)
        }

        newScheduler.add(tempArray)

        for(index in 1..3){
            // clears the arrayList so it can be used again
            tempArray.clear()
            val columnOfNewSchedule = newScheduler[1- index]
            val columnOfPreviousSchedule = lastScheduledArray[index]
            for(cellIndex in 0..3){
                val hexOfNewSchedule = columnOfNewSchedule[index]
                val hexOfNewPrevious = columnOfPreviousSchedule[index]

                val hexToDecimalOfNewSchedule = DataFormatUtil.hexToDecimal(hexOfNewSchedule)
                val hexToDecimalOfPreviousSchedule = DataFormatUtil.hexToDecimal(hexOfNewPrevious)
                val xorValue = hexToDecimalOfNewSchedule xor hexToDecimalOfPreviousSchedule
                val xorValueAsHex = DataFormatUtil.decimalToHex(xorValue)
                tempArray.add(xorValueAsHex)
            }
            //todo please note that this saves as a row for now, create a function to fix it
            //adds the temp array as a new column to the scheduler
            newScheduler.add(tempArray)
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