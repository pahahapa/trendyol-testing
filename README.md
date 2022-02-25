RunAPP actions on Win10 OS:
1. download last maven build zip and install java (maintain 11) 
to workstation and set it to Windows env. variables
2. unzip folder with project and go to folder trendyol-testing
3. open command promt (cmd) app (from path project folder in p.2) and
run "mvn clean package -DskipTests" and wait until mvn will be executed
4. go to target folder in (cmd) and run command 
"java -jar trendyol-testing-0.0.1-SNAPSHOT.jar" and wait about 1 min
5. check via Chrome http://localhost:4000/api/books - you will see empty array "[]"

OpenProject in IDEA using pom file and test it via BooksAppTest

Exit the application (shutdown) - use Ctrl+C in command promt (cmd)