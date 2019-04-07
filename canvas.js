/*var c = document.getElementById("myCanvas");
var ctx = c.getContext("2d");
ctx.strokeStyle = "#81AEBE";
ctx.moveTo(0,15);
ctx.lineTo(950,15);
ctx.stroke();*/

var c = document.getElementById("myCanvas");
var ctx = c.getContext("2d");

// Create gradient
var grd = ctx.createLinearGradient(0, 0, 950, 0);
grd.addColorStop(0, "#193D66");
grd.addColorStop(1, "#549EC8");

// Fill with gradient
ctx.fillStyle = grd;
ctx.fillRect(0, 20, 950, 5);