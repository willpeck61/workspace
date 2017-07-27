 
---------------------------------------------------------------------
-- SPIRAL PROGRAM                            
-- Time-stamp: <2016-01-14 17:31:22 rlc3>                              
-- Created: 2014-02-07                                              
-- Author: Roy L. Crole                                              
-- Contents: 
{- 
This program produces a simple image consisting of 2 rectangular bands
 of "black and white" ("- and %") color, with the pixels in a central
 disc rotated by angle alpha where alpha varies linearly from 2*pi at
 the origin to 0 at the disc boundary

    ***  TO USE IT, RUN HUGS, THEN TYPE

     	displayC (30,2)   [ or generally displayC (imageHeight,scale)]
  	displayAC (30,2)  [ or generally displayAC (imageHeight,scale)]

    *** WHERE disc radius = imageHeight/scale

We should form the spiral image by updating each pixel of the
image with the (color of) the pixel that is alpha radians
anticlockwise, rather than 'moving' each pixel p to rotate(p).

GOOD: displayAC args
for each p in image, p := getcolor(rotateAntiClockwise(p)) 

BAD: displayC args
for each p in image, rotate(p) := getcolour(p) 

Answer: Not every pixel x making up the image has a p such that
rotate(p) = x. Only the pixels for which rotate is an onto function
will be updated to the "correct" colour. The rotate function is not
onto due to the integer rounding errors. 

In the case of the anticlockwise rotation, pixels in the "vicinity" of
rotateAntiClockwise(p) are likely to be of "roughly the right color on
average"!!!  
-}
---------------------------------------------------------------------

--------------------------------------------------------------------- 
-- Types 
--------------------------------------------------------------------- 

-- parameter args (below) 
-- args = (2*size is image height, fraction of image to be rotated = 1/scale) 

type Size = Double 
type Scale = Double
type Args = (Size,Scale) 
type Pixel = (Coord,Color)
type Image = [Pixel]
type Coord = (Double,Double) 
type Color = String

--------------------------------------------------------------------- 
-- display of spiral image
--------------------------------------------------------------------- 

displayAC :: Args -> IO()
displayAC args = putStr (dispCols args imageACcols)
	       	 where
		 imageACcols = map snd (imageAC args)
		 dispCols :: Args -> [Color] -> Color
		 dispCols args []    = [] 
		 dispCols args cols = (dispRowCols (take (round(2*(fst args)-1)) cols)) 
	      	 	       	         ++ 
		     			 "\n" ++ dispCols args  (drop (round((2*(fst args)-1))) cols) 
       	         dispRowCols (h:t) = h ++ (dispRowCols t)
                 dispRowCols    [] = ""

displayC :: Args -> IO()
displayC args = putStr (dispCols args imageCcols)
	       	 where
		 imageCcols = map snd (imageC args)
		 dispCols :: Args -> [Color] -> Color
		 dispCols args []    = [] 
		 dispCols args cols = (dispRowCols (take (round(2*(fst args)-1)) cols)) 
	      	 	       	         ++ 
		     			 "\n" ++ dispCols args  (drop (round((2*(fst args)-1))) cols) 
       	         dispRowCols (h:t) = h ++ (dispRowCols t)
                 dispRowCols    [] = ""

---------------------------------------------------------------------
-- produce the spiral image, imageAC, computed by anti-clockwize
-- rotation, starting from image
---------------------------------------------------------------------

imageAC :: Args -> Image 
imageAC args = spiralAC args (image args)

-- look up the colour at (x,y) ; return "2" if not found
lookupp :: Coord -> Image -> Color
lookupp (x,y) (h:t) = if (x,y)==(fst h) then (snd h) else (lookupp (x,y) t)
lookupp (x,y) [] = "2"

-- inspect each coord (x,y), and if distance to origin is <= ((fst args)/(snd args)) 
-- then update that coord with the color obtained from the pixel
-- that is alpha radians anticlockwise, ie at coord rotateAC(x,y)
spiralAC :: Args -> Image -> Image 
spiralAC args (((x,y),c):t) = if d <= ((fst args)/(snd args)) 
                                 then ((x,y) , (lookupp (rotateAC args (x,y)) (image args))) : (spiralAC  args t)
			         else ((x,y) , c) : (spiralAC args t)
	   	      	       where
		               d = sqrt(x*x+y*y) 
spiralAC args [] = [] 

---------------------------------------------------------------------
-- produce the spiral image, imageC, computed by clockwize
-- rotation, starting from image
---------------------------------------------------------------------

imageC :: Args -> Image 
imageC args = sortImage((spiralC args (image args)))

