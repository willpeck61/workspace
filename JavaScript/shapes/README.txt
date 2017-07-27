Frontend Developer Assessment - Will Peck

This application provides a command-line style user-interface via 
an HTML text input field for executing functions that manipulate an 
HTML5 canvas.  

I have taken an Object Orientated approach to the design where each command is 
instantiated as a class.  Each class holds the object properties and class 
specific methods.  

These include a “toString” method, for autocomplete, and another that 
draws the element onto the canvas.  The JQuery library was used to manipulate 
the DOM and event handling. 

The implemented functionality includes:

-	clear, circle, rectangle, line, fill (incomplete)
-	autocomplete
-	error handling
-	command line interface

The flood-fill command was not implemented correctly.  My attempt at a 
recursive solution results in a stack overflow exception.  If logically 
correct, it needs a suitable customised stack data-structure.
I used some responsive CSS by using viewport dimensions to position and 
resize elements.  I also used an @media query to adjust 
font-sizes for smaller displays.

To improve user experience, I added the "Next Parameter" button.  This allows
users to switch to the next parameter in the populated text field after autocomplete. 
Whilst it is not properly implemented yet, I have included it as an potential 
improvement.


