   #!/bin/bash

   # Caminho para o diretório onde está o seu JAR
   JAR_PATH="target/todoapp-1.0-SNAPSHOT.jar"

   if [ "$#" -lt 1 ]; then
       echo "Usage: todo.sh <command> [<args>]"
       exit 1
   fi

   COMMAND=$1
   shift

   case $COMMAND in
       add)
           java --jar $JAR_PATH add ${2:}
           ;;
       list)
           java --jar $JAR_PATH add list ${2:}
           ;;
       *)
           echo "Unknown command: $COMMAND"
           echo "Available commands: addTask, listTasks"
           exit 1
           ;;
   esac
