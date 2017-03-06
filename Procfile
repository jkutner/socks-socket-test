main: java $JAVA_OPTS -cp ./build/libs/*.jar com.example.Main
main_with_socks: sh socks.sh && export FIXIE_URL="http://localhost:9090" && java $JAVA_OPTS -cp ./build/libs/*.jar com.example.Main