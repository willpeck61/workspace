using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using System.Text.Encodings.Web;

namespace Xactaware.Controllers
{
    public class HelloWorldController : Controller
    {

        //
        // GET: /HelloWorld/

        public IActionResult Index()
        {
            return View();
        }

        //
        // GET:/HelloWorld/Welcome

        public string Welcome(string name, int num)
        {
            return HtmlEncoder.Default.Encode($"Welcome {name} who likes the number {num}...");
        }
    }
}