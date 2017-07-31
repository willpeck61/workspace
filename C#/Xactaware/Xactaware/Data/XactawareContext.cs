using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

namespace Xactaware.Models
{
    public class XactawareContext : DbContext
    {
        public XactawareContext (DbContextOptions<XactawareContext> options)
            : base(options)
        {
        }

        public DbSet<Xactaware.Models.User> User { get; set; }
    }
}
