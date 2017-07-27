
-- CO 2008 Functional Programming 
-- Created: March 2016, University of Leicester, UK 
-------------------------------------------------------------------- 
-- Student Name Will Peck
-- Student Number 159029051
--------------------------------------------------------------------
--
-- VERY IMPORTANT: call this file worksheet4.hs when you hand in
-- starting with small "w"
-- if you don't call it EXACTLY LIKE THAT the hand in system won't accept your file

module Worksheet4 where 

---------------------------------------------------------------------
----- EXERCISE 1
---------------------------------------------------------------------
data Value = A|Two|Three|Four|Five|Six|Seven|Eight|Nine|Ten|J|Q|K
             deriving (Eq, Ord, Enum)
instance Show Value
-- Part a)
	where 
		show A     = "A"
		show Two   = "2"
		show Three = "3"
		show Four  = "4"
		show Five  = "5"
		show Six   = "6"
		show Seven = "7"
		show Eight = "8"
		show Nine  = "9"
		show Ten   = "10"
		show J     = "J"
		show Q     = "Q"
		show K     = "K"

data Suite = Hearts | Spades | Clubs | Diamonds
             deriving (Eq, Ord, Enum)
instance Show Suite
--- Part b)
  where 
  	show Hearts   = "H"
  	show Spades   = "S"
  	show Clubs    = "C"
  	show Diamonds = "D" 

data Colour = Red | Black
              deriving (Eq, Ord,Enum, Show)

data Error a = Fail|Ok a
              deriving (Eq, Ord, Show)

type Card  = (Value, Suite)


cardToStr :: [Card] -> String
cardToStr c = show (fst(head c)) ++ show (snd(head c))

--- Part c)
pack :: [Card]																			
pack = [ (n, m) | n <- [A .. K], m <- [Hearts .. Diamonds] ]

--- Part d)
colour :: Card -> Colour
colour c
	| snd(c) == Hearts   = Red
	| snd(c) == Diamonds = Red
	| snd(c) == Clubs    = Black
	| snd(c) == Spades   = Black

--- Part e)

split :: Int -> [a] -> (Error ( [a], [a] ))         -- see line 91 for pattern match 
split n (x : xs)																	  -- using x and xpack
	| n < 0         = Fail
	| n > length xs = Fail
	| n >= 0        = Ok (n `take` xs,  n `drop` xs)

interleave :: [a] -> [a] -> [a]										  -- interleaves two lists
interleave [] []         = []
interleave (x:xs) []     = (x:xs)
interleave [] (y:ys)     = (y:ys)
interleave (x:xs) (y:ys) = x : y : interleave xs ys -- add head xs, head ys to list 

--- Part f)

shuffle :: [Int] -> [a] -> Error [a]							  -- takes list of splits then
shuffle [] []        = Fail                         -- interleaves result
shuffle [] xpack     = Ok xpack
shuffle (x:xs) []    = Fail
shuffle (x:xs) xpack = shuffle xs (interleave a b)  -- define a b as variables
	where (Ok (a,b)) = split x xpack                  -- a and b equals x and xpack 

---------------------------------------------------------------------
----- EXERCISE 2
---------------------------------------------------------------------


data Btree a = ND | Data a |  Branch (Btree a) (Btree a) 
  deriving (Show,Eq)

data Dir = L | R 
  deriving (Show,Eq)

type Path =  [Dir] 
    
--- Part a)

extract :: Path  -> Btree a -> Error a
extract [] ND              = Fail
extract [] (Branch l r)    = Fail
extract [] (Data a)        = Ok a
extract (L:p) ND           = Fail
extract (R:p) ND           = Fail
extract (L:p) (Data a)     = Ok a
extract (R:p) (Data a)     = Ok a
extract (L:p) (Branch l r) = extract p l
extract (R:p) (Branch l r) = extract p r 

--- Part b)

add :: a -> Path -> Btree a -> Error (Btree a)
add x [] (Branch l r)      = Fail
add x (p:ps) ND            = Fail
add x _ (Data a)           = Fail
add x [] ND                = Ok (Data x)
add x (p:ps) (Branch l r)
	| p == R = case (add x ps r) of         
							 Ok dir -> Ok (Branch l dir)
							 Fail -> Fail
	| p == L = case (add x ps l) of
							 Ok dir -> Ok (Branch dir r)
							 Fail -> Fail

--- Part c)

getpath :: Eq b => (a -> b) -> b -> Btree a -> Path -> [Path]
getpath f x ND p           = []
getpath f x (Data a) p
	| (f a) == x             = [reverse p]  -- if node has x return the path to it
	| otherwise              = []
