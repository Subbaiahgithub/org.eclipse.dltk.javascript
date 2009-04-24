==== function-simple
function a       () 
{
return;	
}
==
function a () {
	return;
}
==> formatter.indent.function=false
==== function-no-indent
function a () {
return;	
}
==
function a () {
return;
}
==> formatter.indent.function=true
==== function-with-parameters
function a(x) {
return x*x;	
}
==
function a (x) {
	return x*x;
}
==> formatter.indent.function=true
==> formatter.braces.function=next.line
==== function-braces-next-line
function a ( x ) 				{
return x*x;	
}
==
function a (x)
{
	return x*x;
}
==> formatter.indent.function=true
==> formatter.braces.function=next.line.indented
==== function-braces-next-line-indented
function a(x) {
return x*x;	
}
==
function a (x)
	{
		return x*x;
	}
==> formatter.indent.function=true
==> formatter.braces.function=next.line
==== function-no-name
MyObject.prototype.getMessage = function () {
return this.message;			
};
==
MyObject.prototype.getMessage = function ()
{
	return this.message;
};
====