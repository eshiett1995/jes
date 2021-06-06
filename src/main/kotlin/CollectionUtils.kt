import java.util.*
import kotlin.collections.ArrayList

class CollectionUtils<T> {

    fun shiftArray(array: MutableList<T>) : MutableList<T>{
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

    fun shiftArrayBackward(array: MutableList<T>) : MutableList<T>{
        val temp = array[array.size - 1]
        for (index in array.size.minus(1) downTo 0){
            if(index != 0){
                array[index] = array[index - 1]
            }else{
                array[index] = temp
            }
        }
        return array
    }

    fun multiDimensionListToQueue(arrays: ArrayList<ArrayList<String>>) : Queue<String>{
        val newList = mutableListOf<String>()
        for (index in 0..arrays.size.minus(1)){
            newList.add(arrays[0][index])
            newList.add(arrays[1][index])
            newList.add(arrays[2][index])
            newList.add(arrays[3][index])
        }
        return LinkedList(newList)
    }

    fun shiftArray(array: Array<T>) : Array<T>{
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