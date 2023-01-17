program syntax_errors

int a+; // Error: recovery until semicolon.
int a+, b; // Error: recovery until comma.

class A extends B+ { // Error: recovery until left brace
	int a+; // Error: recovery until semicolon
	int a+ // Error: recovery until left brace
	{}
}

{
	void a(int a+, int b) {} // Error: recovery until comma.
	void main() {
		b = 43+123+; // Error: recovery until semicolon.
		b = 123;
		b = 43+123+; // Error: recovery until semicolon.
		if (a+ > b) { // Error: recovery until right parenthesis.
			b = 43+123+; // Error: recovery until semicolon.
		}
	}
}
