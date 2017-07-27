/*globals $:false*/
/*jslint bitwise: true*/

/* Command class for clear command. */
function Clear(colour) {
    "use strict";
    this.colour = colour;
    
    this.toString = function () {
        return "clear 'colour'";
    };
    
    this.clearCanvas = function (canv) {
        var draw = canv.getContext("2d");
        draw.clearRect(0, 0, canv.width, canv.height);
        draw.fillStyle = this.colour;
        draw.fillRect(0, 0, draw.canvas.width, draw.canvas.height);
    };
}

/* Command class for circle command. */
function Circle(xpos, ypos, rad, colour) {
    "use strict";
    this.xpos = xpos;
    this.ypos = ypos;
    this.rad = rad;
    this.colour = colour;
    
    this.toString = function () {
        return "circle 'x' 'y' 'r' 'c'";
    };
    
    this.drawCircle = function (canv) {
        var draw = canv.getContext("2d");
        draw.beginPath();
        draw.arc(this.xpos, this.ypos, this.rad, 0, 2 * Math.PI);
        draw.strokeStyle = this.colour;
        draw.stroke();
        draw.closePath();
    };
}

/* Class for rectangle command. */
function Rectangle(xpos, ypos, width, height, colour) {
    "use strict";
    this.xpos = xpos;
    this.ypos = ypos;
    this.width = width;
    this.height = height;
    this.colour = colour;
    
    this.toString = function () {
        return "rectangle 'x' 'y' 'w' 'h' 'c'";
    };
    
    this.drawRectangle = function (canv) {
        var draw = canv.getContext("2d");
        draw.rect(this.xpos, this.ypos, this.width, this.height);
        draw.strokeStyle = this.colour;
        draw.stroke();
    };
}

/* Class for line command. */
function Line(xpos, ypos, xdest, ydest, colour) {
    "use strict";
    this.xpos = xpos;
    this.ypos = ypos;
    this.xdest = xdest;
    this.ydest = ydest;
    this.colour = colour;
    
    this.toString = function () {
        return "line 'x' 'y' 'h' 'v' 'c'";
    };
    
    this.drawLine = function (canv) {
        var draw = canv.getContext("2d");
        draw.beginPath();
        draw.moveTo(this.xpos, this.ypos);
        draw.lineTo(this.xdest, this.ydest);
        draw.strokeStyle = this.colour;
        draw.stroke();
        draw.closePath();
    };
}

/* Not yet implemented */
function Fill(xpos, ypos, colour) {
    "use strict";
    this.xpos = xpos;
    this.ypos = ypos;
    this.colour = colour;
    
    this.toString = function () {
        return "fill 'x' 'y' 'c'";
    };
}


/* From an online example using bitwise. */
function rgbToHex(r, g, b) {
    "use strict";
    return "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
}

/* Incomplete - Causes stack overflow */
function floodFill(canv, x, y, tgt_col, rep_col) {
    "use strict";
    var draw = canv.getContext("2d"), rgba, cur_col, line;
    if (x > 0 && y > 0 && x < canv.width && y < canv.height) {
        rgba = draw.getImageData(x, y, x, y).data;
        cur_col = rgbToHex(rgba[0], rgba[1], rgba[2]);
        if (cur_col === tgt_col) { return; }
        line = new Line(x, y, x + 1, y + 1, tgt_col);
        line.drawLine(canv);
        floodFill(canv, x + 1, y, tgt_col, rep_col);
        floodFill(canv, x - 1, y, tgt_col, rep_col);
        floodFill(canv, x, y + 1, tgt_col, rep_col);
        floodFill(canv, x, y - 1, tgt_col, rep_col);
    }
}

/* Autoselects the first param after autocomplete. */
function selectText(begin, end, str, elem) {
    "use strict";
    var selectRng;
    if (elem.createRange) {
        selectRng = elem.createRange();
        selectRng.collapse(true);
        selectRng.moveStart("character", begin);
        selectRng.moveEnd("character", end);
        selectRng.select();
    } else if (elem.setSelectionRange) {
        elem.setSelectionRange(begin, end);
    } else if (elem.selectionStart) {
        elem.selectionStart = begin;
        elem.selectionEnd = end;
    }
    elem.focus();
}

/* Check colour string matches the hex format. */
function testColour(col) {
    "use strict";
    var chk0 = /^#[0-9A-F]{6}$/i.test(col),
        chk1 = /^#[0-9A-F]{3}$/i.test(col);
    if (chk0 || chk1) {
        return true;
    } else {
        return false;
    }
}