getpath f x (Branch l r) p =              -- recurse through each btree (l and r) 
	 ((getpath f x l (L:p)) ++              -- left edge to another branch add L to path   
		(getpath f x r (R:p)))		            -- right edge to another branch add R to path

findpath :: Eq b => (a -> b) -> b -> Btree a -> [Path]
findpath f x t = getpath f x t []          


tree1 = Branch ND ND
tree2 = Branch ND (Data 3)
tree3 = Branch tree1 tree2
tree4 = Branch (Data 3) (Data 4)
tree5 = Branch tree3 tree4



---------------------------------------------------------------------
----- EXERCISE 3 Family tree question
---------------------------------------------------------------------



-- a

{-- write here your answer
	"deriving Show" is used to specify that a custom data type can be displayed as a string.
		eg. data Colour = Red | Black
              deriving (.., .., .., Show)
   	Typing Red into the haskell interpreter would return the string "Red"
--}

-- b
{-- write here your answer
	"putStr :: String -> IO()" takes a String as an input and outputs it to an IO device such
	as the screen or a file.
	eg. putStr "hello" outputs the string hello to IO device which by default is the screen.
--}

-- c

-- Code taken from lecture slides
sort ::  Eq a => (a -> a -> Bool) -> [a] -> [a]
sort ord [] = []
sort ord (x:xs) = sort ord less ++ occs ++ sort ord more
										 where 
										 less = [ e | e <- xs, ord e x]
										 occs = x : [ e | e <- xs, e == x]
										 more = [ e | e <- xs, ord x e ]

-- d
data Tree a = U | F a (Tree a) (Tree a) deriving Show

term = 
	F "Anna" 
		(F "Fer-Jan" 
			(F "Willem" U U) 
			(F "Nettie" U U)
		) 
		(F "Paula" 
			(F "Mario" U U) 
			(F "Martha" U U)
		)

type Person = String

-- If person has number n then their father is 2 * n and mother 2 * n + 1

newTree :: a -> Tree a
newTree x = F x U U

mapTree ::  (a->b) -> Tree a -> Tree b
mapTree f U = U
mapTree f (F x t1 t2) = F (f x) (mapTree f t1) (mapTree f t2)

genlabelint :: (Tree Person) -> Int -> (Tree (Int, Person))
genlabelint U _ = U
genlabelint (F p l r) n = F (n, p) (genlabelint (l) (2 * n)) (genlabelint (r) (2 * n + 1)) 

genlabel :: (Tree Person) -> (Tree (Int, Person))
genlabel (F p l r) = (genlabelint (F p l r) 1)

preprint :: (Int, Person) -> String
preprint (n, p) = show (n) ++ ":  " ++ p

type Tile = [String]

flatten :: Tile -> String 
flatten [] = []
flatten (x:xs) = x ++ "\n" ++ flatten xs 

liststr :: Tree (Int,Person) -> [String]
liststr U = []
liststr (F p l r) = (preprint p) : (liststr (l) ++ liststr (r)) 

printlist :: Tree Person -> IO()
printlist tree = putStr (flatten (sort (<) (liststr (genlabel tree))))

tree = 
	F "Anna" 
		(F "Fer-Jan" 
			(F "Willem" U U)
			(F "Nettie" U U)
		) 
		(F "Paula" 
			(F "Mario" U U)
			(F "Martha" U U)
		)


test1 = printlist tree

--f

treetostr :: Tree (Int, Person) -> [String]
treetostr U = []
treetostr (F p l r) = treetostr (l) ++ [(subroot p)] ++ treetostr (r) 

tabbit :: Double -> String  		    -- Create no of tabs based on list no.
tabbit x
	| x < 1 = ""
	| x >= 1 = "\t" ++ tabbit (x - 1)

loggit :: Double -> Double			    -- Each entry is tabbed * log base 2 
loggit x 														
	| x < 0.0 = 0.0
	| x >= 0.0 = (logBase 2 x)


-- Creates the string with the no. of 
-- tabs based on person list no.

subroot :: (Int, Person) -> String 
subroot (n, p) = tabbit (loggit (fromIntegral (n))) ++ p  

print2Dtree :: Tree Person -> IO()
print2Dtree tree = putStr (flatten (treetostr (genlabel tree)))

test2 = print2Dtree tree






--Don't forget your name

-- please take care that your solution compiles.
-- of course if things don't work, you can comment them out
-- and explain in the comment that that something is wrong with it.