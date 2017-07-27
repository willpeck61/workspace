--------------------------------------------------------------------
-- CO 2008  Functional Programming
-- Created: February 2016, University of Leicester, UK
-- handin 14.00 hr on 13th February (summatively assessed) 
--------------------------------------------------------------------
-- Student Name
-- Student Number
--------------------------------------------------------------------
--
-- Please don't hand in buggy solutions. That makes the marking harder.
-- Points may be deducted if your solution does not compile properly...
--


module Worksheet2 where
import Char

----------------------------------------------------------------------
-- Exercise 1: A phone book
---------------------------------------------------------------------

type Name = String
type PhoneNumber = Integer
type Person = (Name, PhoneNumber)
type PhoneBook = [Person]

personA :: Person
personA = ("Will", 333839)

personB :: Person
personB = ("John", 747373)

personC :: Person
personC = ("James", 463623)

phonebook :: PhoneBook
phonebook = [personA, personB, personB, personC]


-- Part a)

add :: Person -> PhoneBook -> PhoneBook
add details entries = details : phonebook


-- Part b)

delete :: Name -> PhoneBook -> PhoneBook
delete name phonebook = filter entry phonebook
	where entry (x, y) = x /= name

--  Part c)

find :: Name -> PhoneBook -> [PhoneNumber]
find names phonebook = [number | (name, number) <- phonebook, name == names]

--  Part d)

update :: Name -> PhoneNumber -> PhoneNumber -> PhoneBook -> PhoneBook
update findName oldNumber newNumber [] = []
update findName oldNumber newNumber ((name, oldNum) : phonebook)
	| findName == name && oldNumber == oldNum = (findName, newNumber) : update findName oldNumber newNumber phonebook
    | otherwise = (name, oldNum) : update findName oldNumber newNumber phonebook

-----------------------------------------------------------------
-- Exercise 2:  Customers of a Bank
-----------------------------------------------------------------

type NI = Int
type Age = Int
type Balance = Float
type Customer  = (NI,Age, Balance)
type Bank = [Customer]

customerA :: Customer
customerA = (1111, 61, 1000.10)

customerB :: Customer
customerB = (2222, 23, 100000)

customerC :: Customer
customerC = (3333, 44, -100)

bank :: Bank
bank = [customerA, customerB, customerC]

-- Part a)
retired :: Customer -> Bool
retired (ni, age, bal) = if age > 60 then True else False

-- Part b)
deposit :: Customer -> Float -> Customer
deposit (ni, age, bal) amount = (ni, age, bal + amount)


-- Part c)
withdraw :: Customer -> Float -> Customer
withdraw (ni, age, bal) amount = if bal > 0 then (ni, age, bal - amount) else (ni, age, bal)

-- Part d)
credit [] = []
credit ((ni, age, bal):bank) = if bal > 0 then (ni, age, bal) : credit bank else credit bank


-----------------------------------------------------------------
-- Exercise 3: 
-----------------------------------------------------------------

cubeOdds :: [Int] -> [Int]
cubeOdds xs = [x^3 | x <- xs, x >= 0, odd x ] 

cubeOdds2 :: [Int] -> [Int]
cubeOdds2 xs = filter odd (map (^3) xs)


-----------------------------------------------------------------
-- Exercise 4: 
-----------------------------------------------------------------

repChar :: (Char, Char) -> String -> String
repChar (a, b) [] = []
repChar (a, b) (x : str) = if x == a then repA x : repChar (a, b) str else x : repChar (a, b) str
	where repA n = b


-----------------------------------------------------------------
-- Exercise 5a: 
-----------------------------------------------------------------

zap :: [Int] -> [Int] -> [(Int, Int)]
zap nums1 nums2 = zip nums1 nums2


-----------------------------------------------------------------
-- Exercise 5b:  
-----------------------------------------------------------------

addIndex :: [Int] -> [(Int,Int)]
addIndex numbers = zip [1 .. length numbers] numbers


-----------------------------------------------------------------
-- Exercise 5c:  
-----------------------------------------------------------------

extend :: Int -> String -> String
extend size word = if (size > length word) then extend size (word ++ " ") else word 


-----------------------------------------------------------------
-- Exercise 5d:  
-----------------------------------------------------------------
list :: [Int]
list = [56,49,45,43,43,42,39,39,34,33,31,27,26,24,23,22,21,20,19,17]

makeStars :: Int -> [String]
makeStars n = if (n `mod` 3 > 0) 
				then replicate (n `div` 3) "*** " ++ replicate (n `mod` 3) "*"
				else replicate (n `div` 3) "*** "

flatten :: [String] -> String
flatten [] = []
flatten (x : xs) = x ++ flatten xs

table :: [Int] -> String
table [] = []
table (x : xs) = if (length list - length xs) < 10 
					then show (length list - length xs) ++ "  " ++ flatten (makeStars x) ++ ['\n'] ++ table xs
					else show (length list - length xs) ++ " " ++ flatten (makeStars x) ++ ['\n'] ++ table xs

{-|
1  *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** **
2  *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *
3  *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** 
4  *** *** *** *** *** *** *** *** *** *** *** *** *** *** *
5  *** *** *** *** *** *** *** *** *** *** *** *** *** *** *
6  *** *** *** *** *** *** *** *** *** *** *** *** *** *** 
7  *** *** *** *** *** *** *** *** *** *** *** *** *** 
8  *** *** *** *** *** *** *** *** *** *** *** *** *** 
9  *** *** *** *** *** *** *** *** *** *** *** *
10 *** *** *** *** *** *** *** *** *** *** *** 
11 *** *** *** *** *** *** *** *** *** *** *
12 *** *** *** *** *** *** *** *** *** 
13 *** *** *** *** *** *** *** *** **
14 *** *** *** *** *** *** *** *** 
15 *** *** *** *** *** *** *** **
16 *** *** *** *** *** *** *** *
17 *** *** *** *** *** *** *** 
18 *** *** *** *** *** *** **
19 *** *** *** *** *** *** *
20 *** *** *** *** *** **
-}