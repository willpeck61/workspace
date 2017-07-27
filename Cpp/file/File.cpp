/*
 * File.cpp
 *
 *  Created on: Nov 1, 2016
 *      Author: np183
 *      Module: CO7105
 *      Department of Informatics
 *      University of Leicester
 */
/*
 * File.cpp
 *
 *  Created on: Nov 1, 2016
 *      Author: np183
 *      Module: CO7105
 *      Department of Informatics
 *      University of Leicester
 */

#include "File.h"
#include <iostream>

typedef unsigned int uint;

using std::string;
using std::cout;
using std::endl;

FileBase::FileBase(const std::string& name, char permissions)
{
  filename_ = name;
  chmod(permissions);
}

FileBase::~FileBase()
{
  //do nothing
}

string FileBase::name() const
{
  return filename_;
}

char FileBase::getPermissions()
{
  return filepermissions_;
}

bool FileBase::read() const
{
  if (filepermissions_ & r_) {
    return true;
  } else {
    return false;
  }
}

bool FileBase::write() const
{
  if (filepermissions_ & w_) {
    return true;
  } else {
    return false;
  }
}

bool FileBase::execute() const
{
  if (filepermissions_ & x_) {
    return true;
  } else {
    return false;
  }
}

FileType File::filetype() const
{
  return TEXTFILE;
}

void FileBase::chmod(char permissions)
{
  permissions = permissions - '0';
  filepermissions_ = ~r_ & ~w_ & ~x_;
  if (permissions == 0) {
    //no permissions are set.
  } else if (permissions == 1) {
    filepermissions_ |= x_;
  } else if (permissions == 2) {
    filepermissions_ |= w_;
  } else if (permissions == 3) {
    filepermissions_ |= w_ | x_;
  } else if (permissions == 4) {
    filepermissions_ |= r_;
  } else if (permissions == 5) {
    filepermissions_ |= r_ | x_;
  } else if (permissions == 6) {
    filepermissions_ |= r_ | w_;
  } else if (permissions == 7) {
    filepermissions_ |= r_ | w_ | x_;
  }

}

File::File(const std::string& name, char permissions): FileBase(name, permissions)
{
   content_ = "";
}

File::~File() {}

unsigned int File::size() const
{
  return content_.size();
}

char File::operator[](unsigned int i) const
{
  return content_[i];
}

char& File::operator[](unsigned int i)
{
  return content_[i];

}

void File::add(char c)
{
  content_ += c;
}

void File::add(char* arr, unsigned int len)
{
  for (int i = 0; i < len; i++) {
    content_ += arr[i];
  }
}

void File::add(const std::string& toAdd)
{
  content_ += toAdd;
}

Link::Link(const std::string& name, FileBase* other) :
    FileBase(name, other->getPermissions())
{
  otherfile_ = other;
  cout << otherfile_->read();
}

Link::~Link()
{
  otherfile_ = nullptr;
}

unsigned int Link::size() const {
  return otherfile_->size();
}

FileBase* Link::link() {
  // implement me
  return otherfile_;
}

FileType Link::filetype() const {
  // implement me
  return LINK;
}

char Link::getPermissions() {
  return otherfile_->getPermissions();
}

bool Link::read() const
{
  return otherfile_->read();
}

bool Link::write() const
{
  return otherfile_->write();
}

bool Link::execute() const
{
  return otherfile_->execute();
}

Directory::Directory(const std::string& name, char permissions) :
    FileBase(name, permissions)
{}

Directory::~Directory()
{}

FileType Directory::filetype() const
{
  return DIRECTORY;
}

unsigned int Directory::size() const
{
  return directory_.size();
}

FileBase* Directory::operator[](unsigned int i) const
{
  return directory_[i];
}

void Directory::add(FileBase* file)
{
  directory_.push_back(file);
}



