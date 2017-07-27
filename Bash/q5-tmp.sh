#!/bin/bash
# Author W Peck

echo "The temporary directory is: "
echo  ${TMP}
echo "The working directory is: " 
echo $PWD

if [ $PWD = ${TMP} ] 
then
	echo "(WORKING DIR == TEMP DIR) = TRUE"
else
	echo "(WORKING DIR == TEMP DIR) = FALSE"
fi


