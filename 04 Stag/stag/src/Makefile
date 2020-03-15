default: build

build:
	javac -classpath ./libs/dot-StagParser.jar:./libs/json-StagParser.jar:. StagServer.java

run: build
	java -classpath ./libs/dot-StagParser.jar:./libs/json-StagParser.jar:. StagServer ../data/entities.dot ../data/actions.json

clean:
	rm *.class
