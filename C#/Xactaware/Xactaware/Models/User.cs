using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Xactaware.Models
{
    public class User
    {
        public int Id { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public string Mobile { get; set; }
        public string Email { get; set; }
        public Role Userrole { get; set; }
        public Address Useraddress { get; set; }
    }
}
