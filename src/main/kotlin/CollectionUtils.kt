class CollectionUtils<T> {
    fun shiftArray(array: Array<T>) : Array<T>{
        val temp = array[0]
        for ((index,value) in array.withIndex()){
            if(index != array.size.minus(1)){
                array[index] = array[index + 1]
            }else{
                array[index] = temp
            }
        }
        print(array.contentToString())
        return array
    }

    fun shiftArray(array: ArrayList<T>) : ArrayList<T>{
        val temp = array[0]
        for ((index,value) in array.withIndex()){
            if(index != array.size.minus(1)){
                array[index] = array[index + 1]
            }else{
                array[index] = temp
            }
        }
        return array
    }

    fun arrangeArrayToColumn(
        firstArray: ArrayList<T>,
        secondArray: ArrayList<T>,
        thirdArray: ArrayList<T>,
        fourthArray: ArrayList<T>) : ArrayList<ArrayList<String>> {

        val newScheduler = arrayListOf<ArrayList<String>>(
            arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(),)

        for((index,value) in firstArray.withIndex()){
           newScheduler[index].add(0, value.toString())
        }

        for((index,value) in secondArray.withIndex()){
            newScheduler[index].add(1, value.toString())
        }

        for((index,value) in thirdArray.withIndex()){
            newScheduler[index].add(2, value.toString())
        }

        for((index,value) in fourthArray.withIndex()){
            newScheduler[index].add(3, value.toString())
        }

        return newScheduler
    }
}