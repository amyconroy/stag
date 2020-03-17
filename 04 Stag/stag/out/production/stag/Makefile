default: build

build:
	javac -classpath ./src/libs/dot-StagParser.jar:./src/libs/json-StagParser.jar:. StagServer.java

run: build
	java -classpath ./src/libs/dot-StagParser.jar:./src/libs/json-StagParser.jar:. StagServer ../data/entities.dot ../data/actions.json

clean:
	rm *.class