{- 
-- probably not needed; I thought that "rotated coords might end up outside
-- the image area, O_ut_of_Bounds" at one stage, but now I don't think so. 
remOofBcoords args (((x,y),c):t) = if (
    (x>((fst args)-1)) || (y>((fst args)-1)) || (x< -((fst args)-1)) || (y< -((fst args)-1)) 
         ) then (((x,y), " ")) : (remOofBcoords args t) else (((x,y),c)) : (remOofBcoords args t)
remOofBcoords args [] = [] 
-}

-- inspect each coord (x,y), and if distance to origin is <= ((fst args)/(snd args)) 
-- then update that coord with the coord 
-- that is alpha radians clockwise, ie coord rotateC(x,y)
-- NOTE: the list of coords (ie pixels) in the output image is NOT in the correct order (of course!)
-- we need to use sortImage to correctly order the pixels, "top left to bottom right"
spiralC args (((x,y),c):t) = if d <= abs((fst args)/(snd args)) 
	     		     	then ((rotateC args (x,y)),c) : (spiralC args t)
				else ((x,y) , c) : (spiralC args t)
	   	      	       where
		               d = sqrt(x*x+y*y) 
spiralC args [] = [] 

sortImage :: Image -> Image 
sortImage []     = []
sortImage (p:xs) = sortImage lesser ++ [p] ++ sortImage greater
       	      	where 
       	      	lesser  = [ y | y <- xs, lt (fst y) (fst p) ]
       	        greater = [ y | y <- xs, not( lt (fst y) (fst p)) ]
                lt (a,b) (a',b') = (b<b') || ((b==b')&&(a<=a'))

---------------------------------------------------------------------
-- rotation functions 
-- pixels at (0,0) are rotated 2pi; pixels at distance size remain fixed
-- linear rotation angle alpha for pixels at distance d (from (0,0)) between 0 and size
---------------------------------------------------------------------

-- rotateAC (a,b) is (a,b) rotateAC by alpha radians AntiClockwise
-- graphics coords 

rotateAC  :: Args -> Coord -> Coord
rotateACi :: Args -> Coord -> Double 
rotateACj :: Args -> Coord -> Double 
rotateACi args (i, j) = fromIntegral (round(((i) * cos(alpha) + ((j)) * sin(alpha)))) 
                      	where
 	                d = sqrt (i*i+j*j) 
                        alpha= 2*pi*(1-(d-0)/((fst args)-0));
rotateACj args (i,j) = fromIntegral(round(((-i) * sin(alpha) + ((j)) * cos(alpha))))
                       	where
 	                d = sqrt (i*i+j*j) 
                        alpha= 2*pi*(1-(d-0)/((fst args)-0));
rotateAC   args (a,b) = ((rotateACi  args (a,b)), (rotateACj  args (a,b)))

-- rotateC (a,b) is (a,b) rotated by alpha radians Clockwise
-- graphics coords 
rotateC  :: Args -> Coord -> Coord
rotateCi :: Args -> Coord -> Double 
rotateCj :: Args -> Coord -> Double 
rotateCi  args (i, j) = fromIntegral (round( ((i) * cos(alpha) - ((j)) * sin(alpha))) ) 
                      	where
 	                d = sqrt (i*i+j*j) 
                        alpha= 2*pi*(1-(d-0)/((fst args)-0));
rotateCj  args (i,j) = fromIntegral (round( ( ((i) * sin(alpha) + ((j)) * cos(alpha)))) )
                        where
 	                d = sqrt (i*i+j*j) 
                        alpha= 2*pi*(1-(d-0)/((fst args)-0));
rotateC  args (a,b) = ((rotateCi args  (a,b)), (rotateCj args (a,b)))

---------------------------------------------------------------------
-- set up an image 
---------------------------------------------------------------------

-- area is (2*size)^2 
-- where args = (size,scale)
-- a small rectangular image of "pixels", top area white and bottom area black
-- NOTE: pixels are in the order "top left to bottom right", "(-x,-y) to (+x,+y)
white :: Args -> [Coord]
white args = cartp  [-((fst args)-1)..((fst args)-1)] [-((fst args)-1)..0]
black args = cartp  [-((fst args)-1)..((fst args)-1)] [1.0..((fst args)-1)] 

-- take a color c and coordinate pn = (x,y) and output pixel ((x,y),c)
addcol c (pn:pns)  = (pn,c) : (addcol c pns) 
addcol c []  = [] 

-- the image is a list of pixels (depending on args) 
-- white and black are "%" and "-"
image :: Args -> Image
image args = (addcol "%" (white args))++(addcol "-" (black args))

---------------------------------------------------------------------
-- cartesian product
---------------------------------------------------------------------

-- cartesian product of lists
cartp  l (h:t) = (prod l h) ++ (cartp l t)
       	         where -- make the list [(h,e1),(h,e2), .. ] from [e1,e2, .. ]
		  prod (h':t) h = (h,h') : (prod t h)
		  prod     [] h = [] 
cartp  l []    = []

---------------------------------------------------------------------
-- The End
---------------------------------------------------------------------
