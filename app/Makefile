.PHONY: build report sonar

run-dist:
	./build/install/app/bin/app $(ARGS)

clean:
	./gradlew clean

build:
	./gradlew installDist

sonar:
	./gradlew sonar

report:
	./gradlew jacocoTestReport

test:
	./gradlew test

style:
	./gradlew checkstyleMain checkstyleTest
