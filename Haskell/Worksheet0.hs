--------------------------------------------------------------------
-- CO 2008  Functional Programming
-- Created: January 2016, University of Leicester, UK
--------------------------------------------------------------------
--
-- DON'T FORGET TO FILL IN NAME AND STUDENT NUMBER.
--
--------------------------------------------------------------------
-- Student Will Peck
-- Student 159029051
--------------------------------------------------------------------

module Worksheet0 where
import Char
--------------------------------------------------------------------
-- Exercise 6
--------------------------------------------------------------------


myint :: Int
myint = 707

myfloat :: Float
myfloat = 12.5


mychar :: Char
mychar = 't'


mystring :: String
mystring = "Hello "

less :: Bool
less = (myint < 100)

cube :: Int -> Int
cube n = n*n*n

--------------------------------------------------------------------
-- Exercise 7
--------------------------------------------------------------------


-- A function with two integer input that adds them.
plus :: Int -> Int -> Int
plus m n = m + n


-- A function with three integer inputs and Boolean output
-- yields True if all inputs equal, else False.
allEqual :: Int -> Int -> Int -> Bool
allEqual m n k = m == n && n == k



--------------------------------------------------------------------
-- Exercise 10
--------------------------------------------------------------------


message :: Float -> String
message x = "The number is " ++ (show x)



--------------------------------------------------------------------
-- Exercise 11
--------------------------------------------------------------------

--Write a function blankVowel that given a character x returns a blank
--when x is a vowel (ie. x âˆˆ {a, e, i, o, u, A, . . .}) and other wise returns x.

blankVowel :: Char -> Char
blankVowel x 
	| x `elem` ["a","e","i","o","u","A","E","I","O","U"] = ' '
	| otherwise = x

text :: String
text = "Altijd is Kortjakje ziek,\ndriemaal in de week,\nmaar zondags niet."

-------------------------------------------------------------------
-- Exercise 12 Challenge
--------------------------------------------------------------------

--Write a function blankVowel2 that given a character x returns a blank
--when x is a DUTCH vowel (ie. x âˆˆ {a, e, i, ij, o, u, A, . . .}) and other wise returns x.
-- in the Dutch language also the combination ij is treated as a vowel!

-- ****** NB: Incomplete - Still working on this challenge ******
blankVowel2 :: String -> String
blankVowel2 x 
	| head x `elem` ["a","e","i","o","u","A","E","I","O","U"] = " "
	| tail x `elem` ["ij", "IJ"] = " "
	| otherwise = x