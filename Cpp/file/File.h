/*
 * File.h
 *
 *  Created on: Nov 1, 2016
 *      Author: np183
 *      Module: CO7105
 *      Department of Informatics
 *      University of Leicester
 */

#ifndef FILE_H_
#define FILE_H_

#include <string>
#include <cstdint>
#include <vector>

enum Permission { READ, WRITE, EXECUTE };
enum FileType { TEXTFILE, LINK, DIRECTORY };

// This exercise implements a simple inheritance hierarchy.
// The base class is FileBase and there are three derived
// classes: File (text file), Link, and Directory.
// Each of these files supports several functions as seen below.
//
// You should add the virtual keyword where appropriate and then
// implement the functions where they are most appropriate.
// FileBase should be an abstract class but the others should be
// concrete classes.
//
// Each of the concrete classes has some methods not
// supported by the others.
class FileBase {
public:
	// No need to implement default, copy, and move constructors
	// and assignment operators
	//FileBase(); // required for compilation of the EMPTY version. REMOVE IF YOU DONT NEED
	FileBase(const FileBase& other) = delete;
	FileBase(FileBase&& other) = delete;
	FileBase& operator=(const FileBase& other) = delete;
	FileBase& operator=(FileBase&&) = delete;

	// Construct a basic file with a name and permissions
	// The permissions are passed as to chmod but only for
	// one user:
	// So it should be a character between 0 and 7 and
	// the binary encoding spells r=4, w=2, x=1
	// 7 = rwx
	// 6 = rw
	// 5 = rx
	// 4 = r
	// 3 = wx
	// 2 = w
	// 1 = x
	// 0 =
	FileBase(const std::string& name, char permissions);

	// Clean
	virtual ~FileBase();

	// Return the name of the file
	std::string name() const;

	// For a File return the size of the file
	// For a Directory return the number of files
	// in the directory
	// For a Link whatever the size of the object
	// it links
	virtual unsigned int size() const = 0;

	// Return the file type:
	// TEXTFILE, LINK, or DIRECTORY
	virtual FileType filetype() const = 0;

	// Return whether the file has that permission
	virtual bool read() const;
	virtual bool write() const;
	virtual bool execute() const;

	// Change the permission of the file according
	// to the char.
	// Encoding in binary as before
	void chmod(char);
	virtual char getPermissions();

protected:
	// TODO:
	// Do you need to add protected members?

private:
	// TODO
	// Add private members (data definitely, functions if you need)

  unsigned char filepermissions_;
  const unsigned char r_ = 0x01; // (r)ead
  const unsigned char w_ = 0x02; // (w)rite
  const unsigned char x_ = 0x04; // e(x)ecute
  std::string filename_;

};

// A text file
// It contains an unbounded amount of text
class File : public FileBase {
public:

  // No need to implement default, copy, and move constructors
	// or assignment operators
	File() = delete;
	File(const File&) = delete;
	File(File&&) = delete;
	File& operator=(const File& other) = delete;
	File& operator=(File&&) = delete;

	// Initialize a text file
	File(const std::string& name, char permissions);

	// Clean
	~File() override;

	FileType filetype() const override;

	unsigned int size() const override;

	// Allow access to the contents of the file
	// There is no need to check bounds (behavior
	// in case of out of range access is undefined)
	char operator[](unsigned int) const;
	char& operator[](unsigned int);

	// Add a character, a sequence of characters of the specified
	// lenght, or a whole string to the end of the file
	void add(char);
	void add(char*, unsigned int);
	void add(const std::string&);

protected:

	// TODO:
	// Do you need to add protected members?


private:
	std::string content_;
	// TODO
	// Add private members (data definitely, functions if you need)

};

class Link : public FileBase {
public:

	// No need to implement default, copy, and move constructors
	// or assignment operators
	Link() = delete;
	Link(const Link&) = delete;
	Link(Link&&) = delete;
	Link& operator=(const Link& other) = delete;
	Link& operator=(Link&&) = delete;

	// Build a basic link with a pointer to another file
	// The memory of the other file is not owned
	Link(const std::string& name, FileBase* other);

	~Link() override;

	FileType filetype() const override;
  char getPermissions();
	bool read() const override;
	bool write() const override;
	bool execute() const override;

	unsigned int size() const override;
	// Return the link to the file
	FileBase* link();

protected:
	// TODO:
	// Do you need to add protected members?

private:
	FileBase* otherfile_;
};

// A Directory containing other files
// The number of files in the directory
// is unbounded
class Directory : public virtual FileBase {
public:

  // No need to implement default, copy, and move constructors
	// or assignment operators
	Directory() = delete;
	Directory(const Directory&) = delete;
	Directory(Directory&&) = delete;
	Directory& operator=(const Directory& other) = delete;
	Directory& operator=(Directory&&) = delete;

	// Create a basic directory with permissions
	Directory(const std::string& name, char);

	~Directory() override;

	FileType filetype() const override;

	unsigned int size() const override;
	// Access the files in the directory
	// There is no need to check bounds
	FileBase* operator[](unsigned int) const;

	// Add a file to the directory
	// The memory is not owned
	void add(FileBase*);

protected:

	// TODO:
	// Do you need to add protected members?

private:

  std::vector<FileBase*> directory_;
	// TODO
	// Add private members (data definitely, functions if you need)

};

#endif /* FILE_H_ */

/*
 * File.h
 *
 *  Created on: Nov 1, 2016
 *      Author: np183
 *      Module: CO7105
 *      Department of Informatics
 *      University of Leicester
 */


