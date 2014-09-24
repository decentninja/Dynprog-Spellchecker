run: build
	java Speller

build:
	javac Speller.java


largetest:
	cat ordlista.txt | make run

time:
	time cat ordlista.txt | java Speller