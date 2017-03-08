main1: java $JAVA_OPTS -cp ./build/libs/*.jar com.example.Main
main2: sh socks.sh && export FIXIE_URL="http://localhost:9090" && java $JAVA_OPTS -cp ./build/libs/*.jar com.example.Main
main3: java $JAVA_OPTS -cp ./build/libs/*.jar com.example.Main HEROKU_POSTGRESQL_JADE_URL