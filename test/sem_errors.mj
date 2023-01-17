program ord // Error: program cannot be named by an already defined symbol

int a;
const int b = 10;
const char b = '1', c = 'c'; // Error: duplicate constant declaration of 'b'
int d, e[];
const bool g = true, h = false;
const char i = 'c';

class Lol {
	{
		char ord(char chr) {
			return '1';
		}
	}
}
class Lol2 extends Lol {
	int a, b;
}
class Lol4 extends Lol3 { // Error: Lol3 not defined
	int c;
	{
		void bb(int c) {
			return 1; // Error: incorrect return type
		}
	}
}
class Lol3 extends Lol2 {
	int a; // Error: duplicate field
	int c, d;
	{
		a aa() {} // Error: 'a' does not name a type
		Lol2() {} // Error: incorrect constructor name
		Lol3() {}
		void a(Lol x, int x) Lol2 x; { // Error: duplicate method name + duplicate parameter + duplicate variable declaration
			print(x); // Error: incorrect data type in print()
		}
		void f() Lol c[]; char c2; {
			this.a(); // Error: Non-method invocation
			a = 1;
			ord(a); // Error: incompatible parameter types
			c2 = ord(c2);
			a++;
			a--;
			[, a, b, ] = e;
			a = b * c + (-1) - 2 / 4 % d; // Error: multiplying by c not permitted
			b = new Lol(1, f(), 2); // Error: no such constructor + incompatible assignment
			c = new Lol2[a]; // Error: incompatible assignment (array types of inherited classes)
			c = new Lol2[c2]; // Error: array size must be an integer
			a = new int(); // Error: 'new' can only instantiate classes
			if (a) { // Error: conditional expression must be a boolean
				if (g || a && c[0] < c[1]) {} // Error: both sides of an OR must be booleans + reference types may only be compared with == or !=
				break; // Error: break can only be used within loops
			}
		}
	}
}

{
	void lol() {}
	Lol lol2(int a) int b, c[]; {}
}
// Error: no main method found