/* Checks each param (except colour) is numeric. */
function testNumeric(arr) {
    "use strict";
    var chk = true;
    $.each(arr, function (k, v) {
        if (isNaN(parseInt(v, 10))) {
            chk = false;
        }
    });
    return chk;
}

/* Instantiates object specified and draws it on the canvas. */
function makeObj(cmd, params, col) {
    "use strict";
    var canv = $('#shapes')[0], fill, clear, circle, rect, line, flood;

    if (cmd === "clear") {
        clear = new Clear(col);
        clear.clearCanvas(canv);
    } else if (cmd === "circle") {
        circle = new Circle(params[0], params[1], params[2], col);
        circle.drawCircle(canv);
    } else if (cmd === "rectangle") {
        rect = new Rectangle(params[0], params[1], params[2], params[3], col);
        rect.drawRectangle(canv);
    } else if (cmd === "line") {
        line = new Line(params[0], params[1], params[2], params[3],
                        col);
        line.drawLine(canv);
    } else if (cmd === "fill") {
        fill = canv.getContext.fillStyle;
        if (typeof (fill) === "undefined") {
            fill = "#fff";
        }
        floodFill(canv, params[0], params[1], col, fill);
    } else {
        window.alert("Error: Command not recognised.");
    }
}

/* Splits and validates command and param values from user input
   before selectFunc. Then resets input field. */
function getCommand(inp) {
    "use strict";
    var first = false, j = 0, begin = 0, end = 0,
        err, str, colour, params = [], cmd = "";
    for (j = 0; j < inp.length; j += 1) {
        if (inp[j] === "'" && first === false) {
            first = true;
            begin = j + 1;
        } else if (inp[j] === "'" && first === true) {
            end = j;
            str = inp.substring(begin, end);
            params.push(str);
            str = "";
            first = false;
        }
        if (begin === 0 && inp[j] !== " ") {
            cmd = cmd.concat(inp[j]);
        }
    }
    colour = params[params.length - 1];
    params.splice(-1, 1);
    if (!(testNumeric(params))) {
        window.alert("Sorry, your input is incorrect.  Coordinates must be numeric.");
    } else if (!(testColour(colour))) {
        window.alert("Sorry, colours in hex format eg. #aabbcc.");
    } else {
        makeObj(cmd, params, colour);
        $("#cmd").val("");
    }
}

/* Capitalise any string */
String.prototype.capitalize = function () {
    "use strict";
    return this.charAt(0).toUpperCase() + this.slice(1);
};

/* Simple autocomplete function. Only populates input field when 
   matched substring returns one command. ie. 'c' returns 2 commmands 
   'circle' and 'clear', 'ci' returns just 'circle' which triggers the 
   autocomplete. */
function autoComplete(cmds, inp) {
    "use strict";
    var elems = 0, cmd = null, c, begin, end;
    
    $.each(cmds, function (k, v) {
        var i = 0;
        if (v.indexOf(inp) === 0) {
            cmd = v;
            elems += 1;
        }
    });
    
    if (cmd !== null && elems === 1) {
        cmd = cmd.capitalize();
        c = new window[cmd]();
        $("#cmd").val(c.toString());
        begin = c.toString().indexOf("'") + 1;
        end = c.toString().indexOf("'", begin);
        selectText(begin, end, c.toString(), $('#cmd')[0]);
    }
}


$(document).ready(function () {
    "use strict";
    var cmds = ["clear", "circle", "rectangle", "line", "fill"],
        canvw = $("#shapes").width(),
        canvh = $("#shapes").height(),
        canv = $("#shapes")[0];
    canv.width = canvw;
    canv.height = canvh;
    
    $("#cmd").keyup(function (e) {
        if (e) { e.preventDefault(); }
        var inp = this.value.toLowerCase();
        if (e.keyCode === 13) {
            getCommand(inp);
        } else if (!(e.keyCode === 8 || e.keyCode === 46)) {
            autoComplete(cmds, inp);
        }
    });
    
    /* Prevent form submit. */
    $("#cmdform").submit(function (e) {
        if (e) { e.preventDefault(); }
        return false;
    });
    
    $("#nextparam").click(function () {
        var str = $("#cmd").val(), begin, end, i,
            regex = /[xyrchvwh]/;
        begin = str.slice(0, $("#cmd")[0].selectionStart).length;
        for (i = begin; i < str.length; i += 1) {
            if (regex.test(str.substr(str[i]))) {
                begin = i + 4;
                end = begin + 1;
                break;
            }
        }
        selectText(begin, end, str, $("#cmd")[0]);
    });
    
    $("#send").click(function (e) {
        if (e) { e.preventDefault(); }
        var inp = $("#cmd")[0].value.toLowerCase();
        getCommand(inp);
    });

});





