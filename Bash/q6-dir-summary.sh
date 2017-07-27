#!/bin/sh
# Author Will Peck
echo "Files that belong to $USER most recently changed over 48 hours ago."
find $1 -maxdepth 1 -user "$USER" -ctime +2 

echo "Sub directories that do not belong to the user."
find $1 /! -type d -user "$USER" 

echo "The files that are executable by this user but not owned."
find $1 -maxdepth 1 -executable -type f ! -user "$USER"
