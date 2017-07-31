using System;

namespace ConsoleApp1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
            int[] arr = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
            string str = ToString(BubbleSort(arr));
            Console.WriteLine(str);
        }

        static int[] BubbleSort(int[] arr)
        {

            for (int k = 0; k < arr.Length; k++)
            {
                for (int i = 0; i < arr.Length - 1; i++)
                {
                    if (arr[i] > arr[i + 1])
                    {
                        int Temp = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = Temp;
                    }
                }
            }
            return arr;
        }

        static string ToString(int[] arr)
        {
            string str = ""; 
            foreach ( int i in arr )
            {
                str += int.Parse("" + i + " ");
            }
            return str;
        }
    }

}